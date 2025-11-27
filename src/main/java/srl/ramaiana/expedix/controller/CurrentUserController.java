package srl.ramaiana.expedix.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import srl.ramaiana.expedix.model.dto.user.UserDTO;
import srl.ramaiana.expedix.service.UserService;

@RestController
@RequestMapping("/api/me")
@RequiredArgsConstructor
public class CurrentUserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDTO> getCurrentUser(@AuthenticationPrincipal String email) {
        UserDTO userDTO = userService.getCurrentUser(email);
        return ResponseEntity.ok(userDTO);
    }
}
