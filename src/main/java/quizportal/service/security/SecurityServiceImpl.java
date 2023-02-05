package quizportal.service.security;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quizportal.config.jwt.JwtTokenProvider;
import quizportal.data.model.User;
import quizportal.data.repository.UserRepository;
import quizportal.exception.RefreshTokenNotFoundException;
import quizportal.exception.ResourceNotFoundException;
import quizportal.service.refreshtoken.RefreshTokenServiceImpl;
import quizportal.service.user.UserService;
import quizportal.service.user.dto.AuthRequest;
import quizportal.service.user.dto.AuthResponseWithRole;
import quizportal.service.user.dto.UserResponse;
import quizportal.utils.mapping.DTOMapper;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class SecurityServiceImpl implements SecurityService {
    private static final Logger LOGGER = LogManager.getLogger(SecurityServiceImpl.class);

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final RefreshTokenServiceImpl refreshTokenService;
    private final UserRepository userRepository;
    private final DTOMapper dtoMapper;

    @Override
    public void authenticateForChangePassword(String username, String oldPassword) {
        LOGGER.info("authentication by username: {}, and old password", username);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
    }

    @Transactional
    @Override
    public AuthResponseWithRole authenticate(AuthRequest authRequest) {
        String username = getUsername(authRequest);
        LOGGER.info("Login authentication. Username: {}", username);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, authRequest.getPassword()));
        return createAuthorizationResponse(authRequest);
    }

    private String getUsername(AuthRequest authRequest) {
        String login = authRequest.getLogin();
        User user = userRepository.
                findByUsernameOrEmailAndRemovedIsFalse(login, login).
                orElseThrow(() -> new ResourceNotFoundException("User", login));
        return user.getUsername();
    }

    private String getPasswordByUsername(String username) {
        return userService.findByUsername(username).getPassword();
    }

    private AuthResponseWithRole createAuthorizationResponse(AuthRequest authRequest) {
        LOGGER.info("Authorization response creation");
        String username = getUsername(authRequest);
        String password = getPasswordByUsername(username);

        User user = userRepository.findByUsernameAndRemovedIsFalse(username).orElseThrow(() -> new ResourceNotFoundException("user", username));

        String accessToken = jwtTokenProvider.generateAccessToken(username, user.getRole().name(), password, user.getCreatedAt());

        String refreshToken = jwtTokenProvider.generateRefreshToken(username, user.getRole().name(), password, user.getCreatedAt());

        AuthResponseWithRole authResponseWithRole = new AuthResponseWithRole();
        authResponseWithRole.setRole(user.getRole());
        authResponseWithRole.setAccessToken(accessToken);
        authResponseWithRole.setRefreshToken(refreshToken);
        authResponseWithRole.setExpiresIn(jwtTokenProvider.getAccessTokenExpirationMs());
        return authResponseWithRole;
    }

    @Transactional
    @Override
    public AuthResponseWithRole getAuthResponseDTO() {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setLogin(getAuthenticatedUserDetails().getUsername());
        authRequest.setPassword(getAuthenticatedUserDetails().getPassword());
        return createAuthorizationResponse(authRequest);
    }

    @Override
    public String getUsername() {
        return getAuthenticatedUserDetails().getUsername();
    }


    @Override
    public UserResponse getAuthenticatedUserData() {
        String username = getUsername();
        User user = userRepository.findByUsernameAndRemovedIsFalse(username).
                orElseThrow(() -> new ResourceNotFoundException("User", username));
        return dtoMapper.toDTO(user);
    }

    private UserDetails getAuthenticatedUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetails) authentication.getPrincipal();
    }

    @Transactional
    @Override
    public AuthResponseWithRole renew(String refreshToken) {
        String jwt = jwtTokenProvider.parseJwt(refreshToken);
        if (!refreshTokenService.exists(jwt)) {
            throw new RefreshTokenNotFoundException("Refresh token not found");
        }
        refreshTokenService.delete(jwt);
        return getAuthResponseDTO();
    }
}