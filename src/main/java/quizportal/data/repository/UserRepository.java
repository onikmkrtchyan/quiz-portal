package quizportal.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import quizportal.data.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsernameAndRemovedIsFalse(String username);

    boolean existsByUsernameAndRemovedIsFalse(String username);

    Optional<User> findByEmailAndRemovedIsFalse(String email);

    Optional<User> findByUsernameOrEmailAndRemovedIsFalse(String login, String email);

    Optional<User> findByIdAndRemovedIsFalse(Long id);
}
