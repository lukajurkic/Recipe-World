package hr.dice.luka_jurkic.service.exceptions;

import hr.dice.luka_jurkic.service.exceptions.base.ServiceException;
import hr.dice.luka_jurkic.service.exceptions.errors.IngredientErrorKey;
import hr.dice.luka_jurkic.service.exceptions.errors.base.ErrorKey;
import org.springframework.http.HttpStatus;

public class IngredientServiceException extends ServiceException {
    protected IngredientServiceException(ErrorKey errorKey, HttpStatus httpStatus) {
        super(errorKey, httpStatus);
    }

    protected IngredientServiceException(ErrorKey errorKey, HttpStatus httpStatus, String message) {
        super(errorKey, httpStatus, message);
    }

    public static IngredientServiceException notFound() {
        throw new IngredientServiceException(IngredientErrorKey.NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    public static IngredientServiceException notFound(String message) {
        throw new IngredientServiceException(IngredientErrorKey.NOT_FOUND, HttpStatus.NOT_FOUND, message);
    }

    public static IngredientServiceException alreadyExists() {
        throw new IngredientServiceException(IngredientErrorKey.ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
    }

    public static IngredientServiceException alreadyExists(String message) {
        throw new IngredientServiceException(IngredientErrorKey.ALREADY_EXISTS, HttpStatus.BAD_REQUEST, message);
    }

    public static IngredientServiceException imageLimitReached() {
        throw new IngredientServiceException(IngredientErrorKey.IMAGE_LIMIT_REACHED, HttpStatus.BAD_REQUEST);
    }

    public  static IngredientServiceException imageLimitReached(String message) {
        throw new IngredientServiceException(IngredientErrorKey.IMAGE_LIMIT_REACHED, HttpStatus.BAD_REQUEST, message);
    }

    public static IngredientServiceException inUse() {
        throw new IngredientServiceException(IngredientErrorKey.IN_USE, HttpStatus.BAD_REQUEST);
    }

    public static IngredientServiceException inUse(String message) {
        throw new IngredientServiceException(IngredientErrorKey.IN_USE, HttpStatus.BAD_REQUEST, message);
    }
}
