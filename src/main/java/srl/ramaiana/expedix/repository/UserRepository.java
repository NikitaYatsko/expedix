package srl.ramaiana.expedix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import srl.ramaiana.expedix.model.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByIdAndIsDeletedFalse(Integer id);

    boolean existsByEmail(String email);
}
