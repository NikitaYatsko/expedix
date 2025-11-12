package srl.ramaiana.expedix.model.dto.user;

import lombok.Data;
import srl.ramaiana.expedix.model.dto.role.RoleDTO;

import java.util.List;

@Data
public class UserProfileDTO {
    private Integer id;
    private String personalCode;
    private String fullName;
    private String email;
    private String phone;

    List<RoleDTO> roles;

}
