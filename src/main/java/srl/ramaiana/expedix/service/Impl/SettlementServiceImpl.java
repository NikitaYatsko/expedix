package srl.ramaiana.expedix.service.Impl;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import srl.ramaiana.expedix.constants.ApiErrorMessage;
import srl.ramaiana.expedix.exceptions.DataExistsException;
import srl.ramaiana.expedix.exceptions.DataNotFoundException;
import srl.ramaiana.expedix.mapper.SettlementMapper;
import srl.ramaiana.expedix.model.dto.settlement.SettlementDTO;
import srl.ramaiana.expedix.model.entity.Settlement;
import srl.ramaiana.expedix.model.request.settlement.NewSettlementRequest;
import srl.ramaiana.expedix.model.request.settlement.UpdateSettlementRequest;
import srl.ramaiana.expedix.model.response.PaginationResponse;
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
                () -> new DataNotFoundException(ApiErrorMessage.SETTLEMENT_NOT_FOUND.getMessage())
        );
        return settlementMapper.toDto(settlement);
    }

    @Transactional
    @Override
    public SettlementDTO createSettlement(@NotNull NewSettlementRequest settlementRequest) {
        if (settlementRepository.existsByName(settlementRequest.getName())) {
            throw new DataExistsException(ApiErrorMessage.SETTLEMENT_ALREADY_EXISTS.getMessage());
        }

        Settlement settlement = settlementMapper.toEntity(settlementRequest);
        return settlementMapper.toDto(settlement);

    }

    @Transactional
    @Override
    public SettlementDTO updateSettlement(@NotNull Integer id, @NotNull UpdateSettlementRequest settlementRequest) {
        Settlement settlement = settlementRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(ApiErrorMessage.SETTLEMENT_NOT_FOUND.getMessage()));

        Settlement settlementToSave = settlementMapper.updateSettlement(settlementRequest, settlement);
        Settlement savedSettlement = settlementRepository.save(settlementToSave);

        return settlementMapper.toDto(savedSettlement);
    }

    @Override
    public PaginationResponse<SettlementDTO> getAllSettlements(Pageable pageable) {
        Page<Settlement> settlements = settlementRepository.findAll(pageable);
        Page<SettlementDTO> dto = settlements.map(settlementMapper::toDto);

        return new PaginationResponse<>(
                dto.getContent(),
                new PaginationResponse.Pagination(
                        dto.getTotalElements(),
                        pageable.getPageSize(),
                        dto.getNumber() + 1,
                        dto.getTotalPages()
                )
        );
    }

}
