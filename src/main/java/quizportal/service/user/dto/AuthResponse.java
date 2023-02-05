package quizportal.service.user.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AuthResponse {
    @NotEmpty
    private String accessToken;

    @NotEmpty
    private String refreshToken;

    @NotNull
    private Long expiresIn;
}
