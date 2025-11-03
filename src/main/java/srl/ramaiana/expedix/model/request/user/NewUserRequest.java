package srl.ramaiana.expedix.model.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NewUserRequest {
    @NotBlank(message = "Don't you have a name?:)")
    private String fullName;
    @NotBlank(message = "Email is necessary")
    private String email;
    @NotBlank(message = "You must have a password")
    private String password;
    @NotBlank(message = "Confirm Password")
    private String confirmPassword;
    @NotBlank(message = "Phone is necessary")
    private String phone;
}
