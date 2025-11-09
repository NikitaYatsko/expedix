package srl.ramaiana.expedix.service;

import jakarta.validation.constraints.NotNull;
import org.hibernate.sql.Update;
import org.springframework.data.domain.Pageable;
import srl.ramaiana.expedix.model.dto.order.OrderDTO;
import srl.ramaiana.expedix.model.entity.enums.OrderStatusEnum;
import srl.ramaiana.expedix.model.request.order.NewOrderRequest;
import srl.ramaiana.expedix.model.request.order.UpdateOrderDTO;
import srl.ramaiana.expedix.model.response.PaginationResponse;


public interface OrderService {
    OrderDTO getOrderById(Long id);
    PaginationResponse<OrderDTO> getOrders(Pageable pageable);
    OrderDTO createOrder(@NotNull NewOrderRequest request);
    OrderDTO updateOrder(@NotNull Long id, @NotNull UpdateOrderDTO request);
}
