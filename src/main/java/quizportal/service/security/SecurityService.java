package quizportal.service.security;

import quizportal.service.user.dto.AuthRequest;
import quizportal.service.user.dto.AuthResponseWithRole;
import quizportal.service.user.dto.UserResponse;

public interface SecurityService {
    void authenticateForChangePassword(String username, String oldPassword);

    AuthResponseWithRole authenticate(AuthRequest authRequest);

    AuthResponseWithRole getAuthResponseDTO();

    String getUsername();

    UserResponse getAuthenticatedUserData();

    AuthResponseWithRole renew(String refreshToken);
}
