package srl.ramaiana.expedix.service;

import srl.ramaiana.expedix.entity.dto.UserDTO;

public interface UserService {
    UserDTO findUserById(Integer userId);
}
