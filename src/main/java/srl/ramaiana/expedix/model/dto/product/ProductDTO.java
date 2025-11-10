package srl.ramaiana.expedix.model.dto.product;

import lombok.Data;
import srl.ramaiana.expedix.model.entity.enums.UnitTypeEnum;

import java.math.BigDecimal;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String brand;
    private Integer quantityInStock;
    private BigDecimal unitPrice;
    private UnitTypeEnum typeOfUnit;
}
