package srl.ramaiana.expedix.security.validation;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import srl.ramaiana.expedix.constants.ApiErrorMessage;
import srl.ramaiana.expedix.exceptions.DataExistsException;
import srl.ramaiana.expedix.exceptions.DataNotFoundException;
import srl.ramaiana.expedix.exceptions.InvalidPasswordException;
import srl.ramaiana.expedix.model.entity.User;
import srl.ramaiana.expedix.repository.UserRepository;
import srl.ramaiana.expedix.utils.ApiUtils;
import srl.ramaiana.expedix.utils.PasswordUtils;

import java.nio.file.AccessDeniedException;

@Component
@RequiredArgsConstructor
public class AccessValidator {
    private final UserRepository userRepository;

    public void validateNewUser(String email,
                                String password, String confirmPassword) {

        if (userRepository.existsByEmail(email)) {
            throw new DataExistsException(ApiErrorMessage.EMAIL_ALREADY_EXISTS
                    .getMessage(email));
        }

        if (!password.equals(confirmPassword)) {
            throw new InvalidPasswordException(ApiErrorMessage.INVALID_PASSWORD.getMessage());
        }

        if (PasswordUtils.isNotValidPassword(password)) {
            throw new InvalidPasswordException(ApiErrorMessage.INVALID_PASSWORD.getMessage());
        }
    }

    public boolean isDirector(String email) {
        User user = userRepository.findUserByEmailAndIsDeletedFalse(email).orElseThrow(
                () -> new DataNotFoundException(ApiErrorMessage.EMAIL_NOT_FOUND.getMessage(email))
        );
        return user.getRoles().stream().anyMatch(role -> role.getName().equals("Директор"));
    }

    @SneakyThrows
    public void validateDirectorOrOwnerAccess(String email) {
        String currentEmail = ApiUtils.getCurrentUsername();

        if (!currentEmail.equals(email) &&
                !isDirector(currentEmail)) {
            throw new AccessDeniedException(ApiErrorMessage.ACCESS_DENIED.getMessage());
        }
    }

}
