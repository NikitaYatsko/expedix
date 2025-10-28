package srl.ramaiana.expedix.repository;

import org.springframework.data.repository.CrudRepository;
import srl.ramaiana.expedix.model.entity.Shop;

import java.util.Optional;

public interface ShopRepository extends CrudRepository<Shop, Integer> {
    Optional<Shop> findByIdAndIsDeletedFalse(Integer id);
}
