package quizportal.service.test.dto;

import lombok.Getter;
import lombok.Setter;
import quizportal.service.question.dto.BaseQuestionDto;

import java.util.Set;

@Getter
@Setter
public class TestDTO {
    private Set<BaseQuestionDto> questions;

    private String participantUuid;
}
