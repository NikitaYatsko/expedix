package srl.ramaiana.expedix.service.Impl;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import srl.ramaiana.expedix.exceptions.DataNotFoundException;
import srl.ramaiana.expedix.mapper.ShopMapper;
import srl.ramaiana.expedix.model.dto.ShopDTO;
import srl.ramaiana.expedix.model.entity.Shop;
import srl.ramaiana.expedix.model.request.shop.ShopRequest;
import srl.ramaiana.expedix.repository.ShopRepository;
import srl.ramaiana.expedix.service.ShopService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;

    @Override
    public ShopDTO getShopById(@NotNull Integer shopId) {
        Shop shop = shopRepository.findByIdAndIsDeletedFalse(shopId).orElseThrow(
                () -> new DataNotFoundException("Shop not found"));
        return shopMapper.toDto(shop);
    }

    @Override
    public ShopDTO createShop(@NotNull ShopRequest shopRequest) {
        Shop shop = shopMapper.toEntity(shopRequest);
        shopRepository.save(shop);
        return shopMapper.toDto(shop);
    }


    @Override
    public ShopDTO updateShopById(@NotNull Integer shopId, @NotNull ShopRequest shopRequest) {
        Shop shop = shopRepository.findByIdAndIsDeletedFalse(shopId).orElseThrow(
                () -> new DataNotFoundException("Shop not found"));
        shop.setAddress(shopRequest.getAddress());
        shop.setName(shopRequest.getName());
        shopRepository.save(shop);
        return shopMapper.toDto(shop);
    }

    @Override
    public void deleteShopById(Integer shopId) {
        Shop shop = shopRepository.findByIdAndIsDeletedFalse(shopId).orElseThrow(
                () -> new DataNotFoundException("Shop not found"));
        shop.setIsDeleted(true);
        shopRepository.save(shop);
    }

    @Override
    public List<ShopDTO> getAllShops() {
        List<Shop> shops = shopRepository.findAll();
        return shops.stream()
                .map(shopMapper::toDto)
                .collect(Collectors.toList());
    }

}
