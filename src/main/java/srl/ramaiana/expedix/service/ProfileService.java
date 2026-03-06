package srl.ramaiana.expedix.service;

import org.springframework.web.multipart.MultipartFile;
import srl.ramaiana.expedix.model.dto.user.UserDTO;

import java.security.Principal;


public interface ProfileService {
    UserDTO uploadPhoto(MultipartFile file);
}
