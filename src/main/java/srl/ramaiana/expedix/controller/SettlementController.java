package srl.ramaiana.expedix.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import srl.ramaiana.expedix.model.dto.SettlementDTO;
import srl.ramaiana.expedix.model.request.settlement.NewSettlementRequest;
import srl.ramaiana.expedix.model.request.settlement.UpdateSettlementRequest;
import srl.ramaiana.expedix.service.SettlementService;

import java.util.List;

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
    @GetMapping
    public ResponseEntity<List<SettlementDTO>> getAllSettlements() {
        log.info("Getting all settlements");
        return ResponseEntity.ok(settlementService.getSettlements());
    }

    @PostMapping()
    public ResponseEntity<SettlementDTO> saveSettlement(
            @RequestBody NewSettlementRequest request) {

        //TODO replace with real user,
        int id = 1;
        log.info("Saving settlement {} for user {}", request, id);

        SettlementDTO dto = settlementService.createSettlement(id, request);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SettlementDTO> updateSettlement(
            @PathVariable Integer id,
            @RequestBody UpdateSettlementRequest request
    ) {
        log.info("Updating settlement with id {} using request {}", id, request);
        SettlementDTO updatedSettlement = settlementService.updateSettlement(id, request);
        return ResponseEntity.ok(updatedSettlement);
    }


}
