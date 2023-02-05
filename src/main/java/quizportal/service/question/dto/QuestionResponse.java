package quizportal.service.question.dto;

import lombok.Getter;
import lombok.Setter;
import quizportal.data.model.enums.TestSubjectEnum;

@Getter
@Setter
public class QuestionResponse extends BaseQuestionDto {

    private String correctOption;

    private TestSubjectEnum subject;

    private Long id;
}
