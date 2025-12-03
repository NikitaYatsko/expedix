package srl.ramaiana.expedix.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import srl.ramaiana.expedix.model.dto.order.OrderDTO;
import srl.ramaiana.expedix.model.dto.user.UserDTO;
import srl.ramaiana.expedix.model.request.user.UpdateUserRequest;
import srl.ramaiana.expedix.model.response.PaginationResponse;
import srl.ramaiana.expedix.service.OrderService;
import srl.ramaiana.expedix.service.UserService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final OrderService orderService;


    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable Integer id) {
        log.info("Getting user by ID: {}", id);
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @GetMapping
    public ResponseEntity<PaginationResponse<UserDTO>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        log.info("Getting all users, page {}, size {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        PaginationResponse<UserDTO> response = userService.getAllUsers(pageable);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{userId}/orders")
    public ResponseEntity<PaginationResponse<OrderDTO>> getOrdersByUser(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(orderService.findAllByUser(userId, pageable));
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody UpdateUserRequest request) {
        log.info("Updating user: {}", request);
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        log.info("Deleting user: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
