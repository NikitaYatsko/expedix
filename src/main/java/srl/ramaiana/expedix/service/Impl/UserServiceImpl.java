package srl.ramaiana.expedix.service.Impl;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import srl.ramaiana.expedix.exceptions.DataExistsException;
import srl.ramaiana.expedix.exceptions.DataNotFoundException;
import srl.ramaiana.expedix.model.dto.user.UserOnlyDTO;
import srl.ramaiana.expedix.model.entity.User;
import srl.ramaiana.expedix.model.dto.user.UserDTO;
import srl.ramaiana.expedix.mapper.UserMapper;
import srl.ramaiana.expedix.model.request.user.NewUserRequest;
import srl.ramaiana.expedix.model.request.user.UpdateUserRequest;
import srl.ramaiana.expedix.model.response.PaginationResponse;
import srl.ramaiana.expedix.repository.UserRepository;
import srl.ramaiana.expedix.service.UserService;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDTO findUserById(@NotNull Integer userId) {
        User user = userRepository.findByIdAndIsDeletedFalse(userId).orElseThrow(
                () -> new DataNotFoundException("User not found!")
        );
        return userMapper.toDto(user);
    }

    @Override
    public UserDTO createUser(NewUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DataExistsException("Email already exists!");
        }
        if (!Objects.equals(request.getPassword(), request.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match!");
        }
        User user = userMapper.toEntity(request);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Transactional
    @Override
    public UserDTO updateUser(@NotNull Integer userId, @NotNull UpdateUserRequest request) {
        User user = userRepository.findByIdAndIsDeletedFalse(userId).orElseThrow(
                () -> new DataNotFoundException("User not found!")
        );
        userMapper.updateUserFromRequest(request, user);
        return userMapper.toDto(user);

    }

    @Transactional
    @Override
    public void deleteUser(Integer userId) {
        User user = userRepository.findByIdAndIsDeletedFalse(userId).orElseThrow(
                () -> new DataNotFoundException("User not found!")
        );
        user.setIsDeleted(true);
    }

    @Override
    public PaginationResponse<UserDTO> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        Page<UserDTO> dto = users.map(userMapper::toDto);

        return new PaginationResponse<>(
                dto.getContent(),
                new PaginationResponse.Pagination(
                        dto.getTotalElements(),
                        pageable.getPageSize(),
                        dto.getNumber() + 1,
                        dto.getTotalPages()
                )
        );
    }

    @Override
    public PaginationResponse<UserOnlyDTO> getOnlyUsers(Pageable pageable) {
        Page<User> users = userRepository.findUsersOnly(pageable);
        Page<UserOnlyDTO> dto = users.map(userMapper::toUserOnlyDTO);

        return new PaginationResponse<>(
                dto.getContent(),
                new PaginationResponse.Pagination(
                        dto.getTotalElements(),
                        pageable.getPageSize(),
                        dto.getNumber() + 1,
                        dto.getTotalPages()
                )
        );
    }
}
