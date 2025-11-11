package srl.ramaiana.expedix.model.request.order;

import lombok.Data;
import srl.ramaiana.expedix.model.entity.enums.OrderStatusEnum;

@Data
public class UpdateOrderDTO {
    private OrderStatusEnum status;
    private String comment;
}
