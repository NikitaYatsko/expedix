package srl.ramaiana.expedix.model.dto;


import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Long userId;
    private String personalCode;
    private String fullName;
    private String phoneNumber;
    private String email;
    private Boolean isDeleted;
    private List<SettlementDTO> settlementList;
}
