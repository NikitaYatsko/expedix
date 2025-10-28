package srl.ramaiana.expedix.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import srl.ramaiana.expedix.model.entity.User;
import srl.ramaiana.expedix.model.dto.UserDTO;

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
}
