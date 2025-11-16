package srl.ramaiana.expedix.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import srl.ramaiana.expedix.model.dto.user.UserDTO;
import srl.ramaiana.expedix.model.dto.user.UserOnlyDTO;
import srl.ramaiana.expedix.model.dto.user.UserProfileDTO;
import srl.ramaiana.expedix.model.entity.User;

import srl.ramaiana.expedix.model.request.user.RegistrationUserRequest;
import srl.ramaiana.expedix.model.request.user.UpdateUserRequest;


import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class UserMapper {

    private final SettlementMapper settlementMapper;
    private final RoleMapper roleMapper;

    public UserOnlyDTO toUserOnlyDTO(User user) {
        if (user == null) {
            return null;
        }
        UserOnlyDTO dto = new UserOnlyDTO();

        dto.setUserId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setPersonalCode(user.getPersonalCode());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setIsDeleted(user.getIsDeleted());
        dto.setRoleList(user.getRoles()
                .stream()
                .map(roleMapper::toDto)
                .toList());
        return dto;

    }

    public UserDTO toDto(User user) {

        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setFullName(user.getFullName());
        userDTO.setPersonalCode(user.getPersonalCode());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setIsDeleted(user.getIsDeleted());

        userDTO.setSettlementList(user.getSettlementList()
                .stream()
                .map(settlementMapper::toSettlementMappedByUser)
                .toList());

        userDTO.setRoleList(user.getRoles()
                .stream()
                .map(roleMapper::toDto)
                .toList());
        return userDTO;
    }

    public User toEntity(RegistrationUserRequest request) {
        if (request == null) {
            return null;
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setPassword(request.getPassword());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPersonalCode(null);
        user.setSettlementList(List.of());
        return user;
    }

    public void updateUserFromRequest(UpdateUserRequest request, User user) {
        if (request == null || user == null) {
            return;
        }

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(request.getPassword());
        }

        if (request.getPhone() != null && !request.getPhone().isBlank()) {
            user.setPhoneNumber(request.getPhone());
        }
    }






    public UserProfileDTO toUserProfileDTO(User user, String accessToken, String refreshToken) {
        if (user == null) {
            return null;
        }

        UserProfileDTO dto = new UserProfileDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setPersonalCode(user.getPersonalCode());
        dto.setRoles(user.getRoles()
                .stream()
                .map(roleMapper::toDto)
                .collect(Collectors.toList()));
        dto.setPhone(user.getPhoneNumber());
        dto.setToken(accessToken);
        dto.setRefreshToken(refreshToken);

        return dto;
    }


}
