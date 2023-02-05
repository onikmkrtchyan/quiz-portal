package quizportal.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import quizportal.data.model.Participant;
import quizportal.data.model.QuestionParticipant;

import java.util.Optional;
import java.util.Set;

@Repository
public interface QuestionParticipantRepository extends JpaRepository<QuestionParticipant, Long> {

    Optional<QuestionParticipant> findByParticipantAndQuestionId(Participant participant, Long questionId);


}
