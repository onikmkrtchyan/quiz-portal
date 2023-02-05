package quizportal.service.question;

import quizportal.data.model.Question;
import quizportal.data.model.enums.TestSubjectEnum;
import quizportal.service.question.dto.QuestionRequestDto;
import quizportal.service.question.dto.QuestionResponse;
import quizportal.service.question.dto.UpdateQuestionDto;

import java.util.List;
import java.util.Set;

public interface QuestionService {

    void create(final QuestionRequestDto questionRequestDto);

    QuestionResponse getById(final Long id);

    List<QuestionResponse> getAll();

    Question updateQuestion(Long id, UpdateQuestionDto question);

    void delete(Long id);

    List<Question> findBySubject(final TestSubjectEnum subjectEnum);

    List<Question> getByIds(Set<Long> questionIds);
}
