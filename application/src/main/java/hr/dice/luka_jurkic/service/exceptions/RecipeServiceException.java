
package hr.dice.luka_jurkic.service.exceptions;

import hr.dice.luka_jurkic.service.exceptions.base.ServiceException;
import hr.dice.luka_jurkic.service.exceptions.errors.RecipeErrorKey;
import hr.dice.luka_jurkic.service.exceptions.errors.base.ErrorKey;
import org.springframework.http.HttpStatus;

public class RecipeServiceException extends ServiceException {

    protected RecipeServiceException(ErrorKey errorKey, HttpStatus httpStatus) {
        super(errorKey, httpStatus);
    }

    protected RecipeServiceException(ErrorKey errorKey, HttpStatus httpStatus, String message) {
        super(errorKey, httpStatus, message);
    }
    
    public static RecipeServiceException notFound() {
        throw new RecipeServiceException(RecipeErrorKey.NOT_FOUND, HttpStatus.NOT_FOUND);
    }
    
    public static RecipeServiceException notFound(String message) {
        throw new RecipeServiceException(RecipeErrorKey.NOT_FOUND, HttpStatus.NOT_FOUND, message);
    }

    public static RecipeServiceException alreadyExists() {
        throw new RecipeServiceException(RecipeErrorKey.ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
    }

    public static RecipeServiceException alreadyExists(String message) {
        throw new RecipeServiceException(RecipeErrorKey.ALREADY_EXISTS, HttpStatus.BAD_REQUEST, message);
    }

    public static RecipeServiceException accessDenied() {
        throw new RecipeServiceException(RecipeErrorKey.ACCESS_DENIED, HttpStatus.FORBIDDEN);
    }

    public static RecipeServiceException accessDenied(String message) {
        throw new RecipeServiceException(RecipeErrorKey.ACCESS_DENIED, HttpStatus.FORBIDDEN, message);
    }

    public static RecipeServiceException imageLimitReached() {
        throw new RecipeServiceException(RecipeErrorKey.IMAGE_LIMIT_REACHED, HttpStatus.BAD_REQUEST);
    }

    public static RecipeServiceException imageLimitReached(String message) {
        throw new RecipeServiceException(RecipeErrorKey.IMAGE_LIMIT_REACHED, HttpStatus.BAD_REQUEST, message);
    }
}
