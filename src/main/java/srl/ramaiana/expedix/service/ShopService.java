package srl.ramaiana.expedix.service;

import jakarta.validation.constraints.NotNull;
import srl.ramaiana.expedix.model.dto.ShopDTO;
import srl.ramaiana.expedix.model.entity.Shop;
import srl.ramaiana.expedix.model.request.ShopRequest;

public interface ShopService {
    ShopDTO getShopById(@NotNull Integer shopId);

    ShopDTO createShop(@NotNull ShopRequest shopRequest);
    ShopDTO updateShopById(@NotNull Integer shopId, @NotNull ShopRequest shopRequest);
}
