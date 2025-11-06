package srl.ramaiana.expedix.service;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import srl.ramaiana.expedix.model.dto.settlement.SettlementDTO;
import srl.ramaiana.expedix.model.request.settlement.NewSettlementRequest;
import srl.ramaiana.expedix.model.request.settlement.UpdateSettlementRequest;
import srl.ramaiana.expedix.model.response.PaginationResponse;


public interface SettlementService {
    SettlementDTO getSettlementById(@NotNull Integer id);

    SettlementDTO createSettlement(@NotNull Integer id, @NotNull NewSettlementRequest settlementRequest);

    SettlementDTO updateSettlement(@NotNull Integer id, @NotNull UpdateSettlementRequest settlementRequest);

    PaginationResponse<SettlementDTO> getAllSettlements(Pageable pageable);
}
