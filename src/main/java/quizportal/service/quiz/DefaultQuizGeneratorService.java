package quizportal.service.quiz;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quizportal.data.model.Quiz;
import quizportal.data.model.enums.TestSubjectEnum;
import quizportal.data.repository.QuizRepository;
import quizportal.exception.ResourceNotFoundException;
import quizportal.service.permission.PermissionChecker;
import quizportal.service.quiz.dto.QuizDTO;
import quizportal.utils.RandomStringGenerator;
import quizportal.utils.constants.StringObj;
import quizportal.utils.mapping.DTOMapper;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class DefaultQuizGeneratorService implements QuizGeneratorService {

    private static final int URL_LENGTH = 7;
    private final QuizRepository quizRepository;
    private final DTOMapper dtoMapper;

    @Override
    @Transactional
    public StringObj generateUrl(TestSubjectEnum testSubject) {
        Quiz quiz = new Quiz();
        quiz.setTestSubject(testSubject);
        quiz.setUrl(RandomStringGenerator.generateString(URL_LENGTH));
        quizRepository.save(quiz);
        return new StringObj(quiz.getUrl());
    }

    @Override
    @Transactional
    public void deactivate(String url) {
        Quiz quiz = quizRepository.findByUrlAndRemovedIsFalse(url).
                orElseThrow(() -> new ResourceNotFoundException("Active URL", url));
        quiz.setRemoved(true);
        quiz.setRemovedBy(PermissionChecker.getAuthenticationUserId());
        quiz.setRemovedAt(LocalDateTime.now());
        quizRepository.save(quiz);
    }

    @Override
    public QuizDTO findAllParticipants(Long quizId) {
        Quiz quiz = quizRepository.findByIdAndRemovedIsFalse(quizId).
                orElseThrow(() -> new ResourceNotFoundException("Quiz", quizId));

        return dtoMapper.toDTO(quiz);
    }
}
