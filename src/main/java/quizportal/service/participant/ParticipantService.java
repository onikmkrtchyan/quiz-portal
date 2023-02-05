package quizportal.service.participant;

import quizportal.service.participant.dto.BaseParticipantDTO;
import quizportal.service.participant.dto.ParticipantSubmitDTO;
import quizportal.service.question.dto.QuestionEvaluateDTO;
import quizportal.service.test.dto.TestDTO;
import quizportal.utils.constants.StringObj;

import java.util.Set;

public interface ParticipantService {
    TestDTO validateAndCreate(String url, BaseParticipantDTO baseParticipantDTO);

    StringObj submit(String uuid, Set<ParticipantSubmitDTO> answers);

    StringObj reviewQuizAnswers(Set<QuestionEvaluateDTO> evatuation);
}
