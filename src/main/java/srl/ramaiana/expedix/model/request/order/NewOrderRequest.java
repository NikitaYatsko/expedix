package srl.ramaiana.expedix.model.request.order;

import lombok.Data;

@Data
public class NewOrderRequest {
    private Integer userId;
    private Integer settlementId;
    private Integer shopId;
    private String comment;
}
