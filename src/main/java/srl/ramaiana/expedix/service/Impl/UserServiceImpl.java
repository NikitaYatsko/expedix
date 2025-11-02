package srl.ramaiana.expedix.service.Impl;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import srl.ramaiana.expedix.exceptions.DataExistsException;
import srl.ramaiana.expedix.exceptions.DataNotFoundException;
import srl.ramaiana.expedix.model.entity.User;
import srl.ramaiana.expedix.model.dto.UserDTO;
import srl.ramaiana.expedix.mapper.UserMapper;
import srl.ramaiana.expedix.model.request.user.NewUserRequest;
import srl.ramaiana.expedix.model.request.user.UpdateUserRequest;
import srl.ramaiana.expedix.repository.ShopRepository;
import srl.ramaiana.expedix.repository.UserRepository;
import srl.ramaiana.expedix.service.UserService;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ShopRepository shopRepository;

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
        User user = userMapper.toEntity(request);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDTO updateUser(@NotNull Integer userId, @NotNull UpdateUserRequest request) {
        User user = userRepository.findByIdAndIsDeletedFalse(userId).orElseThrow(
                () -> new DataNotFoundException("User not found!")
        );
        userMapper.updateUserFromRequest(request, user);
        userRepository.save(user);
        return userMapper.toDto(user);

    }

    @Override
    public void deleteUser(Integer userId) {
        User user = userRepository.findByIdAndIsDeletedFalse(userId).orElseThrow(
                () -> new DataNotFoundException("User not found!")
        );
        user.setIsDeleted(true);
        userRepository.save(user);
    }

    @Override
    public Page<UserDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toDto);
    }


}
