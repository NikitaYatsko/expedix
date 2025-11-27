package srl.ramaiana.expedix.service;

import jakarta.validation.constraints.NotNull;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import srl.ramaiana.expedix.model.dto.user.UserDTO;
import srl.ramaiana.expedix.model.request.user.UpdateUserRequest;
import srl.ramaiana.expedix.model.response.PaginationResponse;

public interface UserService extends UserDetailsService {
    UserDTO findUserById(@NotNull Integer userId);

    UserDTO updateUser(@NotNull Integer userId, @NotNull UpdateUserRequest request);

    void deleteUser(@NotNull Integer userId);

    PaginationResponse<UserDTO> getAllUsers(Pageable pageable);

    UserDTO getCurrentUser(String email);

}
