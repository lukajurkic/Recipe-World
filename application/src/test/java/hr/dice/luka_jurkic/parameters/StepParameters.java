package hr.dice.luka_jurkic.parameters;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static hr.dice.luka_jurkic.constants.RecipeTestConstants.NON_EXISTING_RECIPE_NAME;
import static hr.dice.luka_jurkic.constants.StepTestConstants.NON_EXISTING_STEP_NUMBER;
import static hr.dice.luka_jurkic.constants.StepTestConstants.NUMBER_OF_STEPS_FOR_RECIPE_NAME;
import static hr.dice.luka_jurkic.constants.StepTestConstants.STEP_NUMBER;
import static hr.dice.luka_jurkic.constants.StepTestConstants.STEP_RECIPE_NAME;
import static hr.dice.luka_jurkic.constants.UserTestConstants.ADMIN_PASSWORD;
import static hr.dice.luka_jurkic.constants.UserTestConstants.ADMIN_USERNAME;
import static hr.dice.luka_jurkic.constants.UserTestConstants.USER_PASSWORD;
import static hr.dice.luka_jurkic.constants.UserTestConstants.USER_USERNAME;

public class StepParameters {

    public static Stream<Arguments> users() {
        return Stream.of(
                Arguments.of(ADMIN_USERNAME, ADMIN_PASSWORD),
                Arguments.of(USER_USERNAME, USER_PASSWORD)
        );
    }

    public static Stream<Arguments> notFoundForGetStepAndDeleteStep() {
        return Stream.of(
                Arguments.of(ADMIN_USERNAME, ADMIN_PASSWORD, NON_EXISTING_RECIPE_NAME, STEP_NUMBER),
                Arguments.of(ADMIN_USERNAME, ADMIN_PASSWORD, STEP_RECIPE_NAME, NON_EXISTING_STEP_NUMBER),
                Arguments.of(USER_USERNAME, USER_PASSWORD, NON_EXISTING_RECIPE_NAME, STEP_NUMBER),
                Arguments.of(USER_USERNAME, USER_PASSWORD, STEP_RECIPE_NAME, NON_EXISTING_STEP_NUMBER)
        );
    }

    public static Stream<Arguments> okForGetSteps() {
        return Stream.of(
                Arguments.of(ADMIN_USERNAME, ADMIN_PASSWORD, STEP_RECIPE_NAME, NUMBER_OF_STEPS_FOR_RECIPE_NAME),
                Arguments.of(ADMIN_USERNAME, ADMIN_PASSWORD, NON_EXISTING_RECIPE_NAME, 0),
                Arguments.of(USER_USERNAME, USER_PASSWORD, STEP_RECIPE_NAME, NUMBER_OF_STEPS_FOR_RECIPE_NAME),
                Arguments.of(USER_USERNAME, USER_PASSWORD, NON_EXISTING_RECIPE_NAME, 0)
        );
    }
}
