package srl.ramaiana.expedix.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import srl.ramaiana.expedix.model.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @EntityGraph(attributePaths = {"settlementList"})
    Page<User> findAll(Pageable pageable);

    @Query("Select u from User u")
    Page<User> findUsersOnly(Pageable pageable);

    Optional<User> findByIdAndIsDeletedFalse(Integer id);
    Optional<User> findByFullName(String fullName);
    Optional<User> findUserByEmailAndIsDeletedFalse(String email);
    Optional<User> findUserByEmail(String email);

    boolean existsByEmail(String email);
}
 