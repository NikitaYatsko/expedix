package srl.ramaiana.expedix.service.Impl;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import srl.ramaiana.expedix.constants.ApiErrorMessage;
import srl.ramaiana.expedix.exceptions.DataExistsException;
import srl.ramaiana.expedix.exceptions.DataNotFoundException;
import srl.ramaiana.expedix.exceptions.InvalidDataException;
import srl.ramaiana.expedix.exceptions.InvalidPasswordException;
import srl.ramaiana.expedix.mapper.UserMapper;
import srl.ramaiana.expedix.model.entity.Role;
import srl.ramaiana.expedix.model.entity.enums.RolesEnum;
import srl.ramaiana.expedix.model.request.user.LoginRequest;
import srl.ramaiana.expedix.model.dto.user.UserProfileDTO;
import srl.ramaiana.expedix.model.entity.RefreshToken;
import srl.ramaiana.expedix.model.entity.User;
import srl.ramaiana.expedix.model.request.user.RegistrationUserRequest;
import srl.ramaiana.expedix.repository.RoleRepository;
import srl.ramaiana.expedix.repository.UserRepository;
import srl.ramaiana.expedix.security.JwtTokenProvider;
import srl.ramaiana.expedix.service.AuthService;
import srl.ramaiana.expedix.service.RefreshTokenService;
import srl.ramaiana.expedix.utils.PasswordUtils;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserProfileDTO login(@NotNull LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new InvalidDataException(ApiErrorMessage.INVALID_USER_OR_PASSWORD.getMessage());
        }

        User user = userRepository.findUserByEmailAndIsDeletedFalse(loginRequest.getEmail()).orElseThrow(
                () -> new InvalidDataException(ApiErrorMessage.INVALID_USER_OR_PASSWORD.getMessage())
        );

        RefreshToken refreshToken = refreshTokenService.generateOrUpdateRefreshToken(user);
        String token = jwtTokenProvider.generateToken(user);
        UserProfileDTO userProfileDTO = userMapper.toUserProfileDTO(user, token, refreshToken.getToken());
        userProfileDTO.setToken(token);
        return userProfileDTO;
    }

    @Override
    public UserProfileDTO refreshAccessToken(String refreshTokenValue) {
        RefreshToken refreshToken = refreshTokenService.validateAndRefreshToken(refreshTokenValue);
        User user = refreshToken.getUser();
        String accessToken = jwtTokenProvider.generateToken(user);
        UserProfileDTO userProfileDTO = userMapper.toUserProfileDTO(user, accessToken, refreshToken.getToken());
        userProfileDTO.setToken(accessToken);
        return userProfileDTO;
    }

    @Override
    public UserProfileDTO registerUser(@NotNull RegistrationUserRequest registrationUserRequest) {
        if (userRepository.existsByEmail(registrationUserRequest.getEmail())) {
            throw new DataExistsException(ApiErrorMessage.EMAIL_ALREADY_EXISTS
                    .getMessage(registrationUserRequest.getEmail()));
        }

        Role role = roleRepository.findByUserSystemRole(RolesEnum.USER).orElseThrow(
                () -> new DataNotFoundException(ApiErrorMessage.USER_ROLE_NOT_FOUND.getMessage())
        );

        String password = registrationUserRequest.getPassword();
        String confirmPassword = registrationUserRequest.getConfirmPassword();

        if (!password.equals(confirmPassword)) {
            throw new InvalidPasswordException(ApiErrorMessage.INVALID_PASSWORD.getMessage());
        }

        if (PasswordUtils.isNotValidPassword(password)) {
            throw new InvalidPasswordException(ApiErrorMessage.INVALID_PASSWORD.getMessage());
        }

        User newUser = userMapper.toEntity(registrationUserRequest);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        newUser.setRoles(roles);
        userRepository.save(newUser);

        RefreshToken refreshToken = refreshTokenService.generateOrUpdateRefreshToken(newUser);
        String token = jwtTokenProvider.generateToken(newUser);
        UserProfileDTO uesrDTO = userMapper.toUserProfileDTO(newUser, token, refreshToken.getToken());
        uesrDTO.setToken(token);
        return uesrDTO;

    }
}
