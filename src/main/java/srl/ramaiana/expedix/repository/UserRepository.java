package srl.ramaiana.expedix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import srl.ramaiana.expedix.model.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
}
