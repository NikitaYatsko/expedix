package srl.ramaiana.expedix.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import srl.ramaiana.expedix.model.entity.enums.RolesEnum;
import srl.ramaiana.expedix.utils.enums.UserRoleTypeConverter;

import java.util.Set;


@Entity
@Data
@Table(name = "roles", schema = "expedix")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Column(name = "user_system_role", nullable = false, length = 64)
    @Convert(converter = UserRoleTypeConverter.class)
    private RolesEnum userSystemRole;
    @Column(name = "active")
    private Boolean active = true;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Set<User> users;
}
