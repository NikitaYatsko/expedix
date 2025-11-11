package srl.ramaiana.expedix.model.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum RolesEnum {
    OPERATOR("OPERATOR"),
    DIRECTOR("DIRECTOR"),
    AGENT("AGENT"),
    FORWARDER("FORWARDER"),
    USER("USER"),;

    private final String role;

    public static RolesEnum fromRole(String role) {
        return Arrays.stream(RolesEnum.values())
                .filter(r -> r.getRole().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid Role: " + role));
    }

}
