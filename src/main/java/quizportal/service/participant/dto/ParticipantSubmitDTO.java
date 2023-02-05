package quizportal.service.participant.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ParticipantSubmitDTO {

    @NotNull
    private Long id;

    private String answer;
}
