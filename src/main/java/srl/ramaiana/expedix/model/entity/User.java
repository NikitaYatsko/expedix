package srl.ramaiana.expedix.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
@Entity
@Table(name = "users", schema = "expedix")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "personal_code")
    @NotBlank
    private String personalCode;
    @Column(name = "full_name")
    @NotBlank
    private String fullName;
    @Column(name = "phone")
    @NotBlank
    private String phoneNumber;
    @Column(name = "email")
    @NotBlank
    private String email;
}
