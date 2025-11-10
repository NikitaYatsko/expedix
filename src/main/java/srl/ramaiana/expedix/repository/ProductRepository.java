package srl.ramaiana.expedix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import srl.ramaiana.expedix.model.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
