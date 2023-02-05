package quizportal.service.question.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class QuestionEvaluateDTO {

    @NotNull
    private Long questionParticipantId;

    private Float points;
}
