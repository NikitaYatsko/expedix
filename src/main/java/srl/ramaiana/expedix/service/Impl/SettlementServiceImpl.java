package srl.ramaiana.expedix.service.Impl;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import srl.ramaiana.expedix.exceptions.DataExistsException;
import srl.ramaiana.expedix.exceptions.DataNotFoundException;
import srl.ramaiana.expedix.mapper.SettlementMapper;
import srl.ramaiana.expedix.model.dto.SettlementDTO;
import srl.ramaiana.expedix.model.entity.Settlement;
import srl.ramaiana.expedix.model.entity.User;
import srl.ramaiana.expedix.model.request.settlement.NewSettlementRequest;
import srl.ramaiana.expedix.repository.SettlementRepository;
import srl.ramaiana.expedix.repository.UserRepository;
import srl.ramaiana.expedix.service.SettlementService;


@RequiredArgsConstructor
@Service
public class SettlementServiceImpl implements SettlementService {
    private final SettlementRepository settlementRepository;
    private final SettlementMapper settlementMapper;
    private final UserRepository userRepository;

    @Override
    public SettlementDTO getSettlementById(Integer id) {
        Settlement settlement = settlementRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("settlement with id " + id + " not found")
        );
        return settlementMapper.toDto(settlement);
    }

    @Override
    public SettlementDTO createSettlement(@NotNull Integer id, @NotNull NewSettlementRequest settlementRequest) {
        if (settlementRepository.existsByName(settlementRequest.getName())) {
            throw new DataExistsException("settlement with name " + settlementRequest.getName() + " already exists");
        }
        User user = userRepository.findByIdAndIsDeletedFalse(id).orElseThrow(
                () -> new DataNotFoundException("user with id " + id + " not found")
        );
        Settlement settlement = settlementMapper.toEntity(settlementRequest, user);
        settlementRepository.save(settlement);
        return settlementMapper.toDto(settlement);

    }
}
