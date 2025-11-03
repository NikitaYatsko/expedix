package srl.ramaiana.expedix.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import srl.ramaiana.expedix.model.dto.SettlementDTO;
import srl.ramaiana.expedix.model.entity.Settlement;
import srl.ramaiana.expedix.model.entity.User;
import srl.ramaiana.expedix.model.request.settlement.NewSettlementRequest;

import java.util.List;

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
        settlementDTO.setShopList(List.of());
        return settlementDTO;
    }

    public Settlement toEntity(NewSettlementRequest settlementRequest, User user) {
        if (settlementRequest == null) {
            return null;
        }

        Settlement settlement = new Settlement();
        settlement.setName(settlementRequest.getName());
        settlement.setUser(user); // привязываем к текущему пользователю

        // Если хочешь, чтобы пользователь знал о новом settlement:
        if (user.getSettlementList() != null) {
            user.getSettlementList().add(settlement);
        }

        return settlement;
    }



}
