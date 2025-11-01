package srl.ramaiana.expedix.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import srl.ramaiana.expedix.exceptions.DataExistsException;
import srl.ramaiana.expedix.exceptions.DataNotFoundException;

import java.time.LocalDateTime;

@ControllerAdvice
public class CommonControllerAdvice {
    record ErrorResponse(LocalDateTime timestamp, String message, int status) {

    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(DataNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataExistsException.class)
    public ResponseEntity<ErrorResponse> handleDataExistsException(DataExistsException ex) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
