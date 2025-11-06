package srl.ramaiana.expedix.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import srl.ramaiana.expedix.model.entity.Settlement;


public interface SettlementRepository extends JpaRepository<Settlement, Integer> {
    boolean existsByName(String name);
    @Query("Select s from Settlement s")
    Page<Settlement> findSettlementsOnly(Pageable pageable);
}
