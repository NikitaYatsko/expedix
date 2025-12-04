package srl.ramaiana.expedix.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import srl.ramaiana.expedix.model.dto.order.OrderDTO;
import srl.ramaiana.expedix.model.request.order.NewOrderRequest;
import srl.ramaiana.expedix.model.request.order.UpdateOrderDTO;
import srl.ramaiana.expedix.model.response.PaginationResponse;
import srl.ramaiana.expedix.service.OrderService;
import srl.ramaiana.expedix.utils.ApiUtils;

import java.security.Principal;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
@Tag(name = "Orders", description = "Управление заказами")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/me")
    public ResponseEntity<PaginationResponse<OrderDTO>> getMyOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        String currentEmail = ApiUtils.getCurrentUsername();
        return ResponseEntity.ok(orderService.findAllByUser(currentEmail, pageable));
    }

    @GetMapping("/me/{id}")
    public ResponseEntity<OrderDTO> getMyOrder(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(orderService.getMyOrderById(id));
    }

    @PutMapping("/me/{id}")
    public ResponseEntity<OrderDTO> updateMyOrder(@PathVariable Long id, @Valid @RequestBody UpdateOrderDTO request) {
        return ResponseEntity.ok(orderService.updateOrder(id,request));

    }

    @GetMapping("/user")
    public ResponseEntity<PaginationResponse<OrderDTO>> getOrdersByUser(
            @RequestParam String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(orderService.findAllByUser(email, pageable));
    }


    @Operation(
            summary = "Получить заказ по ID",
            description = "Возвращает подробную информацию о заказе по указанному идентификатору.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Заказ найден"),
                    @ApiResponse(responseCode = "404", description = "Заказ не найден")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(
            @Parameter(description = "ID заказа") @PathVariable Long id) {

        log.info("Getting order by id: {}", id);
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @Operation(
            summary = "Получить список всех заказов",
            description = "Возвращает список заказов с пагинацией.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список заказов получен успешно")
            }
    )
    @GetMapping("/all")
    public ResponseEntity<PaginationResponse<OrderDTO>> getAllOrders(
            @Parameter(description = "Номер страницы (по умолчанию 0)")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Размер страницы (по умолчанию 10)")
            @RequestParam(defaultValue = "10") int size) {

        log.info("Getting all orders");
        Pageable pageable = PageRequest.of(page, size);
        PaginationResponse<OrderDTO> response = orderService.getOrders(pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Создать новый заказ",
            description = "Создаёт новый заказ от имени авторизованного пользователя.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Заказ успешно создан"),
                    @ApiResponse(responseCode = "400", description = "Некорректные данные")
            }
    )
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(
            @Valid @RequestBody NewOrderRequest request,
            @Parameter(hidden = true) Principal principal) {

        log.info("Creating order: {}", request);
        return ResponseEntity.ok(orderService.createOrder(principal.getName(), request));
    }

    @Operation(
            summary = "Обновить данные заказа",
            description = "Обновляет информацию о существующем заказе.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Заказ обновлён успешно"),
                    @ApiResponse(responseCode = "404", description = "Заказ не найден")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(
            @Parameter(description = "ID заказа") @PathVariable Long id,
            @RequestBody UpdateOrderDTO request) {

        log.info("Updating order: {}", id);
        return ResponseEntity.ok(orderService.updateOrder(id, request));
    }
}
