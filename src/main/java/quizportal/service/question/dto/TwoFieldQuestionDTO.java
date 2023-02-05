package quizportal.service.question.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TwoFieldQuestionDTO {
    private Byte complexityLevel;
    private Long id;
}
