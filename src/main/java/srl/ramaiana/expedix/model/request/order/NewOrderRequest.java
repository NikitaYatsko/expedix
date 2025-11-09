package srl.ramaiana.expedix.model.request.order;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NewOrderRequest {
    @NotNull
    private Integer userId;
    @NotNull
    private Integer settlementId;
    @NotNull
    private Integer shopId;
    private String comment;
}
