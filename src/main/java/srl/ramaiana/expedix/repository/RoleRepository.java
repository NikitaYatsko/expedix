package srl.ramaiana.expedix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import srl.ramaiana.expedix.model.entity.Role;
import srl.ramaiana.expedix.model.entity.enums.RolesEnum;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findByUserSystemRole(RolesEnum role);
}
