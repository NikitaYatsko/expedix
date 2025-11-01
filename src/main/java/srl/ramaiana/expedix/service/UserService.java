package srl.ramaiana.expedix.service;

import jakarta.validation.constraints.NotNull;
import srl.ramaiana.expedix.model.dto.UserDTO;
import srl.ramaiana.expedix.model.request.NewUserRequest;

public interface UserService {
    UserDTO findUserById(@NotNull Integer userId);
    UserDTO createUser(@NotNull NewUserRequest request);
}
