package srl.ramaiana.expedix.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import srl.ramaiana.expedix.model.entity.User;
import srl.ramaiana.expedix.model.dto.UserDTO;
import srl.ramaiana.expedix.model.request.NewUserRequest;

import java.util.List;

@RequiredArgsConstructor
@Component
public class UserMapper {

    private final SettlementMapper settlementMapper;

    public UserDTO toDto(User user) {

        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setFullName(user.getFullName());
        userDTO.setPersonalCode(user.getPersonalCode());
        userDTO.setPhoneNumber(user.getPhoneNumber());

        userDTO.setSettlementList(user.getSettlementList()
                .stream()
                .map(settlementMapper::toDto).toList());
        return userDTO;
    }

    public User toEntity(NewUserRequest request) {
        if (request == null) {
            return null;
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setPassword(request.getPassword());
        user.setPhoneNumber(request.getPhone());
        user.setPersonalCode(null);
        user.setSettlementList(List.of());
        return user;
    }
}
