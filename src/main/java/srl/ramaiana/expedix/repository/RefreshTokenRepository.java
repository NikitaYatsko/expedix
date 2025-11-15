package srl.ramaiana.expedix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import srl.ramaiana.expedix.model.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUserId(Long userId);

}
