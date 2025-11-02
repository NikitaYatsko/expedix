package srl.ramaiana.expedix.service;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import srl.ramaiana.expedix.model.dto.UserDTO;
import srl.ramaiana.expedix.model.request.user.NewUserRequest;
import srl.ramaiana.expedix.model.request.user.UpdateUserRequest;

public interface UserService {
    UserDTO findUserById(@NotNull Integer userId);
    UserDTO createUser(@NotNull NewUserRequest request);
    UserDTO updateUser(@NotNull Integer userId, @NotNull UpdateUserRequest request);
    void deleteUser(@NotNull Integer userId);
    Page<UserDTO> getAllUsers(Pageable pageable);
}
