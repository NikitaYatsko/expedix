package srl.ramaiana.expedix.controller;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import srl.ramaiana.expedix.model.dto.SettlementDTO;
import srl.ramaiana.expedix.model.request.settlement.NewSettlementRequest;
import srl.ramaiana.expedix.service.SettlementService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/settlements")
public class SettlementController {

    private final SettlementService settlementService;

    @GetMapping("/{id}")
    public ResponseEntity<SettlementDTO> getSettlementById(@PathVariable Integer id) {
        log.info("Getting settlement by id {}", id);
        return ResponseEntity.ok(settlementService.getSettlementById(id));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<SettlementDTO> saveSettlement(
            @PathVariable Integer userId,
            @RequestBody NewSettlementRequest request) {

        log.info("Saving settlement {} for user {}", request, userId);

        SettlementDTO dto = settlementService.createSettlement(userId, request);
        return ResponseEntity.ok(dto);
    }

}
