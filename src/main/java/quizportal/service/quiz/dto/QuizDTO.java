package quizportal.service.quiz.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import quizportal.data.model.enums.TestSubjectEnum;
import quizportal.service.participant.dto.ParticipantResponseDTO;

import java.time.Duration;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuizDTO {
    private Duration duration;

    private TestSubjectEnum testSubject;

    private Byte questionsCount;

    private String acceptanceScore;

    private List<ParticipantResponseDTO> participants;
}
