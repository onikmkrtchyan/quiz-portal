package quizportal.service.user.dto;

import lombok.Getter;
import lombok.Setter;
import quizportal.service.permission.RoleEnum;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BaseUserDTO {

    @NotEmpty
    private String username;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;

    private String middleName;
    @NotEmpty
    private String phoneNumber;

    @Email
    @NotEmpty
    private String email;

    @NotNull
    private RoleEnum role;
}
