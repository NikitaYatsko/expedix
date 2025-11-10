package srl.ramaiana.expedix.model.request.order;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderItemRequest {
    @NotNull
    private Long productId;
    @NotNull
    private Integer quantity;
}
