package srl.ramaiana.expedix.service.Impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import srl.ramaiana.expedix.constants.ApiErrorMessage;
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
import srl.ramaiana.expedix.utils.ApiUtils;

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
                () -> new DataNotFoundException(ApiErrorMessage.ORDER_NOT_FOUND.getMessage())
        );
        return orderMapper.toOrderDto(order);
    }

    public OrderDTO getOrderByIdAndCheckOwner(Long orderId, String email) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new DataNotFoundException("Order not found"));

        accessValidator.validateDirectorOrOwnerAccess(email);
        return orderMapper.toOrderDto(order);
    }

    public OrderDTO updateOrderByOwner(Long orderId, String email, UpdateOrderDTO request) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new DataNotFoundException(ApiErrorMessage.ORDER_NOT_FOUND.getMessage())
        );
        accessValidator.validateDirectorOrOwnerAccess(email);
        if (request.getStatus() != null) {
            order.setOrderStatus(request.getStatus());
        }

        return getOrderDTO(request, order);

    }

    private OrderDTO getOrderDTO(UpdateOrderDTO request, Order order) {
        if (request.getComment() != null && !request.getComment().isBlank()) {
            String oldComment = order.getComment() != null ? order.getComment() : "";
            String newComment = oldComment.isEmpty() ? request.getComment() : oldComment + ", " + request.getComment();
            order.setComment(newComment);
        }

        orderRepository.save(order);
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
    public OrderDTO createOrder(String username, NewOrderRequest request) {
        User user = userRepository.findUserByEmailAndIsDeletedFalse(username).orElseThrow(
                () -> new DataNotFoundException(ApiErrorMessage.USER_NOT_FOUND.getMessage())
        );
        Settlement settlement = settlementRepository.findById(request.getSettlementId()).orElseThrow(
                () -> new DataNotFoundException(ApiErrorMessage.SETTLEMENT_NOT_FOUND.getMessage())
        );
        Shop shop = shopRepository.findByIdAndIsDeletedFalse(request.getShopId()).orElseThrow(
                () -> new DataNotFoundException(ApiErrorMessage.SHOP_NOT_FOUND.getMessage())
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
                throw new DataNotFoundException(ApiErrorMessage.PRODUCT_NOT_FOUND.getMessage());
            }

            if (product.getQuantityInStock() < itemRequest.getQuantity()) {
                throw new DataNotFoundException(ApiErrorMessage.PRODUCT_NOT_ENOUGH_STOCK.getMessage());
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
                () -> new DataNotFoundException(ApiErrorMessage.ORDER_NOT_FOUND.getMessage())
        );

        String currentEmail = ApiUtils.getCurrentUsername();

        if (order.getUser() == null ||
                (!order.getUser().getEmail().equalsIgnoreCase(currentEmail) && !accessValidator.isDirector(currentEmail))) {
            throw new AccessDeniedException("You cannot modify this order");
        }

        if (request.getStatus() != null) {
            order.setOrderStatus(request.getStatus());
        }

        return getOrderDTO(request, order);
    }



    @Override
    public PaginationResponse<OrderDTO> findAllByUser(String email, Pageable pageable) {

        String currentEmail = ApiUtils.getCurrentUsername();
        User currentUser = userRepository.findUserByEmailAndIsDeletedFalse(currentEmail)
                .orElseThrow(() -> new DataNotFoundException(
                        ApiErrorMessage.EMAIL_NOT_FOUND.getMessage(currentEmail)));

        Page<Order> orders;

        if (accessValidator.isDirector(currentEmail)) {
            User user = userRepository.findUserByEmailAndIsDeletedFalse(email)
                    .orElseThrow(() -> new DataNotFoundException(
                            ApiErrorMessage.EMAIL_NOT_FOUND.getMessage(email)));
            orders = orderRepository.findAllByUser(user, pageable);
        } else {
            orders = orderRepository.findAllByUser(currentUser, pageable);

            if (!currentEmail.equals(email)) {
                throw new AccessDeniedException(ApiErrorMessage.ACCESS_DENIED.getMessage());
            }
        }

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
