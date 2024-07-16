package hr.dice.luka_jurkic.parameters;

import hr.dice.luka_jurkic.factory.RecipeIngredientFactory;
import org.junit.jupiter.params.provider.Arguments;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static hr.dice.luka_jurkic.constants.RecipeIngredientTestConstants.NON_EXISTING_RECIPE_INGREDIENT_AMOUNT;
import static hr.dice.luka_jurkic.constants.RecipeIngredientTestConstants.NON_EXISTING_RECIPE_INGREDIENT_INGREDIENT_NAME;
import static hr.dice.luka_jurkic.constants.RecipeIngredientTestConstants.NON_EXISTING_RECIPE_INGREDIENT_RECIPE_NAME;
import static hr.dice.luka_jurkic.constants.RecipeIngredientTestConstants.NON_EXISTING_RECIPE_INGREDIENT_UNIT;
import static hr.dice.luka_jurkic.constants.RecipeIngredientTestConstants.RECIPE_INGREDIENT_BAD_REQUEST;
import static hr.dice.luka_jurkic.constants.RecipeIngredientTestConstants.UPDATED_RECIPE_INGREDIENT_AMOUNT;
import static hr.dice.luka_jurkic.constants.RecipeIngredientTestConstants.UPDATED_RECIPE_INGREDIENT_INGREDIENT_NAME;
import static hr.dice.luka_jurkic.constants.RecipeIngredientTestConstants.UPDATE_RECIPE_INGREDIENT_UNIT;

public class RecipeIngredientParameters {

    public static Stream<Arguments> badRequestForCreateRecipeIngredient() {
        return Stream.of(
                Arguments.of(NON_EXISTING_RECIPE_INGREDIENT_RECIPE_NAME, RecipeIngredientFactory.paramRequest(NON_EXISTING_RECIPE_INGREDIENT_INGREDIENT_NAME, null, NON_EXISTING_RECIPE_INGREDIENT_UNIT), RECIPE_INGREDIENT_BAD_REQUEST),
                Arguments.of(NON_EXISTING_RECIPE_INGREDIENT_RECIPE_NAME, RecipeIngredientFactory.paramRequest(NON_EXISTING_RECIPE_INGREDIENT_INGREDIENT_NAME, BigDecimal.valueOf(0), NON_EXISTING_RECIPE_INGREDIENT_UNIT), RECIPE_INGREDIENT_BAD_REQUEST),
                Arguments.of(NON_EXISTING_RECIPE_INGREDIENT_RECIPE_NAME, RecipeIngredientFactory.paramRequest(NON_EXISTING_RECIPE_INGREDIENT_INGREDIENT_NAME,
                        BigDecimal.valueOf(-5), NON_EXISTING_RECIPE_INGREDIENT_UNIT), RECIPE_INGREDIENT_BAD_REQUEST),
                Arguments.of(NON_EXISTING_RECIPE_INGREDIENT_RECIPE_NAME, RecipeIngredientFactory.paramRequest(NON_EXISTING_RECIPE_INGREDIENT_INGREDIENT_NAME, NON_EXISTING_RECIPE_INGREDIENT_AMOUNT, null), RECIPE_INGREDIENT_BAD_REQUEST),
                Arguments.of(NON_EXISTING_RECIPE_INGREDIENT_RECIPE_NAME, RecipeIngredientFactory.paramRequest(NON_EXISTING_RECIPE_INGREDIENT_INGREDIENT_NAME, NON_EXISTING_RECIPE_INGREDIENT_AMOUNT, "kgg"), RECIPE_INGREDIENT_BAD_REQUEST)
        );
    }

    public static Stream<Arguments> badRequestForUpdateRecipeIngredient() {
        return Stream.of(
                Arguments.of(RecipeIngredientFactory.paramRequest(null, UPDATED_RECIPE_INGREDIENT_AMOUNT, UPDATE_RECIPE_INGREDIENT_UNIT), RECIPE_INGREDIENT_BAD_REQUEST),
                Arguments.of(RecipeIngredientFactory.paramRequest(UPDATED_RECIPE_INGREDIENT_INGREDIENT_NAME, null, UPDATE_RECIPE_INGREDIENT_UNIT), RECIPE_INGREDIENT_BAD_REQUEST),
                Arguments.of(RecipeIngredientFactory.paramRequest(UPDATED_RECIPE_INGREDIENT_INGREDIENT_NAME, BigDecimal.valueOf(0), UPDATE_RECIPE_INGREDIENT_UNIT), RECIPE_INGREDIENT_BAD_REQUEST),
                Arguments.of(RecipeIngredientFactory.paramRequest(UPDATED_RECIPE_INGREDIENT_INGREDIENT_NAME, BigDecimal.valueOf(-5), UPDATE_RECIPE_INGREDIENT_UNIT), RECIPE_INGREDIENT_BAD_REQUEST),
                Arguments.of(RecipeIngredientFactory.paramRequest(UPDATED_RECIPE_INGREDIENT_INGREDIENT_NAME, UPDATED_RECIPE_INGREDIENT_AMOUNT, null), RECIPE_INGREDIENT_BAD_REQUEST),
                Arguments.of(RecipeIngredientFactory.paramRequest(UPDATED_RECIPE_INGREDIENT_INGREDIENT_NAME, UPDATED_RECIPE_INGREDIENT_AMOUNT, "kgg"), RECIPE_INGREDIENT_BAD_REQUEST)
        );
    }
}
