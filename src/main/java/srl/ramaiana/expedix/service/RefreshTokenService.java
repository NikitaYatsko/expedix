package srl.ramaiana.expedix.service;

import srl.ramaiana.expedix.model.entity.RefreshToken;
import srl.ramaiana.expedix.model.entity.User;

public interface RefreshTokenService {
    RefreshToken generateOrUpdateRefreshToken(User user);
}
