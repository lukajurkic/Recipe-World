package hr.dice.luka_jurkic.service.exceptions;

import hr.dice.luka_jurkic.service.exceptions.base.ServiceException;
import hr.dice.luka_jurkic.service.exceptions.errors.CommentErrorKey;
import hr.dice.luka_jurkic.service.exceptions.errors.base.ErrorKey;
import org.springframework.http.HttpStatus;

public class CommentServiceException extends ServiceException {

    protected CommentServiceException(ErrorKey errorKey, HttpStatus httpStatus) {
        super(errorKey, httpStatus);
    }

    protected CommentServiceException(ErrorKey errorKey, HttpStatus httpStatus, String message) {
        super(errorKey, httpStatus, message);
    }
    
    public static CommentServiceException notFound() {
        throw new CommentServiceException(CommentErrorKey.NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    public static CommentServiceException notFound(String message) {
        throw new CommentServiceException(CommentErrorKey.NOT_FOUND, HttpStatus.NOT_FOUND, message);
    }
}
