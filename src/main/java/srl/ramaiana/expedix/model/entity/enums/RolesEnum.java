package srl.ramaiana.expedix.model.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum RolesEnum {
    DIRECTOR("Директор"),
    OPERATOR("Оператор"),
    AGENT("Агент"),
    FORWARDER("Экспедитор"),
    USER("Пользователь");


    private final String role;

    public static RolesEnum fromRole(String role) {
        return Arrays.stream(RolesEnum.values())
                .filter(r -> r.getRole().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid Role: " + role));
    }

}
