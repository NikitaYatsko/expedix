package srl.ramaiana.expedix.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import srl.ramaiana.expedix.model.dto.SettlementDTO;
import srl.ramaiana.expedix.model.entity.Settlement;

@RequiredArgsConstructor
@Component
public class SettlementMapper {

    private final ShopMapper shopMapper;

    public SettlementDTO toDto(Settlement settlement) {

        if (settlement == null) {
            return null;
        }

        SettlementDTO settlementDTO = new SettlementDTO();
        settlementDTO.setId(settlement.getId());
        settlementDTO.setName(settlement.getName());
        settlementDTO.setShopList(settlement.getShops()
                .stream()
                .map(shopMapper::toDto).toList());
        return settlementDTO;
    }
}
