package srl.ramaiana.expedix.model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SettlementDTO {
    private Integer id;
    private String name;
    private String assignedTo;
    private List<ShopMappedBySettlementDTO> shopList = new ArrayList<>();
}
