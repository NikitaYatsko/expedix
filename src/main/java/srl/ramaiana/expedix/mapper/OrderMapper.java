package srl.ramaiana.expedix.mapper;

import org.springframework.stereotype.Component;
import srl.ramaiana.expedix.model.dto.order.OrderDTO;
import srl.ramaiana.expedix.model.entity.Order;

@Component
public class OrderMapper {
    public OrderDTO toOrderDto(Order order) {
        if (order == null) {
            return null;
        }
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId(order.getId());
        orderDTO.setAddress(order.getShop().getAddress());
        orderDTO.setCreated(order.getCreated());
        orderDTO.setShopName(order.getShop().getName());
        orderDTO.setOrderStatus(order.getOrderStatus());
        orderDTO.setCreatedBy(order.getUser().getFullName());
        orderDTO.setLocation(order.getSettlement().getName());
        orderDTO.setComment(order.getComment());
        return orderDTO;
    }

}
