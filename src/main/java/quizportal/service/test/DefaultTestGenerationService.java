package quizportal.service.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quizportal.data.model.Question;
import quizportal.data.model.enums.TestSubjectEnum;
import quizportal.service.question.QuestionService;
import quizportal.service.question.dto.BaseQuestionDto;
import quizportal.service.question.dto.TwoFieldQuestionDTO;
import quizportal.service.test.dto.TestDTO;
import quizportal.utils.mapping.DTOMapper;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultTestGenerationService implements TestGenerationService {
    private static final int NUMBER_OF_QUESTIONS = 5;
    private static final int TARGET_AVERAGE_COMPLEXITY = 50;
    private final QuestionService questionService;
    private final DTOMapper dtoMapper;

    private List<List<TwoFieldQuestionDTO>> getCombinations(List<TwoFieldQuestionDTO> questions, int numberOfQuestions, int targetAvg) {
        List<List<TwoFieldQuestionDTO>> combinations = new ArrayList<>();
        getCombinations(questions, 0, new ArrayList<>(), numberOfQuestions, targetAvg, combinations);
        return combinations;
    }

    private void getCombinations(List<TwoFieldQuestionDTO> questions, int index, List<TwoFieldQuestionDTO> current, int numberOfQuestions, int targetAvg, List<List<TwoFieldQuestionDTO>> combinations) {
        if (current.size() == numberOfQuestions) {
            double avg = current.stream().mapToInt(TwoFieldQuestionDTO::getComplexityLevel).average().getAsDouble();
            if (avg == targetAvg) {
                combinations.add(new ArrayList<>(current));
            }
            return;
        }

        for (int i = index; i < questions.size(); i++) {
            current.add(questions.get(i));
            getCombinations(questions, i + 1, current, numberOfQuestions, targetAvg, combinations);
            current.remove(current.size() - 1);
        }
    }

    @Override
    public TestDTO generate(final String participantUuid, final TestSubjectEnum subjectEnum) {
        List<Question> questions = questionService.findBySubject(subjectEnum);
        List<TwoFieldQuestionDTO> questionDTOS = dtoMapper.toDTO(questions);

        final List<List<TwoFieldQuestionDTO>> lists = getCombinations(questionDTOS, NUMBER_OF_QUESTIONS, TARGET_AVERAGE_COMPLEXITY);

        Random rand = new Random();
        List<TwoFieldQuestionDTO> test = lists.get(rand.nextInt(lists.size()));
        Set<Long> questionIds = new HashSet<>();
        test.forEach(twoFieldQuestionDTO -> questionIds.add(twoFieldQuestionDTO.getId()));

        List<Question> questionsToBeAssigned = questionService.getByIds(questionIds);
        Set<BaseQuestionDto> questionsForOneUser = dtoMapper.toBaseDTOs(questionsToBeAssigned);

        log.info("For this user {}, test is {}", participantUuid, questionsForOneUser);

        TestDTO testDTO = new TestDTO();
        testDTO.setQuestions(questionsForOneUser);
        testDTO.setParticipantUuid(participantUuid);

        return testDTO;
    }
}
