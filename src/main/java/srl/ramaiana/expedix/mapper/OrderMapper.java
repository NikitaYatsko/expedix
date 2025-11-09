package srl.ramaiana.expedix.mapper;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import srl.ramaiana.expedix.model.dto.order.OrderDTO;
import srl.ramaiana.expedix.model.entity.Order;
import srl.ramaiana.expedix.model.entity.Settlement;
import srl.ramaiana.expedix.model.entity.Shop;
import srl.ramaiana.expedix.model.entity.User;
import srl.ramaiana.expedix.model.request.order.NewOrderRequest;
import srl.ramaiana.expedix.model.request.order.UpdateOrderDTO;

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

    public Order toEntity(NewOrderRequest request, User user, Settlement settlement, Shop shop) {
        if (request == null) {
            return null;
        }
        Order order = new Order();

        order.setUser(user);
        order.setSettlement(settlement);
        order.setShop(shop);
        order.setComment(request.getComment());
        return order;

    }
    

}
