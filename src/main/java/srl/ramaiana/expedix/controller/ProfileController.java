package srl.ramaiana.expedix.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import srl.ramaiana.expedix.model.dto.user.UserDTO;
import srl.ramaiana.expedix.service.ProfileService;
import srl.ramaiana.expedix.service.UserService;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/profile")
public class ProfileController {
    private final UserService userService;
    private final ProfileService profileService;

    @GetMapping
    public ResponseEntity<UserDTO> getUserProfile(Principal principal) {
        UserDTO dto = userService.getCurrentUser(principal.getName());
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/photo")
    public ResponseEntity<UserDTO> uploadPhoto(@RequestParam("file") MultipartFile file) {
        UserDTO dto = profileService.uploadPhoto(file);
        return ResponseEntity.ok(dto);
    }


}
