package srl.ramaiana.expedix.model.dto.user;



import lombok.Data;
import srl.ramaiana.expedix.model.dto.role.RoleDTO;
import srl.ramaiana.expedix.model.dto.settlement.SettlementMappedByUserDTO;

import java.util.List;

@Data
public class UserDTO {
    private Long userId;
    private String personalCode;
    private String fullName;
    private String phoneNumber;
    private String email;
    private Boolean isDeleted;
    private List<RoleDTO> roleList;
    private List<SettlementMappedByUserDTO> settlementList;

}
