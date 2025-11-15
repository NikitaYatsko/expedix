package srl.ramaiana.expedix.service.Impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import srl.ramaiana.expedix.constants.ApiErrorMessage;
import srl.ramaiana.expedix.exceptions.DataNotFoundException;
import srl.ramaiana.expedix.model.entity.RefreshToken;
import srl.ramaiana.expedix.model.entity.User;
import srl.ramaiana.expedix.repository.RefreshTokenRepository;
import srl.ramaiana.expedix.service.RefreshTokenService;
import srl.ramaiana.expedix.utils.ApiUtils;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
@Slf4j
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken generateOrUpdateRefreshToken(User user) {
        return refreshTokenRepository.findByUserId(user.getId()).map(
                refreshToken -> {
                    refreshToken.setCreated(LocalDateTime.now());
                    refreshToken.setToken(ApiUtils.generateUuidWithoutDash());
                    return refreshTokenRepository.save(refreshToken);
                }).orElseGet(() -> {
            RefreshToken newToken = new RefreshToken();
            newToken.setUser(user);
            newToken.setCreated(LocalDateTime.now());
            newToken.setToken(ApiUtils.generateUuidWithoutDash());
        });
    }

    @Override
    public RefreshToken validateAndRefreshToken(String refreshRefreshToken) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshRefreshToken).orElseThrow(
                () -> new DataNotFoundException(ApiErrorMessage.NOT_FOUND_REFRESH_TOKEN.getMessage())
        );
        refreshToken.setCreated(LocalDateTime.now());
        refreshToken.setToken(ApiUtils.generateUuidWithoutDash());
        return refreshTokenRepository.save(refreshToken);

    }
}
