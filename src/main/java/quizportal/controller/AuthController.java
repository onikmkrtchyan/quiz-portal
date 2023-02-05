package quizportal.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import quizportal.service.security.SecurityService;
import quizportal.service.user.UserFacade;
import quizportal.service.user.dto.AuthRequest;
import quizportal.service.user.dto.AuthResponseWithRole;
import quizportal.service.user.dto.UserRegisterDTO;

import javax.validation.Valid;
import javax.validation.constraints.Email;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final SecurityService securityService;
    private final UserFacade userFacade;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseWithRole> login(@RequestBody @Valid AuthRequest authRequest) {
        AuthResponseWithRole authResponseWithRole = securityService.authenticate(authRequest);
        return ResponseEntity.ok(authResponseWithRole);
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthResponseWithRole> logOut(@RequestBody @Valid AuthRequest authRequest) {
        // TODO impl
        AuthResponseWithRole authResponseWithRole = securityService.authenticate(authRequest);
        return ResponseEntity.ok(authResponseWithRole);
    }

    @PostMapping("/renew")
    public ResponseEntity<AuthResponseWithRole> renew(@RequestHeader(name = AUTHORIZATION) String refreshToken) {
        AuthResponseWithRole authResponse = securityService.renew(refreshToken);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    public void register(@RequestBody @Valid UserRegisterDTO userRegisterDTO) {
        userFacade.updatePassword(userRegisterDTO);
    }

    @PostMapping("/reset-password")
    public void resetPasswordRequest(@RequestParam @Email String email) {
        userFacade.sendResetPasswordEmail(email);
    }

    /**
     * When user forgot his password
     * Request from reset-password page
     */
    @ApiResponse(description = "User forgot his password. Should be used after reset-password")
    @PostMapping("/change-password")
    public void changePassword(@RequestBody UserRegisterDTO userRegisterDto,
                               @RequestHeader(name = AUTHORIZATION) String token) {
        userFacade.updateIfValid(userRegisterDto, token);
    }

}
