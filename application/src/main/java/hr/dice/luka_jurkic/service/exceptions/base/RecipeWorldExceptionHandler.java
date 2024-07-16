package hr.dice.luka_jurkic.service.exceptions.base;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RecipeWorldExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> handle(ServiceException exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getErrorKey().getKey(), exception.getMessage());
        return ResponseEntity.status(exception.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handle(Exception exception) {

        if (exception instanceof MethodArgumentNotValidException || exception instanceof IllegalArgumentException) {
            ErrorResponse errorResponse = new ErrorResponse("Bad Request", exception.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } if (exception instanceof AccessDeniedException) {
            ErrorResponse errorResponse = new ErrorResponse("Access Denied", exception.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        } else {
            ErrorResponse errorResponse = new ErrorResponse("Server Error", exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
