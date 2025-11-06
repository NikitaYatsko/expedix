package srl.ramaiana.expedix.service;

import srl.ramaiana.expedix.model.dto.order.OrderDTO;

public interface OrderService {
    OrderDTO getOrderById(Long id);
}
