package quizportal.service.permission;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("role")
@RequiredArgsConstructor
public class RoleNames {
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_HR = "ROLE_HR";
    public static final String ROLE_TEAM_LEAD = "ROLE_TEAM_LEAD";
    public static final String ROLE_MANAGER = "ROLE_MANAGER";
}
