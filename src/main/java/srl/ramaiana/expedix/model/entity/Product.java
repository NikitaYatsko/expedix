package srl.ramaiana.expedix.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import srl.ramaiana.expedix.model.entity.enums.UnitTypeEnum;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "products", schema = "expedix")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    @NotBlank
    private String name;
    @Column(name = "brand")
    @NotBlank
    private String brand;
    @Column(name = "quantity_in_stock")
    private Integer quantityInStock;
    @Column(name = "unit_price")
    private BigDecimal unitPrice;
    @Enumerated(EnumType.STRING)
    private UnitTypeEnum typeOfUnit;

}
