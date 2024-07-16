package hr.dice.luka_jurkic.service.exceptions;

import hr.dice.luka_jurkic.service.exceptions.base.ServiceException;
import hr.dice.luka_jurkic.service.exceptions.errors.StepErrorKey;
import hr.dice.luka_jurkic.service.exceptions.errors.base.ErrorKey;
import org.springframework.http.HttpStatus;

public class StepServiceException extends ServiceException {

    protected StepServiceException(ErrorKey errorKey, HttpStatus httpStatus) {
        super(errorKey, httpStatus);
    }

    protected StepServiceException(ErrorKey errorKey, HttpStatus httpStatus, String message) {
        super(errorKey, httpStatus, message);
    }

    public static StepServiceException notFound() {
        throw new StepServiceException(StepErrorKey.NOt_FOUND, HttpStatus.NOT_FOUND);
    }

    public static StepServiceException notFound(String message) {
        throw new StepServiceException(StepErrorKey.NOt_FOUND, HttpStatus.NOT_FOUND, message);
    }
}
