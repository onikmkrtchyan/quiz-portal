package quizportal.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import quizportal.data.model.Quiz;

import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    Optional<Quiz> findByUrlAndRemovedIsFalse(String url);

    boolean existsByUrlAndRemovedIsFalse(String url);

    Optional<Quiz> findByIdAndRemovedIsFalse(Long quizId);
}
