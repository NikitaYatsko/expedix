package srl.ramaiana.expedix.service;

import jakarta.validation.constraints.NotNull;
import srl.ramaiana.expedix.model.dto.SettlementDTO;
import srl.ramaiana.expedix.model.request.settlement.NewSettlementRequest;
import srl.ramaiana.expedix.model.request.settlement.UpdateSettlementRequest;

import java.util.List;

public interface SettlementService
{
    SettlementDTO getSettlementById(@NotNull Integer id);
    SettlementDTO createSettlement(@NotNull Integer id ,@NotNull NewSettlementRequest settlementRequest);
    SettlementDTO updateSettlement(@NotNull Integer id, @NotNull UpdateSettlementRequest settlementRequest);
    List<SettlementDTO> getSettlements();
}
