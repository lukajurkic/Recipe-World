package hr.dice.luka_jurkic.mvc.controller.exception;

import hr.dice.luka_jurkic.service.exceptions.CommentServiceException;
import hr.dice.luka_jurkic.service.exceptions.ImageServiceException;
import hr.dice.luka_jurkic.service.exceptions.IngredientServiceException;
import hr.dice.luka_jurkic.service.exceptions.RecipeIngredientServiceException;
import hr.dice.luka_jurkic.service.exceptions.RecipeServiceException;
import hr.dice.luka_jurkic.service.exceptions.UserServiceException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UserServiceException.class)
    public String userException(UserServiceException exception, Model model) {
        String errorKey = "user " + exception.getErrorKey().toString().toLowerCase().replace('_', ' ');
        model.addAttribute("errorMessage", errorKey);
        model.addAttribute("httpStatus", exception.getHttpStatus().value());
        return "exceptions/error";
    }

    @ExceptionHandler(RecipeServiceException.class)
    public String recipeException(RecipeServiceException exception, Model model) {
        String errorKey = "recipe " + exception.getErrorKey().toString().toLowerCase().replace('_', ' ');
        model.addAttribute("errorMessage", errorKey);
        model.addAttribute("httpStatus", exception.getHttpStatus().value());
        return "exceptions/error";
    }

    @ExceptionHandler(IngredientServiceException.class)
    public String ingredientException(IngredientServiceException exception, Model model) {
        String errorKey = "ingredient " + exception.getErrorKey().toString().toLowerCase().replace('_', ' ');
        model.addAttribute("errorMessage", errorKey);
        model.addAttribute("httpStatus", exception.getHttpStatus().value());
        return "exceptions/error";
    }

    @ExceptionHandler(ImageServiceException.class)
    public String imageException(ImageServiceException exception, Model model) {
        String errorKey = "image " + exception.getErrorKey().toString().toLowerCase().replace('_', ' ');
        model.addAttribute("errorMessage", errorKey);
        model.addAttribute("httpStatus", exception.getHttpStatus().value());
        return "exceptions/error";
    }

    @ExceptionHandler(CommentServiceException.class)
    public String commentException(CommentServiceException exception, Model model) {
        String errorKey = "comment " + exception.getErrorKey().toString().toLowerCase().replace('_', ' ');
        model.addAttribute("errorMessage", errorKey);
        model.addAttribute("httpStatus", exception.getHttpStatus().value());
        return "exceptions/error";
    }

    @ExceptionHandler(RecipeIngredientServiceException.class)
    public String recipeIngredientException(RecipeIngredientServiceException exception, Model model) {
        String errorKey = "recipe ingredient " + exception.getErrorKey().toString().toLowerCase().replace('_', ' ');
        model.addAttribute("errorMessage", errorKey);
        model.addAttribute("httpStatus", exception.getHttpStatus().value());
        return "exceptions/error";
    }

}
