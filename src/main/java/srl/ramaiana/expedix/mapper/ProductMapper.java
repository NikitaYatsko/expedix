package srl.ramaiana.expedix.mapper;

import org.springframework.stereotype.Component;
import srl.ramaiana.expedix.model.dto.product.ProductDTO;
import srl.ramaiana.expedix.model.entity.Product;

@Component
public class ProductMapper {
    public ProductDTO toDto(Product product) {
        if (product == null) {
            return null;
        }
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setBrand(product.getBrand());
        dto.setQuantityInStock(product.getQuantityInStock());
        dto.setTypeOfUnit(product.getTypeOfUnit());
        dto.setUnitPrice(product.getUnitPrice());
        return dto;
    }
}
