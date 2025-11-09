package srl.ramaiana.expedix.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import srl.ramaiana.expedix.model.entity.Order;
import srl.ramaiana.expedix.model.entity.User;


public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByUser(User user, Pageable pageable);

}
