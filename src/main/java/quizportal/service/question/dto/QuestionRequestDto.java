package quizportal.service.question.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import quizportal.data.model.enums.TestSubjectEnum;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionRequestDto extends BaseQuestionDto {

    private String correctOption;

    private TestSubjectEnum subject;
}
