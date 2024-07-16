package hr.dice.luka_jurkic.service.exceptions;

import hr.dice.luka_jurkic.service.exceptions.base.ServiceException;
import hr.dice.luka_jurkic.service.exceptions.errors.UserErrorKey;
import hr.dice.luka_jurkic.service.exceptions.errors.base.ErrorKey;
import org.springframework.http.HttpStatus;

public class UserServiceException extends ServiceException {

    protected UserServiceException(ErrorKey errorKey, HttpStatus httpStatus) {
        super(errorKey, httpStatus);
    }

    protected UserServiceException(ErrorKey errorKey, HttpStatus httpStatus, String message) {
        super(errorKey, httpStatus, message);
    }

    public static UserServiceException notFound() {
        throw new UserServiceException(UserErrorKey.NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    public static UserServiceException notFound(String message) {
        throw new UserServiceException(UserErrorKey.NOT_FOUND, HttpStatus.NOT_FOUND, message);
    }

    public static UserServiceException alreadyExists() {
        throw new UserServiceException(UserErrorKey.ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
    }

    public static UserServiceException alreadyExists(String message) {
        throw new UserServiceException(UserErrorKey.ALREADY_EXISTS, HttpStatus.BAD_REQUEST, message);
    }

    public static UserServiceException isLastAdmin() {
        throw new UserServiceException(UserErrorKey.IS_LAST_ADMIN, HttpStatus.BAD_REQUEST);
    }

    public static UserServiceException isLastAdmin(String message) {
        throw new UserServiceException(UserErrorKey.IS_LAST_ADMIN, HttpStatus.BAD_REQUEST, message);
    }
}
