package hr.dice.luka_jurkic.frontend;

import hr.dice.luka_jurkic.AbstractIT;
import hr.dice.luka_jurkic.clients.StepTestClient;
import hr.dice.luka_jurkic.factory.StepFactory;
import hr.dice.luka_jurkic.persistence.repository.StepRepository;
import hr.dice.luka_jurkic.rest.dto.StepDTO;
import io.restassured.common.mapper.TypeRef;
import jakarta.inject.Inject;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static hr.dice.luka_jurkic.constants.RecipeTestConstants.RECIPE_NOT_FOUND;
import static hr.dice.luka_jurkic.constants.StepTestConstants.NEW_STEP_NUMBER;
import static hr.dice.luka_jurkic.constants.StepTestConstants.NON_EXISTING_STEP_DESCRIPTION;
import static hr.dice.luka_jurkic.constants.StepTestConstants.NON_EXISTING_STEP_NUMBER;
import static hr.dice.luka_jurkic.constants.StepTestConstants.NON_EXISTING_STEP_RECIPE_NAME;
import static hr.dice.luka_jurkic.constants.StepTestConstants.STEP_BAD_REQUEST;
import static hr.dice.luka_jurkic.constants.StepTestConstants.STEP_DESCRIPTION;
import static hr.dice.luka_jurkic.constants.StepTestConstants.STEP_ID;
import static hr.dice.luka_jurkic.constants.StepTestConstants.STEP_NOT_FOUND;
import static hr.dice.luka_jurkic.constants.StepTestConstants.STEP_NUMBER;
import static hr.dice.luka_jurkic.constants.StepTestConstants.STEP_RECIPE_NAME;
import static hr.dice.luka_jurkic.constants.StepTestConstants.UPDATED_STEP_DESCRIPTION;
import static hr.dice.luka_jurkic.constants.StepTestConstants.UPDATE_STEP_DESCRIPTION_NUMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class StepApiIT extends AbstractIT {

    @Inject StepRepository stepRepository;

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.StepParameters#users")
    @DisplayName("GET Step - OK")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe", "migrations/step"})
    public void givenValidRecipeNameAndStepNumber_whenGetStep_thenExpect200(String username, String password) {
        StepDTO step = StepTestClient.getStep(username, password, STEP_RECIPE_NAME, STEP_NUMBER)
                .then()
                .statusCode(200)
                .and().extract()
                .body().as(StepDTO.class);
        assertThat(step.getNumber()).isEqualTo(STEP_NUMBER);
        assertThat(step.getRecipe().getName()).isEqualTo(STEP_RECIPE_NAME);
        assertThat(step.getDescription()).isEqualTo(STEP_DESCRIPTION);
        assertThat(step.getId()).isEqualTo(STEP_ID);
    }

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.StepParameters#notFoundForGetStepAndDeleteStep")
    @DisplayName("GET Step - Not Found")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe", "migrations/step"})
    public void givenInvalidRecipeNameOrStepNumber_whenGetStep_thenExpect404(String username, String password, String recipeName, Integer stepNumber){
        StepTestClient.getStep(username, password, recipeName, stepNumber)
                .then()
                .statusCode(404)
                .body("errorKey", equalTo(STEP_NOT_FOUND));
    }

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.StepParameters#okForGetSteps")
    @DisplayName("GET Steps - OK")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe", "migrations/step"})
    public void givenValidRecipeName_whenGetSteps_thenExpect200(String username, String password, String recipeName, Integer numberOfSteps) {
        List<StepDTO> steps = StepTestClient.getSteps(username, password, recipeName)
                .then()
                .statusCode(200)
                .and().extract()
                .body().as(new TypeRef<>() {});
        assertThat(steps).isNotNull();
        assertThat(steps.size()).isEqualTo(numberOfSteps);
    }

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.StepParameters#users")
    @DisplayName("POST Step - OK")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe", "migrations/step"})
    public void givenValidRequest_whenCreateStep_thenExpect200(String username, String password) {
        StepDTO step = StepTestClient.createStep(username, password, StepFactory.validRequest())
                .then()
                .statusCode(200)
                .and().extract()
                .body().as(StepDTO.class);
        assertThat(step.getDescription()).isEqualTo(NON_EXISTING_STEP_DESCRIPTION);
        assertThat(step.getRecipe().getName()).isEqualTo(NON_EXISTING_STEP_RECIPE_NAME);
        assertThat(step.getNumber()).isEqualTo(NEW_STEP_NUMBER);
    }

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.StepParameters#users")
    @DisplayName("POST Step - Bad Request")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe", "migrations/step"})
    public void givenInvalidRequest_whenCreateStep_expect400(String username, String password) {
        StepTestClient.createStep(username, password, StepFactory.paramRequest(null))
                .then()
                .statusCode(400)
                .body("errorKey", equalTo(STEP_BAD_REQUEST));
    }

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.StepParameters#users")
    @DisplayName("POST Step - Recipe Not Found")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe", "migrations/step"})
    public void givenInvalidRequest_whenCreateStep_expect404(String username, String password) {
        StepTestClient.createStep(username, password, StepFactory.paramRequest(NON_EXISTING_STEP_DESCRIPTION))
                .then()
                .statusCode(404)
                .body("errorKey", equalTo(RECIPE_NOT_FOUND));
    }

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.StepParameters#users")
    @DisplayName("DELETE Step - OK")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe", "migrations/step"})
    public void givenValidRecipeNameAndStepNumber_whenDeleteStep_thenExpect200(String username, String password) {
        StepTestClient.deleteStep(username, password, STEP_RECIPE_NAME, STEP_NUMBER)
                .then()
                .statusCode(200);
        assertThat(stepRepository.findByRecipeNameAndNumber(STEP_RECIPE_NAME, STEP_NUMBER)).isNotPresent();
    }

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.StepParameters#notFoundForGetStepAndDeleteStep")
    @DisplayName("DELETE Step - Step Not Found")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe", "migrations/step"})
    public void givenInvalidRecipeNameOrStepNumber_whenDeleteStep_thenExpect404(String username, String password, String recipeName, Integer stepNumber) {
        StepTestClient.deleteStep(username, password, recipeName, stepNumber)
                .then()
                .statusCode(404)
                .body("errorKey", equalTo(STEP_NOT_FOUND));
    }

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.StepParameters#users")
    @DisplayName("PUT Step - OK")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe", "migrations/step"})
    public void givenValidStepNumberAndRequest_whenUpdateStep_thenExpect200(String username, String password) {
        StepDTO step = StepTestClient.updateStep(username, password, UPDATE_STEP_DESCRIPTION_NUMBER, StepFactory.paramRequest(UPDATED_STEP_DESCRIPTION))
                .then()
                .statusCode(200)
                .and().extract()
                .body().as(StepDTO.class);
        assertThat(step.getNumber()).isEqualTo(UPDATE_STEP_DESCRIPTION_NUMBER);
        assertThat(step.getDescription()).isEqualTo(UPDATED_STEP_DESCRIPTION);
        assertThat(step.getId()).isEqualTo(STEP_ID);
        assertThat(step.getRecipe().getName()).isEqualTo(STEP_RECIPE_NAME);
    }

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.StepParameters#users")
    @DisplayName("DELETE Step - Bad Request")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe", "migrations/step"})
    public void givenValidStepNumberAndInvalidRequest_whenUpdateStep_thenExpect400(String username, String password) {
        StepTestClient.updateStep(username, password, UPDATE_STEP_DESCRIPTION_NUMBER, StepFactory.paramRequest(null))
                .then()
                .statusCode(400)
                .body("errorKey", equalTo(STEP_BAD_REQUEST));
    }

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.StepParameters#users")
    @DisplayName("DELETE Step - Step Not Found")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe", "migrations/step"})
    public void givenInvalidStepNumberAndValidRequest_whenUpdateStep_thenExpect404(String username, String password) {
        StepTestClient.updateStep(username, password, NON_EXISTING_STEP_NUMBER, StepFactory.paramRequest(UPDATED_STEP_DESCRIPTION))
                .then()
                .statusCode(404)
                .body("errorKey", equalTo(STEP_NOT_FOUND));
    }
}
