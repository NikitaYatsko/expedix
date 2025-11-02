package srl.ramaiana.expedix.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import srl.ramaiana.expedix.exceptions.DataExistsException;
import srl.ramaiana.expedix.exceptions.DataNotFoundException;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class CommonControllerAdvice {

    private record ErrorResponse(LocalDateTime timestamp, String message, int status) {
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(DataNotFoundException ex) {
        log.warn("Data not found: {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataExistsException.class)
    public ResponseEntity<ErrorResponse> handleDataExistsException(DataExistsException ex) {
        log.warn("Data already exists: {}", ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        log.error("Unexpected error: ", ex);

        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                "Internal server error",
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
