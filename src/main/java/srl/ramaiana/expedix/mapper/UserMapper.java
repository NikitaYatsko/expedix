package srl.ramaiana.expedix.mapper;

import org.springframework.stereotype.Component;
import srl.ramaiana.expedix.model.entity.User;
import srl.ramaiana.expedix.model.dto.UserDTO;

@Component
public class UserMapper {
    public UserDTO toDto(User user) {

        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setFullName(user.getFullName());
        userDTO.setPersonalCode(user.getPersonalCode());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        return userDTO;
    }
}
