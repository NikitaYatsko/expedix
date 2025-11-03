package srl.ramaiana.expedix.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import srl.ramaiana.expedix.exceptions.DataNotFoundException;
import srl.ramaiana.expedix.mapper.SettlementMapper;
import srl.ramaiana.expedix.model.dto.SettlementDTO;
import srl.ramaiana.expedix.model.entity.Settlement;
import srl.ramaiana.expedix.repository.SettlementRepository;
import srl.ramaiana.expedix.service.SettlementService;



@RequiredArgsConstructor
@Service
public class SettlementServiceImpl implements SettlementService {
    private final SettlementRepository settlementRepository;
    private final SettlementMapper settlementMapper;

    @Override
    public SettlementDTO getSettlementById(Integer id) {
        Settlement settlement = settlementRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("settlement with id " + id + " not found")
        );
        return settlementMapper.toDto(settlement);
    }
}
