package hr.dice.luka_jurkic.parameters;

import hr.dice.luka_jurkic.factory.IngredientFactory;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static hr.dice.luka_jurkic.constants.IngredientTestConstants.ANOTHER_INGREDIENT_CATEGORY;
import static hr.dice.luka_jurkic.constants.IngredientTestConstants.ANOTHER_INGREDIENT_NAME;
import static hr.dice.luka_jurkic.constants.IngredientTestConstants.EMPTY_LIST_COUNT;
import static hr.dice.luka_jurkic.constants.IngredientTestConstants.GET_BY_CATEGORY_INGREDIENTS_COUNT;
import static hr.dice.luka_jurkic.constants.IngredientTestConstants.GET_BY_CATEGORY_INGREDIENT_CATEGORY;
import static hr.dice.luka_jurkic.constants.IngredientTestConstants.GET_BY_CATEGORY_NON_EXISTING_INGREDIENT_CATEGORY;
import static hr.dice.luka_jurkic.constants.IngredientTestConstants.INGREDIENT_ALREADY_EXISTS;
import static hr.dice.luka_jurkic.constants.IngredientTestConstants.INGREDIENT_BAD_REQUEST;
import static hr.dice.luka_jurkic.constants.IngredientTestConstants.INGREDIENT_CATEGORY;
import static hr.dice.luka_jurkic.constants.IngredientTestConstants.INGREDIENT_NAME;
import static hr.dice.luka_jurkic.constants.IngredientTestConstants.INVALID_INGREDIENT_CATEGORY;
import static hr.dice.luka_jurkic.constants.IngredientTestConstants.MISTYPED_INGREDIENT_CATEGORY;
import static hr.dice.luka_jurkic.constants.IngredientTestConstants.NON_EXISTING_INGREDIENT_CATEGORY;
import static hr.dice.luka_jurkic.constants.IngredientTestConstants.NON_EXISTING_INGREDIENT_NAME;
import static hr.dice.luka_jurkic.constants.IngredientTestConstants.UPDATED_INGREDIENT_CATEGORY;
import static hr.dice.luka_jurkic.constants.IngredientTestConstants.UPDATED_INGREDIENT_NAME;
import static hr.dice.luka_jurkic.constants.UserTestConstants.ADMIN_PASSWORD;
import static hr.dice.luka_jurkic.constants.UserTestConstants.ADMIN_USERNAME;
import static hr.dice.luka_jurkic.constants.UserTestConstants.USER_PASSWORD;
import static hr.dice.luka_jurkic.constants.UserTestConstants.USER_USERNAME;

public class IngredientParameters {

    public static Stream<Arguments> okForGetIngredientsByCategory() {
        return Stream.of(
                Arguments.of(GET_BY_CATEGORY_INGREDIENT_CATEGORY, GET_BY_CATEGORY_INGREDIENTS_COUNT),
                Arguments.of(GET_BY_CATEGORY_NON_EXISTING_INGREDIENT_CATEGORY, EMPTY_LIST_COUNT)
        );
    }

    public static Stream<Arguments> notFoundForGetIngredientsByCategory() {
        return Stream.of(
                Arguments.of(INVALID_INGREDIENT_CATEGORY),
                Arguments.of(MISTYPED_INGREDIENT_CATEGORY)
        );
    }

    public static Stream<Arguments> okForCreateIngredient() {
        return Stream.of(
                Arguments.of(ADMIN_USERNAME, ADMIN_PASSWORD),
                Arguments.of(USER_USERNAME, USER_PASSWORD)
        );
    }

    public static Stream<Arguments> badRequestForCreateIngredients() {
        return Stream.of(
                Arguments.of(ADMIN_USERNAME, ADMIN_PASSWORD, IngredientFactory.paramRequest(null, NON_EXISTING_INGREDIENT_CATEGORY), INGREDIENT_BAD_REQUEST),
                Arguments.of(ADMIN_USERNAME, ADMIN_PASSWORD, IngredientFactory.paramRequest(NON_EXISTING_INGREDIENT_NAME, null), INGREDIENT_BAD_REQUEST),
                Arguments.of(ADMIN_USERNAME, ADMIN_PASSWORD, IngredientFactory.paramRequest(INGREDIENT_NAME, INGREDIENT_CATEGORY), INGREDIENT_ALREADY_EXISTS),
                Arguments.of(USER_USERNAME, USER_PASSWORD, IngredientFactory.paramRequest(null, NON_EXISTING_INGREDIENT_CATEGORY), INGREDIENT_BAD_REQUEST),
                Arguments.of(USER_USERNAME, USER_PASSWORD, IngredientFactory.paramRequest(NON_EXISTING_INGREDIENT_NAME, null), INGREDIENT_BAD_REQUEST),
                Arguments.of(USER_USERNAME, USER_PASSWORD, IngredientFactory.paramRequest(INGREDIENT_NAME, INGREDIENT_CATEGORY), INGREDIENT_ALREADY_EXISTS)
        );
    }

    public static Stream<Arguments> badRequestForUpdateIngredient() {
        return Stream.of(
                Arguments.of(IngredientFactory.paramRequest(UPDATED_INGREDIENT_NAME, null), INGREDIENT_BAD_REQUEST),
                Arguments.of(IngredientFactory.paramRequest(null, UPDATED_INGREDIENT_CATEGORY), INGREDIENT_BAD_REQUEST),
                Arguments.of(IngredientFactory.paramRequest(ANOTHER_INGREDIENT_NAME, ANOTHER_INGREDIENT_CATEGORY), INGREDIENT_ALREADY_EXISTS)
        );
    }
}
