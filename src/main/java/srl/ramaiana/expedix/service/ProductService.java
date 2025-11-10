package srl.ramaiana.expedix.service;

import org.springframework.data.domain.Pageable;
import srl.ramaiana.expedix.model.dto.product.ProductDTO;
import srl.ramaiana.expedix.model.response.PaginationResponse;

public interface ProductService {
    PaginationResponse<ProductDTO> findAllProduct(Pageable pageable);
}
