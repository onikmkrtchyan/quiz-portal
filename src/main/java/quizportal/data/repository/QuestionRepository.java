package quizportal.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import quizportal.data.model.Question;
import quizportal.data.model.enums.TestSubjectEnum;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findBySubjectAndRemovedIsFalse(TestSubjectEnum subjectEnum);

    List<Question> findByIdInAndRemovedIsFalse(Set<Long> questionIds);

    Optional<Question> findByIdAndRemovedIsFalse(Long id);
}
