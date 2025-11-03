package srl.ramaiana.expedix.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import srl.ramaiana.expedix.model.dto.SettlementDTO;
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
}
