package srl.ramaiana.expedix.model.dto.user;

import lombok.Data;
import srl.ramaiana.expedix.model.dto.role.RoleDTO;

import java.util.List;

@Data
public class UserOnlyDTO {
    private Long userId;
    private String personalCode;
    private String fullName;
    private String phoneNumber;
    private String email;
    private List<RoleDTO> roleList;
    private Boolean isDeleted;
}
