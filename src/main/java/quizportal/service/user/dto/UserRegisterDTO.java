package quizportal.service.user.dto;

import lombok.Getter;
import lombok.Setter;
import quizportal.utils.password.ValidatePassword;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserRegisterDTO {

    @NotEmpty
    @ValidatePassword
    private String password;

    @NotEmpty
    @ValidatePassword
    private String confirmPassword;
}
