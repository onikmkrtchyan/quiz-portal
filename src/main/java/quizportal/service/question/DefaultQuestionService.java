package quizportal.service.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import quizportal.data.model.Question;
import quizportal.data.model.enums.TestSubjectEnum;
import quizportal.data.repository.QuestionRepository;
import quizportal.exception.ResourceNotFoundException;
import quizportal.service.question.dto.QuestionRequestDto;
import quizportal.service.question.dto.QuestionResponse;
import quizportal.service.question.dto.UpdateQuestionDto;
import quizportal.utils.mapping.DTOMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
class DefaultQuestionService implements QuestionService {

    private final DTOMapper dtoMapper;
    private final QuestionRepository questionRepository;

    @Override
    public void create(QuestionRequestDto questionRequestDto) {
        Question question = dtoMapper.toEntity(questionRequestDto);
        questionRepository.save(question);
    }

    @Override
    public QuestionResponse getById(Long id) {
        return dtoMapper.toDTO(questionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id)));
    }

    @Override
    public List<QuestionResponse> getAll() {
        List<QuestionResponse> questionEntitys = new ArrayList<>();
        List<Question> questionEntities = questionRepository.findAll();
        for (Question question : questionEntities) {
            questionEntitys.add(dtoMapper.toDTO(question));
        }
        return questionEntitys;
    }

    @Override
    public Question updateQuestion(Long id, UpdateQuestionDto question) {
        Question questionEntity = questionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

        if (question.getTitle() != null) {
            questionEntity.setTitle(question.getTitle());
        }
        if (question.getDescription() != null) {
            questionEntity.setDescription(question.getDescription());
        }
        if (question.getDefaultOption() != null) {
            questionEntity.setDefaultOption(question.getDefaultOption());
        }
        if (question.getSubject() != null) {
            questionEntity.setSubject(question.getSubject());
        }
        if (question.getOptionOne() != null) {
            questionEntity.setOptionOne(question.getOptionOne());
        }
        if (question.getOptionTwo() != null) {
            questionEntity.setOptionTwo(question.getOptionTwo());
        }
        if (question.getOptionThree() != null) {
            questionEntity.setOptionThree(question.getOptionThree());
        }
        if (question.getOptionFour() != null) {
            questionEntity.setOptionFour(question.getOptionFour());
        }
        if (question.getCorrectOption() != null) {
            questionEntity.setCorrectOption(question.getCorrectOption());
        }
        if (question.getComplexityLevel() != null) {
            questionEntity.setComplexityLevel(question.getComplexityLevel());
        }
        questionRepository.save(questionEntity);

        return questionEntity;
    }

    @Override
    public void delete(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        questionRepository.delete(question);
    }

    @Override
    public List<Question> findBySubject(TestSubjectEnum subjectEnum) {
        return questionRepository.findBySubjectAndRemovedIsFalse(subjectEnum);
    }

    @Override
    public List<Question> getByIds(Set<Long> questionIds) {
        return questionRepository.findByIdInAndRemovedIsFalse(questionIds);
    }
}
