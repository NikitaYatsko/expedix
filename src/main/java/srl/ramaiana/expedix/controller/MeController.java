package srl.ramaiana.expedix.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import srl.ramaiana.expedix.model.dto.user.UserDTO;
import srl.ramaiana.expedix.service.UserService;

import java.security.Principal;
@RequiredArgsConstructor
@RestController
@RequestMapping("/profile")
public class MeController {
    private final UserService userService;
    @GetMapping
    public ResponseEntity<UserDTO> getUserProfile(Principal principal) {
        UserDTO dto = userService.getCurrentUser(principal.getName());
        return ResponseEntity.ok(dto);
    }

}
