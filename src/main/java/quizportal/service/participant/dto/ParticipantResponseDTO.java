package quizportal.service.participant.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParticipantResponseDTO extends BaseParticipantDTO {
    private Long id;

    private LocalDateTime createdAt;

    private Float earnedScore;

    private List<QuestionParticipantDTO> questionParticipant;
}
