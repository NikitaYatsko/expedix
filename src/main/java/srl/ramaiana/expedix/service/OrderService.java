package srl.ramaiana.expedix.service;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import srl.ramaiana.expedix.model.dto.order.OrderDTO;


import srl.ramaiana.expedix.model.request.order.NewOrderRequest;
import srl.ramaiana.expedix.model.request.order.UpdateOrderDTO;
import srl.ramaiana.expedix.model.response.PaginationResponse;


public interface OrderService {
    OrderDTO getOrderById(Long id);

    PaginationResponse<OrderDTO> getOrders(Pageable pageable);

    OrderDTO createOrder(@NotNull String username, @NotNull NewOrderRequest request);

    OrderDTO updateOrder(@NotNull Long id, @NotNull UpdateOrderDTO request);

    OrderDTO getMyOrderById(Long orderId);

    PaginationResponse<OrderDTO> findAllByUser(@NotNull String email, Pageable pageable);
}
