package quizportal.service.permission;

public enum RoleEnum {
  ROLE_ADMIN(RoleNames.ROLE_ADMIN),
  ROLE_USER(RoleNames.ROLE_USER),
  ROLE_HR(RoleNames.ROLE_HR),
  ROLE_TEAM_LEAD(RoleNames.ROLE_TEAM_LEAD),
  ROLE_MANAGER(RoleNames.ROLE_MANAGER);

  private final String name;

  RoleEnum(String name) {
    this.name = name;
  }
}
