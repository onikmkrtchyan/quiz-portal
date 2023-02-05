package quizportal.service.permission;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quizportal.service.user.details.UserDetailsImpl;

import java.util.Collection;

@Service("permission")
@RequiredArgsConstructor
public class PermissionChecker {

    public static Long getAuthenticationUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return 0L;
        }

        return ((UserDetailsImpl) authentication.getPrincipal()).getId();
    }

    @Transactional
    public boolean check(String role) {
        Collection<? extends GrantedAuthority> authorities = getAuthorities();
        return authorities.stream().anyMatch(au -> au.getAuthority().equals(role));
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getAuthenticatedUserDetails().getAuthorities();
    }

    private UserDetails getAuthenticatedUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetails) authentication.getPrincipal();
    }
}
