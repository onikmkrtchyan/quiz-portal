package quizportal.service.user;

import quizportal.data.model.User;
import quizportal.service.user.dto.UserDtoWithPassword;

public interface UserService {
    User save(User userEntity);

    User findByUsername(String username);

    void delete(Long userId);

    void createIfNotExist(UserDtoWithPassword user);

    User findById(Long authenticationUserId);

    User findByEmail(String email);

    boolean existsByUsername(String username);
}
