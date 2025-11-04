package srl.ramaiana.expedix.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;


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
    @NotBlank
    private String fullName;
    @NotBlank
    @Size(min = 6, max = 80)
    private String password;
    @Column(name = "phone")
    @NotBlank
    private String phoneNumber;
    @Column(name = "email")
    @NotBlank
    private String email;
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Settlement> settlementList;


}
