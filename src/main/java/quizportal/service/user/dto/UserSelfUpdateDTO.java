package quizportal.service.user.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserSelfUpdateDTO {

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    private String middleName;

    @NotEmpty
    private String phoneNumber;
}
