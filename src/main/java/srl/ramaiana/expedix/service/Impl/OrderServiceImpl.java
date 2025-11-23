package srl.ramaiana.expedix.service.Impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import srl.ramaiana.expedix.exceptions.DataNotFoundException;
import srl.ramaiana.expedix.mapper.OrderMapper;
import srl.ramaiana.expedix.model.dto.order.OrderDTO;
import srl.ramaiana.expedix.model.entity.*;
import srl.ramaiana.expedix.model.request.order.NewOrderRequest;
import srl.ramaiana.expedix.model.request.order.UpdateOrderDTO;
import srl.ramaiana.expedix.model.response.PaginationResponse;
import srl.ramaiana.expedix.repository.*;
import srl.ramaiana.expedix.security.validation.AccessValidator;
import srl.ramaiana.expedix.service.OrderService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final SettlementRepository settlementRepository;
    private final ShopRepository shopRepository;
    private final ProductRepository productRepository;
    private final AccessValidator accessValidator;


    @Override
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Order not found with id " + id)
        );
        accessValidator.validateDirectorOrOwnerAccess(order.getUser().getEmail());
        return orderMapper.toOrderDto(order);
    }


    @Override
    public PaginationResponse<OrderDTO> getOrders(Pageable pageable) {
        Page<Order> orders = orderRepository.findAll(pageable);
        Page<OrderDTO> dtos = orders.map(orderMapper::toOrderDto);accessValidator.validateDirectorOrOwnerAccess(orders.getContent().getFirst().getUser().getEmail());
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
    public OrderDTO createOrder(String username, NewOrderRequest request) {
        User user = userRepository.findUserByEmailAndIsDeletedFalse(username).orElseThrow(
                () -> new DataNotFoundException("User not found with name " + username)
        );
        Settlement settlement = settlementRepository.findById(request.getSettlementId()).orElseThrow(
                () -> new DataNotFoundException("Settlement not found with id " + request.getSettlementId())
        );
        Shop shop = shopRepository.findByIdAndIsDeletedFalse(request.getShopId()).orElseThrow(
                () -> new DataNotFoundException("Shop not found with id " + request.getShopId())
        );

        Order order = new Order();
        order.setUser(user);
        order.setShop(shop);
        order.setSettlement(settlement);
        order.setComment(request.getComment());

        List<Long> productIds = new ArrayList<>();
        for (var itemRequest : request.getItems()) {
            productIds.add(itemRequest.getProductId());
        }


        List<Product> allProducts = productRepository.findByIds(productIds);
        Map<Long, Product> productMap = new HashMap<>();
        for (Product product : allProducts) {
            productMap.put(product.getId(), product);
        }

        for (var itemRequest : request.getItems()) {

            Product product = productMap.get(itemRequest.getProductId());
            if (product == null) {
                throw new DataNotFoundException("Product not found with id " + itemRequest.getProductId());
            }

            if (product.getQuantityInStock() < itemRequest.getQuantity()) {
                throw new DataNotFoundException("Product not enough stock");
            }

            order.addOrderItem(product, itemRequest.getQuantity());
            product.setQuantityInStock(product.getQuantityInStock() - itemRequest.getQuantity());

        }

        order.calculateTotalPrice();
        Order savedOrder = orderRepository.save(order);

        return orderMapper.toOrderDto(savedOrder);
    }

    @Transactional
    @Override
    public OrderDTO updateOrder(Long id, UpdateOrderDTO request) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Order not found with id " + id)
        );
        accessValidator.validateDirectorOrOwnerAccess(order.getUser().getEmail());
        if (request.getStatus() != null) {
            order.setOrderStatus(request.getStatus());
        }
        if (request.getComment() != null) {
            order.setComment(order.getComment() + ", " + request.getComment());
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
