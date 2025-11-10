package srl.ramaiana.expedix.mapper;


import org.springframework.stereotype.Component;
import srl.ramaiana.expedix.model.dto.order.OrderDTO;
import srl.ramaiana.expedix.model.dto.order.OrderItemDTO;
import srl.ramaiana.expedix.model.entity.*;
import srl.ramaiana.expedix.model.request.order.NewOrderRequest;

import java.util.stream.Collectors;


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

        orderDTO.setOrderItems(order.getOrderItems()
                .stream()
                .map(this::toOrderItemDto)
                .collect(Collectors.toList()));

        orderDTO.setTotalPrice(order.getTotalPrice());

        return orderDTO;
    }

    public OrderItemDTO toOrderItemDto(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setId(orderItem.getId());
        orderItemDTO.setProductId(orderItem.getProduct().getId());
        orderItemDTO.setQuantity(orderItem.getQuantity());
        orderItemDTO.setUnitPrice(orderItem.getUnitPrice());
        orderItemDTO.setTotalPrice(orderItem.getTotalPrice());
        orderItemDTO.setBrand(orderItem.getProduct().getBrand());
        orderItemDTO.setProductName(orderItem.getProduct().getName());
        orderItemDTO.setTypeOfUnit(orderItem.getProduct().getTypeOfUnit());
        return orderItemDTO;

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
