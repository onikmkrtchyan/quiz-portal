package quizportal.service.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDtoWithPassword extends BaseUserDTO {
    private String password;
}
