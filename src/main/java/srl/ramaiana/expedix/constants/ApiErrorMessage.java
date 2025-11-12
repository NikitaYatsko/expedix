package srl.ramaiana.expedix.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiErrorMessage {

    ERROR_DURING_JWT_PROCESSING("An unexpected error occurred during JWT processing"),
    TOKEN_EXPIRED("Token expired."),
    UNEXPECTED_ERROR_OCCURRED("An unexpected error occurred. Please try again later."),
    INVALID_TOKEN_SIGNATURE("Invalid token signature"),
    EMAIL_NOT_FOUND("Email: %s not found"),

    ;

    private final String message;

    public String getMessage(Object... args) {
        return String.format(message, args);
    }
}
