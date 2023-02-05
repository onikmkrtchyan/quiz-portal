package quizportal.service.question.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseQuestionDto {

    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String description;

    private String defaultOption;

    private String optionOne;

    private String optionTwo;

    private String optionThree;

    private String optionFour;

    @Min(1)
    @Max(100)
    private Byte complexityLevel;
}
