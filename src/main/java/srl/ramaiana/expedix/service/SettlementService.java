package srl.ramaiana.expedix.service;

import jakarta.validation.constraints.NotNull;
import srl.ramaiana.expedix.model.dto.SettlementDTO;
import srl.ramaiana.expedix.model.request.settlement.NewSettlementRequest;

public interface SettlementService
{
    SettlementDTO getSettlementById(@NotNull Integer id);
}
