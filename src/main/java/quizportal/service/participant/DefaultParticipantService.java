package quizportal.service.participant;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quizportal.data.model.Participant;
import quizportal.data.model.Question;
import quizportal.data.model.QuestionParticipant;
import quizportal.data.model.Quiz;
import quizportal.data.repository.ParticipantRepository;
import quizportal.data.repository.QuestionParticipantRepository;
import quizportal.data.repository.QuestionRepository;
import quizportal.data.repository.QuizRepository;
import quizportal.exception.PermissionDeniedException;
import quizportal.exception.ResourceNotFoundException;
import quizportal.service.participant.dto.BaseParticipantDTO;
import quizportal.service.participant.dto.ParticipantSubmitDTO;
import quizportal.service.question.dto.QuestionEvaluateDTO;
import quizportal.service.test.TestGenerationService;
import quizportal.service.test.dto.TestDTO;
import quizportal.utils.constants.StringObj;
import quizportal.utils.mapping.DTOMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultParticipantService implements ParticipantService {
    private final QuizRepository quizRepository;
    private final ParticipantRepository participantRepository;
    private final TestGenerationService testGenerationService;
    private final QuestionParticipantRepository questionParticipantRepository;
    private final QuestionRepository questionRepository;
    private final DTOMapper dtoMapper;

    @Override
    @Transactional
    public TestDTO validateAndCreate(String url, BaseParticipantDTO baseParticipantDTO) {
        //TODO add validations
        if (quizRepository.existsByUrlAndRemovedIsFalse(url)) {
            Quiz quiz = quizRepository.findByUrlAndRemovedIsFalse(url).
                    orElseThrow(() -> new ResourceNotFoundException("Url", url));
            Participant participant = dtoMapper.toEntity(baseParticipantDTO);
            participant.setQuiz(quiz);
            participantRepository.save(participant);

            final TestDTO generatedTest = testGenerationService.generate(participant.getUuid(), quiz.getTestSubject());

            Set<Long> questionIds = new HashSet<>();

            generatedTest.getQuestions().
                    forEach(baseQuestionDto -> questionIds.add(baseQuestionDto.getId()));

            List<Question> questions = questionRepository.findByIdInAndRemovedIsFalse(questionIds);

            Set<QuestionParticipant> questionParticipants = new HashSet<>();
            for (Question question : questions) {
                QuestionParticipant questionParticipant = new QuestionParticipant();
                questionParticipant.setParticipant(participant);
                questionParticipant.setQuestion(question);
                questionParticipants.add(questionParticipant);
            }

            questionParticipantRepository.saveAll(questionParticipants);

            return generatedTest;
        }
        throw new PermissionDeniedException();
    }

    @Override
    @Transactional
    public StringObj submit(String uuid, Set<ParticipantSubmitDTO> answers) {
        AtomicInteger atomicInteger = new AtomicInteger();

        Participant participant = participantRepository.findByUuidAndRemovedIsFalse(uuid).
                orElseThrow(() -> new ResourceNotFoundException("Participant", uuid));

        if (participant.getIsSubmitted()){
            throw new RuntimeException("Your answer already submitted");
        }

        List<QuestionParticipant> questionParticipants = new ArrayList<>();
        answers.forEach(participantSubmitDTO -> {
            QuestionParticipant questionParticipant = questionParticipantRepository.findByParticipantAndQuestionId(participant, participantSubmitDTO.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("QuestionParticipant", participantSubmitDTO.getId()));

            Question question = questionRepository.findByIdAndRemovedIsFalse(participantSubmitDTO.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Question", participantSubmitDTO.getId()));
            if (question.getDefaultOption() == null && question.getCorrectOption().equals(participantSubmitDTO.getAnswer())) {
                questionParticipant.setEarnedScore(Float.valueOf(question.getComplexityLevel()));
                atomicInteger.incrementAndGet();
            } else {
                questionParticipant.setEarnedScore(0F);

            }
            questionParticipant.setParticipantOption(participantSubmitDTO.getAnswer());
            questionParticipants.add(questionParticipant);
        });
        questionParticipantRepository.saveAll(questionParticipants);

        participant.setIsSubmitted(true);
        participantRepository.save(participant);
        float f = (float) atomicInteger.get() / answers.size();
        return new StringObj("Wow! " + f * 100 + "%");
    }

    @Override
    @Transactional
    public StringObj reviewQuizAnswers(Set<QuestionEvaluateDTO> evaluation) {

        List<QuestionParticipant> questionParticipants = new ArrayList<>();

        evaluation.forEach(questionEvaluateDTO -> {
            QuestionParticipant questionParticipant = questionParticipantRepository.findById(questionEvaluateDTO.getQuestionParticipantId())
                    .orElseThrow(() -> new ResourceNotFoundException("QuestionParticipant", questionEvaluateDTO.getQuestionParticipantId()));

            if (questionEvaluateDTO.getPoints() != null) {
                questionParticipant.setEarnedScore(questionEvaluateDTO.getPoints());
            } else {
                questionParticipant.setEarnedScore(0F);
            }

            questionParticipants.add(questionParticipant);
        });

        questionParticipantRepository.saveAll(questionParticipants);

        return new StringObj("Participant points updated successfully ");
    }
}
