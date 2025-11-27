package srl.ramaiana.expedix.service.Impl;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import srl.ramaiana.expedix.constants.ApiErrorMessage;
import srl.ramaiana.expedix.exceptions.DataNotFoundException;
import srl.ramaiana.expedix.model.entity.User;
import srl.ramaiana.expedix.model.dto.user.UserDTO;
import srl.ramaiana.expedix.mapper.UserMapper;
import srl.ramaiana.expedix.model.entity.enums.RolesEnum;
import srl.ramaiana.expedix.model.request.user.UpdateUserRequest;
import srl.ramaiana.expedix.model.response.PaginationResponse;
import srl.ramaiana.expedix.repository.UserRepository;
import srl.ramaiana.expedix.service.UserService;

import java.util.stream.Collectors;

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
    public UserDTO getCurrentUser(String email) {
        User user = userRepository.findUserByEmailAndIsDeletedFalse(email).orElseThrow(
                () -> new DataNotFoundException("User not found!")
        );
        return userMapper.toDto(user);

    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return getUserDetails(email, userRepository);
    }

    static UserDetails getUserDetails(String email, UserRepository userRepository) {
        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new DataNotFoundException(ApiErrorMessage.EMAIL_NOT_FOUND.getMessage())
        );
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(
                                RolesEnum.fromRole(role.getName()).getAuthority()
                        ))
                        .collect(Collectors.toList())
        );
    }
}
