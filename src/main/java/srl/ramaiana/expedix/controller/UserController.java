package srl.ramaiana.expedix.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import srl.ramaiana.expedix.model.dto.UserDTO;
import srl.ramaiana.expedix.model.request.NewUserRequest;
import srl.ramaiana.expedix.service.UserService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable Integer id) {
        log.info("Getting user by ID: {}", id);
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody NewUserRequest request) {
        log.info("Creating new user: {}", request);
        return ResponseEntity.ok(userService.createUser(request));
    }
}
