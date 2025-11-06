package srl.ramaiana.expedix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import srl.ramaiana.expedix.model.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
