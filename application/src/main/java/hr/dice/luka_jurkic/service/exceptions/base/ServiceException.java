package hr.dice.luka_jurkic.service.exceptions.base;

import hr.dice.luka_jurkic.service.exceptions.errors.base.ErrorKey;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public abstract class ServiceException extends RuntimeException {

    protected final ErrorKey errorKey;
    protected  final HttpStatus httpStatus;

    protected ServiceException(ErrorKey errorKey, HttpStatus httpStatus) {
        this.errorKey = errorKey;
        this.httpStatus = httpStatus;
    }

    protected ServiceException(ErrorKey errorKey, HttpStatus httpStatus, String message) {
        super(message);
        this.errorKey = errorKey;
        this.httpStatus = httpStatus;
    }
}

