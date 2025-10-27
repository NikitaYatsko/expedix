package srl.ramaiana.expedix.entity;

import jakarta.persistence.*;
import lombok.Builder;
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
    private String personalCode;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "phone")
    private String phoneNumber;
    @Column(name = "email")
    private String email;
}
