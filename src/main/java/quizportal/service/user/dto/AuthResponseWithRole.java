package quizportal.service.user.dto;

import lombok.Getter;
import lombok.Setter;
import quizportal.service.permission.RoleEnum;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class AuthResponseWithRole extends AuthResponse{
    @NotEmpty
    private RoleEnum role;
}
