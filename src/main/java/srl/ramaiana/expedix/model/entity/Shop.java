package srl.ramaiana.expedix.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "shops", schema = "expedix")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    @Column(name = "name")
    private String name;
    @NotBlank
    @Column(name = "address")
    private String address;

}
