package quizportal.service.quiz;

import quizportal.data.model.enums.TestSubjectEnum;
import quizportal.service.quiz.dto.QuizDTO;
import quizportal.utils.constants.StringObj;

public interface QuizGeneratorService {
    StringObj generateUrl(TestSubjectEnum testSubject);

    void deactivate(String url);

    QuizDTO findAllParticipants(Long quizId);
}
