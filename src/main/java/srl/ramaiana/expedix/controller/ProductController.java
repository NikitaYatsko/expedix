package srl.ramaiana.expedix.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import srl.ramaiana.expedix.model.dto.product.ProductDTO;
import srl.ramaiana.expedix.model.response.PaginationResponse;

import srl.ramaiana.expedix.service.ProductService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;


    @GetMapping
    public ResponseEntity<PaginationResponse<ProductDTO>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);
        PaginationResponse<ProductDTO> response = productService.findAllProduct(pageable);
        return ResponseEntity.ok(response);

    }

}
