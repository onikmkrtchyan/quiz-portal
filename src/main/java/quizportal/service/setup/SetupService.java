package quizportal.service.setup;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quizportal.service.permission.RoleEnum;
import quizportal.service.user.UserFacade;
import quizportal.service.user.dto.UserDtoWithPassword;

@Slf4j
@Service
@RequiredArgsConstructor
public class SetupService implements ApplicationListener<ContextRefreshedEvent> {
    private final UserFacade userFacade;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        log.debug("Setup started");
        setAdmin();
        log.debug("Details is ");
    }

    private void setAdmin() {
        log.debug("Admin Setup started");

        final UserDtoWithPassword user = new UserDtoWithPassword();
        user.setUsername("Admin");
        user.setEmail("admin@quizportal.am");
        user.setPassword(passwordEncoder.encode("Quiz-Portal_1234"));
        user.setFirstName("Admin");
        user.setMiddleName("Admin");
        user.setLastName("Admin");
        user.setPhoneNumber("+37477889900");
        user.setRole(RoleEnum.ROLE_ADMIN);

        log.debug("Details is {}", user);

        userFacade.createIfNotExist(user);
    }
}
