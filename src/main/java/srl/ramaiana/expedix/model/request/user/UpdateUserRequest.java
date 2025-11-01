package srl.ramaiana.expedix.model.request.user;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String password;
    private String phone;
}
