package srl.ramaiana.expedix.service.Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import srl.ramaiana.expedix.constants.ApiErrorMessage;
import srl.ramaiana.expedix.exceptions.DataNotFoundException;
import srl.ramaiana.expedix.mapper.UserMapper;
import srl.ramaiana.expedix.model.dto.user.UserDTO;
import srl.ramaiana.expedix.model.entity.User;
import srl.ramaiana.expedix.repository.UserRepository;
import srl.ramaiana.expedix.service.ProfileService;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ProfileServiceImpl implements ProfileService {
    private final Cloudinary cloudinary;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public UserDTO uploadPhoto(MultipartFile file) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            User user = userRepository.findUserByEmailAndIsDeletedFalse(email).orElseThrow(
                    () -> new DataNotFoundException(ApiErrorMessage.USER_NOT_FOUND.getMessage())
            );
            Map<String, Object> uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap("folder", "user_photos")
            );
            String photoUrl = (String) uploadResult.get("secure_url");
            user.setImageUrl(photoUrl);
            userRepository.save(user);
            return userMapper.toDto(user);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to upload photo", e);
        }
    }
}
