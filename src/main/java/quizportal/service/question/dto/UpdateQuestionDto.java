package quizportal.service.question.dto;

import lombok.Getter;
import lombok.Setter;
import quizportal.data.model.enums.TestSubjectEnum;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
public class UpdateQuestionDto {

    private String title;

    private String description;

    private String defaultOption;

    private String optionOne;

    private String optionTwo;

    private String optionThree;

    private String optionFour;

    private String correctOption;

    @Min(1)
    @Max(100)
    private Byte complexityLevel;

    private TestSubjectEnum subject;
}
