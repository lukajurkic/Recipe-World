package hr.dice.luka_jurkic.factory;

import hr.dice.luka_jurkic.rest.request.RecipeIngredientRequest;

import java.math.BigDecimal;

import static hr.dice.luka_jurkic.constants.RecipeIngredientTestConstants.NON_EXISTING_RECIPE_INGREDIENT_AMOUNT;
import static hr.dice.luka_jurkic.constants.RecipeIngredientTestConstants.NON_EXISTING_RECIPE_INGREDIENT_INGREDIENT_NAME;
import static hr.dice.luka_jurkic.constants.RecipeIngredientTestConstants.NON_EXISTING_RECIPE_INGREDIENT_UNIT;

public class RecipeIngredientFactory {

    public static RecipeIngredientRequest validRequest() {
        RecipeIngredientRequest request = new RecipeIngredientRequest();
        request.setIngredientName(NON_EXISTING_RECIPE_INGREDIENT_INGREDIENT_NAME);
        request.setAmount(NON_EXISTING_RECIPE_INGREDIENT_AMOUNT);
        request.setUnit(NON_EXISTING_RECIPE_INGREDIENT_UNIT);
        return request;
    }

    public static RecipeIngredientRequest paramRequest(String ingredientName, BigDecimal Amount, String ingredientUnit) {
        RecipeIngredientRequest request = new RecipeIngredientRequest();
        request.setIngredientName(ingredientName);
        request.setAmount(Amount);
        request.setUnit(ingredientUnit);
        return request;
    }
}
