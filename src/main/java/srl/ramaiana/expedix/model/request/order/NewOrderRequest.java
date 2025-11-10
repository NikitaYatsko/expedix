package srl.ramaiana.expedix.model.request.order;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class NewOrderRequest {
    @NotNull
    private Integer userId;
    @NotNull
    private Integer settlementId;
    @NotNull
    private Integer shopId;
    private List<OrderItemRequest> items;
    private String comment;
}
