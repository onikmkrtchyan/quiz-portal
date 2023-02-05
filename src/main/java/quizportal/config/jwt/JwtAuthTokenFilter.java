package quizportal.config.jwt;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.nonNull;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
public class JwtAuthTokenFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LogManager.getLogger(JwtAuthTokenFilter.class);

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            LOGGER.info("Checking token");
            String jwt = jwtTokenProvider.parseJwt(request.getHeader(AUTHORIZATION));
            if (nonNull(jwt) && jwtTokenProvider.validateJwtTokenSignature(jwt)) {
                LOGGER.info("Token signature is validated");
                String username = jwtTokenProvider.getSubject(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                jwtTokenProvider.validateJwtToken(jwt,userDetails.getPassword());
                LOGGER.info("JWT token validated by disfigured password");

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                LOGGER.info("Authentication is set");
            }
        } catch (Exception ex) {
            LOGGER.error("Something went wrong during internal filter {}", ex.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
