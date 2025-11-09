package srl.ramaiana.expedix.service.Impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import srl.ramaiana.expedix.exceptions.DataNotFoundException;
import srl.ramaiana.expedix.mapper.OrderMapper;
import srl.ramaiana.expedix.model.dto.order.OrderDTO;
import srl.ramaiana.expedix.model.entity.Order;
import srl.ramaiana.expedix.model.entity.Settlement;
import srl.ramaiana.expedix.model.entity.Shop;
import srl.ramaiana.expedix.model.entity.User;
import srl.ramaiana.expedix.model.request.order.NewOrderRequest;
import srl.ramaiana.expedix.model.request.order.UpdateOrderDTO;
import srl.ramaiana.expedix.model.response.PaginationResponse;
import srl.ramaiana.expedix.repository.OrderRepository;
import srl.ramaiana.expedix.repository.SettlementRepository;
import srl.ramaiana.expedix.repository.ShopRepository;
import srl.ramaiana.expedix.repository.UserRepository;
import srl.ramaiana.expedix.service.OrderService;

import java.util.List;


@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final SettlementRepository settlementRepository;
    private final ShopRepository shopRepository;

    @Override
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Order not found with id " + id)
        );
        return orderMapper.toOrderDto(order);
    }


    @Override
    public PaginationResponse<OrderDTO> getOrders(Pageable pageable) {
        Page<Order> orders = orderRepository.findAll(pageable);
        Page<OrderDTO> dtos = orders.map(orderMapper::toOrderDto);
        return new PaginationResponse<>(
                dtos.getContent(),
                new PaginationResponse.Pagination(
                        dtos.getTotalElements(),
                        pageable.getPageSize(),
                        dtos.getNumber() + 1,
                        dtos.getTotalPages()
                )
        );
    }

    @Transactional
    @Override
    public OrderDTO createOrder(NewOrderRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(
                () -> new DataNotFoundException("User not found with id " + request.getUserId())
        );
        Settlement settlement = settlementRepository.findById(request.getSettlementId()).orElseThrow(
                () -> new DataNotFoundException("Settlement not found with id " + request.getSettlementId())
        );
        Shop shop = shopRepository.findByIdAndIsDeletedFalse(request.getShopId()).orElseThrow(
                () -> new DataNotFoundException("Shop not found with id " + request.getShopId())
        );
        Order orderToSave = orderMapper.toEntity(request, user, settlement, shop);
        Order saved = orderRepository.save(orderToSave);
        return orderMapper.toOrderDto(saved);

    }

    @Transactional
    @Override
    public OrderDTO updateOrder(Long id, UpdateOrderDTO request) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Order not found with id " + id)
        );
        if (request.getStatus() != null) {
            order.setOrderStatus(request.getStatus());
        }
        return orderMapper.toOrderDto(order);
    }

    @Override
    public PaginationResponse<OrderDTO> findAllByUser(Integer userId, Pageable pageable) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new DataNotFoundException("User not found with id " + userId)
        );
        Page<Order> orders = orderRepository.findAllByUser(user, pageable);
        Page<OrderDTO> dtos = orders.map(orderMapper::toOrderDto);

        return new PaginationResponse<>(
                dtos.getContent(),
                new PaginationResponse.Pagination(
                        dtos.getTotalElements(),
                        pageable.getPageSize(),
                        dtos.getNumber() + 1,
                        dtos.getTotalPages()
                )
        );

    }
}
