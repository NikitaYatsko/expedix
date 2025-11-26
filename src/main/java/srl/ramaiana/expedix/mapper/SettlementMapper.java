package srl.ramaiana.expedix.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import srl.ramaiana.expedix.model.dto.settlement.SettlementDTO;
import srl.ramaiana.expedix.model.entity.Settlement;
import srl.ramaiana.expedix.model.request.settlement.NewSettlementRequest;
import srl.ramaiana.expedix.model.request.settlement.UpdateSettlementRequest;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
        settlementDTO.setShopList(settlement.getShops() != null ?
                settlement.getShops()
                        .stream()
                        .map(shopMapper::toMappedBySettlementDto).collect(Collectors.toList()) :
                new ArrayList<>());
        return settlementDTO;
    }

    public Settlement toEntity(NewSettlementRequest settlementRequest) {
        if (settlementRequest == null) {
            return null;
        }

        Settlement settlement = new Settlement();
        settlement.setName(settlementRequest.getName());


        return settlement;
    }

    public Settlement updateSettlement(UpdateSettlementRequest request, Settlement settlement) {
        if (settlement == null) return null;

        if (request.getName() != null) {
            settlement.setName(request.getName());
        }

        return settlement;
    }

    public SettlementDTO toSettlementMappedByUser(Settlement settlement) {
        if (settlement == null) return null;

        SettlementDTO settlementMappedByUserDTO = new SettlementDTO();
        settlementMappedByUserDTO.setId(settlement.getId());
        settlementMappedByUserDTO.setName(settlement.getName());
        settlementMappedByUserDTO.setShopList(settlement.getShops() != null ?
                settlement.getShops()
                        .stream()
                        .map(shopMapper::toMappedBySettlementDto).collect(Collectors.toList()) :
                new ArrayList<>());
        return settlementMappedByUserDTO;
    }

}
