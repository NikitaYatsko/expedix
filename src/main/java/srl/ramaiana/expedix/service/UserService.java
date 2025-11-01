package srl.ramaiana.expedix.service;

import jakarta.validation.constraints.NotNull;
import srl.ramaiana.expedix.model.dto.UserDTO;
import srl.ramaiana.expedix.model.request.user.NewUserRequest;
import srl.ramaiana.expedix.model.request.user.UpdateUserRequest;

public interface UserService {
    UserDTO findUserById(@NotNull Integer userId);
    UserDTO createUser(@NotNull NewUserRequest request);
    UserDTO updateUser(@NotNull Integer userId, @NotNull UpdateUserRequest request);
}
