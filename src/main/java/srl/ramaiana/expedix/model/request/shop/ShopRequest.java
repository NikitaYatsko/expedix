package srl.ramaiana.expedix.model.request.shop;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ShopRequest {
    @NotBlank(message = "Shop must have a name")
    private String name;
    @NotBlank(message = "Address is necessary")
    private String address;
    @NotNull(message = "Shop must be located somewhere!")
    private Integer settlementId;
}
