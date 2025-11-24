package srl.ramaiana.expedix.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import srl.ramaiana.expedix.mapper.ProductMapper;
import srl.ramaiana.expedix.model.dto.product.ProductDTO;
import srl.ramaiana.expedix.model.entity.Product;
import srl.ramaiana.expedix.model.response.PaginationResponse;
import srl.ramaiana.expedix.repository.ProductRepository;
import srl.ramaiana.expedix.security.validation.AccessValidator;
import srl.ramaiana.expedix.service.ProductService;


@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final AccessValidator accessValidator;

    @Override
    public PaginationResponse<ProductDTO> findAllProduct(Pageable pageable) {
        accessValidator.validateProductReadAccess();
        Page<Product> products = productRepository.findAll(pageable);
        Page<ProductDTO> dtos = products.map(productMapper::toDto);

        return new PaginationResponse<>(
                dtos.getContent(),
                new PaginationResponse.Pagination(
                        dtos.getTotalElements(),
                        pageable.getPageSize(),
                        dtos.getNumber() + 1,
                        dtos.getTotalPages()
                )
        );

    }
}
