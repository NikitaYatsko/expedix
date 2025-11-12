package srl.ramaiana.expedix.service.Impl;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import srl.ramaiana.expedix.constants.ApiErrorMessage;
import srl.ramaiana.expedix.exceptions.InvalidDataException;
import srl.ramaiana.expedix.mapper.UserMapper;
import srl.ramaiana.expedix.model.dto.user.LoginRequest;
import srl.ramaiana.expedix.model.dto.user.UserProfileDTO;
import srl.ramaiana.expedix.model.entity.User;
import srl.ramaiana.expedix.repository.UserRepository;
import srl.ramaiana.expedix.security.JwtTokenProvider;
import srl.ramaiana.expedix.service.AuthService;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

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

        String token = jwtTokenProvider.generateToken(user);
        UserProfileDTO userProfileDTO = userMapper.toUserProfileDTO(user);
        userProfileDTO.setToken(token);
        return userProfileDTO;
    }
}
