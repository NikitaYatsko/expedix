package srl.ramaiana.expedix.service;

import srl.ramaiana.expedix.model.dto.user.LoginRequest;
import srl.ramaiana.expedix.model.dto.user.UserProfileDTO;

public interface AuthService {

    UserProfileDTO login(LoginRequest loginRequest);
    UserProfileDTO refreshAccessToken(String refreshToken);

}
