package quizportal.service.user;

import quizportal.service.user.dto.BaseUserDTO;
import quizportal.service.user.dto.UserDtoWithPassword;
import quizportal.service.user.dto.UserRegisterDTO;

public interface UserFacade {
    BaseUserDTO create(BaseUserDTO user);

    BaseUserDTO findByUsername(String username);

    void updatePassword(UserRegisterDTO userRegisterDTO);

    void sendResetPasswordEmail(String email);

    void createIfNotExist(UserDtoWithPassword user);

    void updateIfValid(UserRegisterDTO userRegisterDto, String token);
}
