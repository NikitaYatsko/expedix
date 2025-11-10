package srl.ramaiana.expedix.model.dto.order;

import lombok.Data;
import srl.ramaiana.expedix.model.entity.enums.UnitTypeEnum;

import java.math.BigDecimal;

@Data
public class OrderItemDTO {
    private Long id;
    private Long productId;
    private String productName;
    private String brand;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private UnitTypeEnum typeOfUnit;
}
