package srl.ramaiana.expedix.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ShopRequest {
    @NotBlank(message = "Shop must have a name")
    private String name;
    @NotBlank(message = "Address is necessary")
    private String address;
}
