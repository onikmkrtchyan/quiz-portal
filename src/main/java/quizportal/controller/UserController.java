package quizportal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import quizportal.service.user.UserFacade;
import quizportal.service.user.dto.BaseUserDTO;

import javax.validation.Valid;


@RestController
@RequestMapping("user")
@Validated
@RequiredArgsConstructor
public class UserController {
    private UserFacade userFacade;

    @PostMapping
    @PreAuthorize("@permission.check(@role.ROLE_ADMIN)")
    public ResponseEntity<BaseUserDTO> create(@RequestBody @Valid BaseUserDTO user) {
        return ResponseEntity.ok(userFacade.create(user));
    }

    @PostMapping("/{username}")
    @PreAuthorize("@permission.check(@role.ROLE_ADMIN)")
    public ResponseEntity<BaseUserDTO> findByUsername(@PathVariable @Valid String username) {
        return ResponseEntity.ok(userFacade.findByUsername(username));
    }
}
