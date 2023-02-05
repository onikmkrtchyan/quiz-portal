package quizportal.config.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import quizportal.service.user.details.UserDetailsImpl;

import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<Long> {
    public Optional<Long> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.of(0L);
        }

        return Optional.of(((UserDetailsImpl) authentication.getPrincipal()).getId());
    }
}