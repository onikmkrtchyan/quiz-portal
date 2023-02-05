package quizportal.service.test;

import quizportal.data.model.enums.TestSubjectEnum;
import quizportal.service.test.dto.TestDTO;

public interface TestGenerationService {
    TestDTO generate(final String participantUuid, final TestSubjectEnum subjectEnum);
}
