package hr.dice.luka_jurkic.parameters;

import hr.dice.luka_jurkic.factory.RecipeFactory;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static hr.dice.luka_jurkic.constants.RecipeTestConstants.OTHER_RECIPE_NAME;
import static hr.dice.luka_jurkic.constants.RecipeTestConstants.RECIPE_ALREADY_EXISTS;
import static hr.dice.luka_jurkic.constants.RecipeTestConstants.RECIPE_BAD_REQUEST;
import static hr.dice.luka_jurkic.constants.UserTestConstants.ADMIN_PASSWORD;
import static hr.dice.luka_jurkic.constants.UserTestConstants.ADMIN_USERNAME;
import static hr.dice.luka_jurkic.constants.UserTestConstants.USER_PASSWORD;
import static hr.dice.luka_jurkic.constants.UserTestConstants.USER_USERNAME;

public class RecipeParameters {

    public static Stream<Arguments> loginCredentials() {
        return Stream.of(
                Arguments.of(ADMIN_USERNAME, ADMIN_PASSWORD),
                Arguments.of(USER_USERNAME, USER_PASSWORD)
        );
    }

    public static Stream<Arguments> badRequestForCreateAndUpdateAndUpdateByNameRecipe() {
        return Stream.of(
                Arguments.of(RecipeFactory.paramRequest(null), RECIPE_BAD_REQUEST),
                Arguments.of(RecipeFactory.paramRequest(OTHER_RECIPE_NAME), RECIPE_ALREADY_EXISTS)
        );
    }
}
