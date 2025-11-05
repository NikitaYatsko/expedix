package srl.ramaiana.expedix.model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class SettlementMappedByUserDTO {
    private Integer id;
    private String name;
    private List<ShopMappedBySettlementDTO> shopList = new ArrayList<>();
}
