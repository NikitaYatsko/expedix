package srl.ramaiana.expedix.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import srl.ramaiana.expedix.model.entity.Settlement;


public interface SettlementRepository extends JpaRepository<Settlement, Integer> {
    boolean existsByName(String name);

}
