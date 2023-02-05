package quizportal.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quizportal.data.model.User;
import quizportal.data.repository.UserRepository;
import quizportal.exception.ResourceNotFoundException;
import quizportal.service.user.dto.UserDtoWithPassword;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return getUser(username);
    }

    private User getUser(String username) {
        return userRepository.findByUsernameAndRemovedIsFalse(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    @Transactional
    public void delete(final Long userId) {
        User user = userRepository.findByIdAndRemovedIsFalse(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", userId));
        userRepository.delete(user);
    }

    @Override
    public void createIfNotExist(UserDtoWithPassword user) {

    }

    @Override
    public User findById(Long id) {
        return userRepository.findByIdAndRemovedIsFalse(id).
                orElseThrow(() -> new ResourceNotFoundException("user", id));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmailAndRemovedIsFalse(email)
                .orElseThrow(() -> new ResourceNotFoundException("user", email));
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsernameAndRemovedIsFalse(username);
    }
}
