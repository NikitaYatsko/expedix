package srl.ramaiana.expedix.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import srl.ramaiana.expedix.model.dto.user.LoginRequest;
import srl.ramaiana.expedix.model.dto.user.UserProfileDTO;
import srl.ramaiana.expedix.service.AuthService;
import srl.ramaiana.expedix.utils.ApiUtils;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest, HttpServletResponse response) {
        log.info("Login Request: {}", loginRequest);

        UserProfileDTO result = authService.login(loginRequest);
        Cookie authorizationCookie = ApiUtils.createCookie(result.getToken());
        response.addCookie(authorizationCookie);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/refresh/token")
    public ResponseEntity<?> refreshToken(@RequestParam(name = "token") String refreshToken, HttpServletResponse response) {
        log.info("Refresh Token: {}", refreshToken);

        UserProfileDTO result = authService.refreshAccessToken(refreshToken);
        Cookie authorizationCookie = ApiUtils.createCookie(result.getToken());
        response.addCookie(authorizationCookie);
        return ResponseEntity.ok(result);
    }

}
