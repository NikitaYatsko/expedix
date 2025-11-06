package srl.ramaiana.expedix.service.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import srl.ramaiana.expedix.exceptions.DataNotFoundException;
import srl.ramaiana.expedix.mapper.OrderMapper;
import srl.ramaiana.expedix.model.dto.order.OrderDTO;
import srl.ramaiana.expedix.model.entity.Order;
import srl.ramaiana.expedix.repository.OrderRepository;
import srl.ramaiana.expedix.service.OrderService;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    @Override
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Order not found with id " + id)
        );
        return orderMapper.toOrderDto(order);
    }
}
