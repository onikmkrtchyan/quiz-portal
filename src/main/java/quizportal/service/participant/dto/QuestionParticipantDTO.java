package quizportal.service.participant.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import quizportal.service.question.dto.QuestionRequestDto;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionParticipantDTO {

    private Long id;

    private QuestionRequestDto question;

    private String participantOption;

    private Float earnedScore;
}
