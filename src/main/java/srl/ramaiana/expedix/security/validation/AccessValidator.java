package srl.ramaiana.expedix.security.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import srl.ramaiana.expedix.constants.ApiErrorMessage;
import srl.ramaiana.expedix.exceptions.DataExistsException;
import srl.ramaiana.expedix.exceptions.InvalidPasswordException;
import srl.ramaiana.expedix.repository.UserRepository;
import srl.ramaiana.expedix.utils.PasswordUtils;

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
}
