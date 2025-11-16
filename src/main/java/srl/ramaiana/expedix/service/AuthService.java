package srl.ramaiana.expedix.service;

import srl.ramaiana.expedix.model.request.user.LoginRequest;
import srl.ramaiana.expedix.model.dto.user.UserProfileDTO;
import srl.ramaiana.expedix.model.request.user.RegistrationUserRequest;

public interface AuthService {

    UserProfileDTO login(LoginRequest loginRequest);
    UserProfileDTO refreshAccessToken(String refreshToken);
    UserProfileDTO registerUser(RegistrationUserRequest registrationUserRequest);

}
