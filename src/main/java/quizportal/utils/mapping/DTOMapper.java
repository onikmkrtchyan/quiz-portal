package quizportal.utils.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import quizportal.data.model.Participant;
import quizportal.data.model.Question;
import quizportal.data.model.Quiz;
import quizportal.data.model.User;
import quizportal.service.participant.dto.BaseParticipantDTO;
import quizportal.service.question.dto.*;
import quizportal.service.quiz.dto.QuizDTO;
import quizportal.service.user.dto.UserDtoWithPassword;
import quizportal.service.user.dto.UserResponse;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface DTOMapper {

    UserResponse toDTO(User user);

    Question toEntity(QuestionRequestDto questionRequestDto);

    QuestionResponse toDTO(Question question);

    Question toEntity(QuestionResponse questionResponse);

    User toEntity(UserDtoWithPassword user);

    @Mapping(target = "id", ignore = true)
    Question toEntity(UpdateQuestionDto question);

    List<TwoFieldQuestionDTO> toDTO(List<Question> questions);

    BaseQuestionDto toBaseDTO(Question question);

    default Set<BaseQuestionDto> toBaseDTOs(List<Question> questions) {
        return questions.stream().map(this::toBaseDTO).collect(Collectors.toSet());
    }

    Participant toEntity(BaseParticipantDTO baseParticipantDTO);

    QuizDTO toDTO(Quiz quiz);
}
