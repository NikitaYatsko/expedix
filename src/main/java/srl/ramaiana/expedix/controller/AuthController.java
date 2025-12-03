package srl.ramaiana.expedix.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import srl.ramaiana.expedix.model.request.user.LoginRequest;
import srl.ramaiana.expedix.model.dto.user.UserProfileDTO;
import srl.ramaiana.expedix.model.request.user.RegistrationUserRequest;
import srl.ramaiana.expedix.service.AuthService;
import srl.ramaiana.expedix.utils.ApiUtils;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
@Tag(name = "Аутентификация", description = "API для регистрации, входа и обновления токенов")
public class AuthController {
    private final AuthService authService;

    @Operation(
            summary = "Вход в систему",
            description = "Аутентификация пользователя по email и паролю. При успешном входе устанавливается HTTP-only cookie с токеном доступа."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешная аутентификация",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserProfileDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный формат запроса или некорректные данные"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Неверные учетные данные"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера"
            )
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Parameter(description = "Данные для входа", required = true)
            @RequestBody @Valid LoginRequest loginRequest,
            HttpServletResponse response) {
        log.info("Login Request: {}", loginRequest);

        UserProfileDTO result = authService.login(loginRequest);
        Cookie authorizationCookie = ApiUtils.createCookie(result.getToken());
        response.addCookie(authorizationCookie);

        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "Обновление токена доступа",
            description = "Обновление истекшего токена доступа с использованием refresh token. Устанавливает новую cookie с обновленным токеном."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Токен успешно обновлен",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserProfileDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный или истекший refresh token"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Неавторизованный запрос"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера"
            )
    })
    @GetMapping("/refresh/token")
    public ResponseEntity<?> refreshToken(
            @Parameter(
                    description = "Refresh token для получения нового токена доступа",
                    required = true,
                    example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
            )
            @RequestParam(name = "token") String refreshToken,
            HttpServletResponse response) {
        log.info("Refresh Token: {}", refreshToken);

        UserProfileDTO result = authService.refreshAccessToken(refreshToken);
        Cookie authorizationCookie = ApiUtils.createCookie(result.getToken());
        response.addCookie(authorizationCookie);
        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "Регистрация нового пользователя",
            description = "Создание новой учетной записи пользователя. При успешной регистрации автоматически аутентифицирует пользователя и устанавливает cookie с токеном."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Пользователь успешно зарегистрирован и аутентифицирован",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserProfileDTO.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверный формат запроса, некорректные данные или пользователь уже существует"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Конфликт: пользователь с таким email уже существует"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера"
            )
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Parameter(description = "Данные для регистрации нового пользователя", required = true)
            @RequestBody @Valid RegistrationUserRequest request,
            HttpServletResponse response) {
        log.info("Register Request: {}", request);
        UserProfileDTO result = authService.registerUser(request);
        Cookie authorizationCookie = ApiUtils.createCookie(result.getToken());
        response.addCookie(authorizationCookie);
        return ResponseEntity.ok(result);
    }
}