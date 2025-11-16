package srl.ramaiana.expedix.model.dto.user;

import lombok.Data;
import srl.ramaiana.expedix.model.dto.role.RoleDTO;

import java.util.List;

@Data
public class UserProfileDTO {
    private Long id;
    private String personalCode;
    private String fullName;
    private String email;
    private String phone;
    private String token;
    private String refreshToken;

    List<RoleDTO> roles;

}
