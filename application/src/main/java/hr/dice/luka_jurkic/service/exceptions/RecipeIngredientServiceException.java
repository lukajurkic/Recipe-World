package hr.dice.luka_jurkic.service.exceptions;

import hr.dice.luka_jurkic.service.exceptions.base.ServiceException;
import hr.dice.luka_jurkic.service.exceptions.errors.RecipeIngredientErrorKey;
import hr.dice.luka_jurkic.service.exceptions.errors.base.ErrorKey;
import org.springframework.http.HttpStatus;

public class RecipeIngredientServiceException extends ServiceException {

    protected RecipeIngredientServiceException(ErrorKey errorKey, HttpStatus httpStatus) {
        super(errorKey, httpStatus);
    }

    protected RecipeIngredientServiceException(ErrorKey errorKey, HttpStatus httpStatus, String message) {
        super(errorKey, httpStatus, message);
    }

    public static RecipeIngredientServiceException notFound() {
        throw new RecipeIngredientServiceException(RecipeIngredientErrorKey.NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    public static RecipeIngredientServiceException notFound(String message) {
        throw new RecipeIngredientServiceException(RecipeIngredientErrorKey.NOT_FOUND, HttpStatus.NOT_FOUND, message);
    }
}
