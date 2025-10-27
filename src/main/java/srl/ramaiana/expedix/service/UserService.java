package srl.ramaiana.expedix.service;

import jakarta.validation.constraints.NotNull;
import srl.ramaiana.expedix.model.dto.UserDTO;

public interface UserService {
    UserDTO findUserById(@NotNull Integer userId);
}
