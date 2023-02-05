package quizportal.service.user.details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quizportal.data.model.User;
import quizportal.data.repository.UserRepository;
import quizportal.exception.ResourceNotFoundException;
import quizportal.utils.constants.EntityName;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsernameAndRemovedIsFalse(username)
                .orElseThrow(() -> new ResourceNotFoundException(EntityName.USER, username));
        return UserDetailsImpl.build(user);
    }
}