package srl.ramaiana.expedix.model.dto.order;

import lombok.Data;
import srl.ramaiana.expedix.model.entity.Product;
import srl.ramaiana.expedix.model.entity.enums.OrderStatusEnum;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private long orderId;
    private String createdBy;
    private String location;
    private String shopName;
    private String address;
    private LocalDateTime created;
    private OrderStatusEnum orderStatus = OrderStatusEnum.NEW;
    private String comment;
    private List<Product> products;


}
