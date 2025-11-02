package srl.ramaiana.expedix.service;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import srl.ramaiana.expedix.model.dto.ShopDTO;
import srl.ramaiana.expedix.model.request.shop.ShopRequest;


public interface ShopService {
    ShopDTO getShopById(@NotNull Integer shopId);

    ShopDTO createShop(@NotNull ShopRequest shopRequest);
    ShopDTO updateShopById(@NotNull Integer shopId, @NotNull ShopRequest shopRequest);
    void deleteShopById(@NotNull Integer shopId);
    Page<ShopDTO> getAllShops(Pageable pageable);
}
