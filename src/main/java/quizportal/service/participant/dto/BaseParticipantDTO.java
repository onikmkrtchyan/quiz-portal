package quizportal.service.participant.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseParticipantDTO {

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
}
