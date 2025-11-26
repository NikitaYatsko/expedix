package srl.ramaiana.expedix.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import srl.ramaiana.expedix.model.dto.settlement.SettlementDTO;
import srl.ramaiana.expedix.model.request.settlement.NewSettlementRequest;
import srl.ramaiana.expedix.model.request.settlement.UpdateSettlementRequest;
import srl.ramaiana.expedix.model.response.PaginationResponse;
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


    @GetMapping
    public ResponseEntity<PaginationResponse<SettlementDTO>> getAllSettlements(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        log.info("Getting all users, page {}, size {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(settlementService.getAllSettlements(pageable));
    }

    @PostMapping
    public ResponseEntity<SettlementDTO> saveSettlement(
            @RequestBody NewSettlementRequest request) {

        SettlementDTO dto = settlementService.createSettlement(request);
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
