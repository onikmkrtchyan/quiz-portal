package quizportal.config.jwt;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import quizportal.exception.PermissionDeniedException;
import quizportal.service.permission.RoleEnum;
import quizportal.service.refreshtoken.RefreshTokenServiceImpl;
import quizportal.utils.password.PasswordUtils;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;

import static quizportal.utils.constants.Constants.PASSWORD;
import static quizportal.utils.constants.Constants.TOKEN_TYPE;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final Logger LOGGER = LogManager.getLogger(JwtTokenProvider.class);
    private final RefreshTokenServiceImpl refreshTokenService;

    @Value("${jwtSecret}")
    private String jwtSecret;

    @Value("${accessTokenExpirationMs}")
    private long accessTokenExpirationMs;

    @Value("${refreshTokenExpirationMs}")
    private long refreshTokenExpirationMs;

    @Value("${tempTokenExpirationMs}")
    private long tempTokenExpirationMs;

    @PostConstruct
    protected void init() {
        jwtSecret = Base64.getEncoder().encodeToString(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(
            String username, String role, String password, LocalDateTime createdDate) {
        LOGGER.info("generate access token");
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role);
        claims.put("created_date", createdDate.toString());
        return buildJwt(claims, accessTokenExpirationMs, password);
    }


    public String generateOneTimeToken(
            String username, String password, String role, LocalDateTime createdDate) {
        LOGGER.info("generate one time token");
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role);
        claims.put("created_date", createdDate.toString());

        return buildJwt(claims, tempTokenExpirationMs, password);
    }


    public String generateResetPasswordToken(
            String username, String password, String role, LocalDateTime createdDate) {
        LOGGER.info("generate reset password token");
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("reset", true);
        claims.put("role", role);
        claims.put("created_date", createdDate.toString());

        return buildJwt(claims, tempTokenExpirationMs, password);
    }

    public String generateRefreshToken(String username, String role, String password, LocalDateTime createdDate) {
        LOGGER.info("generate refresh token");
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("created_date", createdDate.toString());
        claims.put("role", role);
        String jwt = buildJwt(claims, refreshTokenExpirationMs, password);
        refreshTokenService.save(jwt);
        return jwt;
    }

    /**
     * Disfigure password is used in jwt body
     * to invalidate all clients tokens of a user
     * when user reset or change his password
     * and all clients of a user should be signed out
     *
     * @param claims          claims
     * @param jwtExpirationMs jwtExpirationMs
     * @param password        password
     * @return token
     */
    private String buildJwt(Claims claims, long jwtExpirationMs, String password) {
        LOGGER.info("build jwt token");
        claims.put(PASSWORD, PasswordUtils.getDisfiguredPassword(password));
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }


    public boolean jwtHasReset(String token) {
        try {
            LOGGER.info("check claims has 'reset' field ");
            return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get("reset", Boolean.class);
        } catch (Exception ex) {
            LOGGER.error("Invalid JWT Signature. May 'reset' field does not exist");
            throw new SignatureException("Invalid JWT signature");
        }
    }

    public boolean isAdmin(String token) {
        try {
            LOGGER.info("check claims has 'ROLE_ADMIN' field ");
            return Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody()
                    .get("role", String.class).equals(RoleEnum.ROLE_ADMIN.name());
        } catch (Exception ex) {
            LOGGER.error("Invalid JWT Signature.");
            throw new SignatureException("Invalid JWT signature");
        }
    }

    public String getSubject(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public String getDisfiguredPasswordFromToken(String token) {
        LOGGER.info("get disfigured password from token ");
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().get(PASSWORD, String.class);
    }

    public String parseJwt(String token) {
        LOGGER.info("jwt parsing");
        if (StringUtils.hasText(token) && token.startsWith(TOKEN_TYPE)) {
            return token.substring(TOKEN_TYPE.length());
        }
        throw new PermissionDeniedException();
    }

    public boolean validateJwtTokenSignature(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            LOGGER.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            LOGGER.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            LOGGER.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("JWT claims string is empty: {}", e.getMessage());
        }

        throw new PermissionDeniedException();
    }

    public long getAccessTokenExpirationMs() {
        return accessTokenExpirationMs;
    }


    /**
     * Validate jwt if user change or reset his password
     */
    public void validateJwtToken(String token, String password) {
        LOGGER.info("JWT token validation by disfigured password");
        if (getDisfiguredPasswordFromToken(token).equals(PasswordUtils.getDisfiguredPassword(password))) {
            return;
        }
        LOGGER.error("JWT token is unsupported");
        throw new PermissionDeniedException();
    }

    public String getRole(String jwt) {
        LOGGER.info("Get Role from JWT");
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).getBody().get("role", String.class);

    }
}
