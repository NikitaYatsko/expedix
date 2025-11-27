package srl.ramaiana.expedix.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import srl.ramaiana.expedix.model.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Page<User> findAll(Pageable pageable);
    Optional<User> findByIdAndIsDeletedFalse(Integer id);
    Optional<User> findUserByEmailAndIsDeletedFalse(String email);
    Optional<User> findUserByEmail(String email);
    boolean existsByEmail(String email);
}
 