package srl.ramaiana.expedix.model.dto.order;

import lombok.Data;
import srl.ramaiana.expedix.model.entity.enums.OrderStatusEnum;

import java.time.LocalDateTime;

@Data
public class OrderDTO {
    private long Id;
    private String createdBy;
    private String location;
    private String shopName;
    private String address;
    private LocalDateTime created;
    private OrderStatusEnum orderStatus = OrderStatusEnum.NEW;
    private String comment;


}
