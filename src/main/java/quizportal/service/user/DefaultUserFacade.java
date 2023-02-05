package quizportal.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import quizportal.config.jwt.JwtTokenProvider;
import quizportal.data.model.User;
import quizportal.service.permission.PermissionChecker;
import quizportal.service.user.dto.BaseUserDTO;
import quizportal.service.user.dto.UserDtoWithPassword;
import quizportal.service.user.dto.UserRegisterDTO;
import quizportal.utils.mapping.DTOMapper;
import quizportal.utils.password.PasswordUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultUserFacade implements UserFacade {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final DTOMapper dtoMapper;

    @Override
    @Transactional
    public BaseUserDTO create(BaseUserDTO baseUserDTO) {
        // TODO: 18.01.2023 Use mapper
        User userEntity = new User();
        userEntity.setUsername(baseUserDTO.getUsername());
        userEntity.setFirstName(baseUserDTO.getFirstName());
        userEntity.setLastName(baseUserDTO.getLastName());
        userEntity.setMiddleName(baseUserDTO.getMiddleName());
        userEntity.setEmail(baseUserDTO.getEmail());
        userEntity.setPhoneNumber(baseUserDTO.getPhoneNumber());
        userEntity.setRole(baseUserDTO.getRole());
        userEntity.setPassword(PasswordUtils.generateOneTimePassword());

        userService.save(userEntity);

        return dtoMapper.toDTO(userEntity);
    }

    @Override
    public BaseUserDTO findByUsername(String username) {
        User user = userService.findByUsername(username);
        return dtoMapper.toDTO(user);
    }

    @Override
    @Transactional
    public void updatePassword(UserRegisterDTO userRegisterDTO) {
        PasswordUtils.comparePasswords(userRegisterDTO.getPassword(), userRegisterDTO.getConfirmPassword());
        User user = userService.findById(PermissionChecker.getAuthenticationUserId());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public void sendResetPasswordEmail(final String email) {
        User user = userService.findByEmail(email);
        // TODO impl Email sender service
//        emailSenderService.sendResetPasswordEmail(email, user.getUsername(), user.getPassword(), user.getRole(), user.getCreatedDate());
    }

    @Override
    @Transactional
    public void createIfNotExist(final UserDtoWithPassword user) {
        if (!userService.existsByUsername(user.getUsername())) {
            User userEntity = dtoMapper.toEntity(user);
            userService.save(userEntity);
        }
    }

    @Override
    @Transactional
    public void updateIfValid(UserRegisterDTO userRegisterDto, String token) {
        if (jwtTokenProvider.jwtHasReset(token)) {
            PasswordUtils.comparePasswords(userRegisterDto.getPassword(), userRegisterDto.getConfirmPassword());
            User user = userService.findById(PermissionChecker.getAuthenticationUserId());
            user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
            userService.save(user);
        }
    }

}
