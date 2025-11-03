package srl.ramaiana.expedix.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import srl.ramaiana.expedix.model.dto.ShopDTO;
import srl.ramaiana.expedix.model.request.shop.ShopRequest;
import srl.ramaiana.expedix.model.response.PaginationResponse;
import srl.ramaiana.expedix.service.ShopService;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/shops")
public class ShopController {

    private final ShopService shopService;

    @GetMapping("/{id}")
    public ResponseEntity<ShopDTO> getShopById(@PathVariable Integer id) {
        log.info("Getting shop by id {}", id);
        return ResponseEntity.ok(shopService.getShopById(id));
    }

    @GetMapping
    public ResponseEntity<PaginationResponse<ShopDTO>> getAllShops(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        log.info("Getting all shops");
        PaginationResponse<ShopDTO> response = shopService.getAllShops(pageable);
        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<ShopDTO> createShop(@RequestBody @Valid ShopRequest shopRequest) {
        log.info("Requesting create shop {}", shopRequest);
        return ResponseEntity.ok(shopService.createShop(shopRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShopDTO> updateShop(@RequestBody @Valid ShopRequest shopRequest, @PathVariable Integer id) {
        log.info("Requesting update shop {}", shopRequest);
        shopService.updateShopById(id, shopRequest);
        return ResponseEntity.ok(shopService.getShopById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeShop(@PathVariable Integer id) {
        log.info("Removing shop {}", id);
        shopService.deleteShopById(id);
        return ResponseEntity.ok().build();
    }


}
