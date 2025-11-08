package srl.ramaiana.expedix.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import srl.ramaiana.expedix.model.dto.order.OrderDTO;
import srl.ramaiana.expedix.model.entity.Order;
import srl.ramaiana.expedix.model.response.PaginationResponse;

import java.util.List;

public interface OrderService {
    OrderDTO getOrderById(Long id);
    PaginationResponse<OrderDTO> getOrders(Pageable pageable);
}
