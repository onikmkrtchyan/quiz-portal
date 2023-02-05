package quizportal.service.user.dto;

import lombok.Getter;
import lombok.Setter;
import quizportal.utils.password.ValidatePassword;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class AuthRequest {

    @NotEmpty
    @NotBlank
    private String login;

    @ValidatePassword
    private String password;
}
