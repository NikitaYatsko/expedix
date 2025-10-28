package srl.ramaiana.expedix.mapper;

import org.springframework.stereotype.Component;
import srl.ramaiana.expedix.model.dto.ShopDTO;
import srl.ramaiana.expedix.model.entity.Shop;
import srl.ramaiana.expedix.model.request.ShopRequest;

@Component
public class ShopMapper {
    public ShopDTO toDto(Shop shop) {
        if (shop == null) {
            return null;
        }
        ShopDTO dto = new ShopDTO();
        dto.setId(shop.getId());
        dto.setName(shop.getName());
        dto.setAddress(shop.getAddress());
        return dto;
    }

    public Shop toEntity(ShopRequest dto) {
        if (dto == null) {
            return null;
        }
        Shop shop = new Shop();
        shop.setName(dto.getName());
        shop.setAddress(dto.getAddress());
        return shop;
    }
}
