package srl.ramaiana.expedix.mapper;

import org.springframework.stereotype.Component;
import srl.ramaiana.expedix.model.dto.shop.ShopDTO;
import srl.ramaiana.expedix.model.dto.shop.ShopMappedBySettlementDTO;
import srl.ramaiana.expedix.model.entity.Shop;
import srl.ramaiana.expedix.model.request.shop.ShopRequest;

@Component
public class ShopMapper {
    public ShopMappedBySettlementDTO toMappedBySettlementDto(Shop shop) {
        if (shop == null) {
            return null;
        }
        ShopMappedBySettlementDTO dto = new ShopMappedBySettlementDTO();
        dto.setId(shop.getId());
        dto.setName(shop.getName());
        dto.setAddress(shop.getAddress());
        return dto;
    }

    public ShopDTO toDto(Shop shop){
        if (shop == null) {
            return null;
        }
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setId(shop.getId());
        shopDTO.setName(shop.getName());
        shopDTO.setAddress(shop.getAddress());
        if (shop.getSettlement() != null && shop.getSettlement().getName() != null) {
            shopDTO.setLocatedIn(shop.getSettlement().getName());
        } else {
            shopDTO.setLocatedIn("Не указан");
        }

        return shopDTO;
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
