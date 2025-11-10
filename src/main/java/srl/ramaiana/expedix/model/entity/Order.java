package srl.ramaiana.expedix.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import srl.ramaiana.expedix.model.entity.enums.OrderStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "orders", schema = "expedix")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "settlement_id")
    private Settlement settlement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @CreationTimestamp
    @Column(name = "created")
    private LocalDateTime created;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatusEnum orderStatus = OrderStatusEnum.NEW;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    private String comment;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();


    public void addOrderItem(Product product, Integer quantity) {
        OrderItem item = new OrderItem();
        item.setOrder(this);
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setUnitPrice(product.getUnitPrice());
        this.orderItems.add(item);
    }

    public void calculateTotalPrice() {
        if (this.orderItems == null || this.orderItems.isEmpty()) {
            this.totalPrice = BigDecimal.ZERO;
            return;
        }

        this.totalPrice = orderItems.stream()
                .map(item -> {

                    if (item.getTotalPrice() == null) {
                        item.calculateTotalPrice();
                    }
                    return item.getTotalPrice() != null ? item.getTotalPrice() : BigDecimal.ZERO;
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}