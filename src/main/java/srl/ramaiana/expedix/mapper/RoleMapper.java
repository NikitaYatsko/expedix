package srl.ramaiana.expedix.mapper;

import org.springframework.stereotype.Component;
import srl.ramaiana.expedix.model.dto.role.RoleDTO;
import srl.ramaiana.expedix.model.entity.Role;


@Component
public class RoleMapper {

    public RoleDTO toDto(Role role) {
        if (role == null) return null;

        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setName(role.getName());
        return dto;
    }
}
