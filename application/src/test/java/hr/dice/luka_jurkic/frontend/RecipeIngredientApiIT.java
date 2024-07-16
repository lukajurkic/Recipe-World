package hr.dice.luka_jurkic.frontend;

import hr.dice.luka_jurkic.AbstractIT;
import hr.dice.luka_jurkic.clients.RecipeIngredientTestClient;
import hr.dice.luka_jurkic.factory.RecipeIngredientFactory;
import hr.dice.luka_jurkic.persistence.repository.RecipeIngredientRepository;
import hr.dice.luka_jurkic.rest.dto.RecipeIngredientDTO;
import hr.dice.luka_jurkic.rest.request.RecipeIngredientRequest;
import io.restassured.common.mapper.TypeRef;
import jakarta.inject.Inject;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static hr.dice.luka_jurkic.constants.IngredientTestConstants.INGREDIENT_NOT_FOUND;
import static hr.dice.luka_jurkic.constants.IngredientTestConstants.NON_EXISTING_INGREDIENT_NAME;
import static hr.dice.luka_jurkic.constants.RecipeIngredientTestConstants.NON_EXISTING_RECIPE_INGREDIENT_AMOUNT;
import static hr.dice.luka_jurkic.constants.RecipeIngredientTestConstants.NON_EXISTING_RECIPE_INGREDIENT_INGREDIENT_NAME;
import static hr.dice.luka_jurkic.constants.RecipeIngredientTestConstants.NON_EXISTING_RECIPE_INGREDIENT_RECIPE_NAME;
import static hr.dice.luka_jurkic.constants.RecipeIngredientTestConstants.NON_EXISTING_RECIPE_INGREDIENT_UNIT;
import static hr.dice.luka_jurkic.constants.RecipeIngredientTestConstants.NOT_IN_DATABASE_RECIPE_INGREDIENT_INGREDIENT_NAME;
import static hr.dice.luka_jurkic.constants.RecipeIngredientTestConstants.RECIPE_INGREDIENTS_COUNT;
import static hr.dice.luka_jurkic.constants.RecipeIngredientTestConstants.RECIPE_INGREDIENT_ID;
import static hr.dice.luka_jurkic.constants.RecipeIngredientTestConstants.RECIPE_INGREDIENT_INGREDIENT_NAME;
import static hr.dice.luka_jurkic.constants.RecipeIngredientTestConstants.RECIPE_INGREDIENT_RECIPE_NAME;
import static hr.dice.luka_jurkic.constants.RecipeIngredientTestConstants.UPDATED_RECIPE_INGREDIENT_AMOUNT;
import static hr.dice.luka_jurkic.constants.RecipeIngredientTestConstants.UPDATED_RECIPE_INGREDIENT_INGREDIENT_NAME;
import static hr.dice.luka_jurkic.constants.RecipeIngredientTestConstants.UPDATE_RECIPE_INGREDIENT_UNIT;
import static hr.dice.luka_jurkic.constants.RecipeTestConstants.NON_EXISTING_RECIPE_NAME;
import static hr.dice.luka_jurkic.constants.RecipeTestConstants.RECIPE_ACCESS_DENIED;
import static hr.dice.luka_jurkic.constants.RecipeTestConstants.RECIPE_NAME;
import static hr.dice.luka_jurkic.constants.RecipeTestConstants.RECIPE_NOT_FOUND;
import static hr.dice.luka_jurkic.constants.UserTestConstants.ADMIN_PASSWORD;
import static hr.dice.luka_jurkic.constants.UserTestConstants.ADMIN_USERNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RecipeIngredientApiIT extends AbstractIT {

    @Inject RecipeIngredientRepository recipeIngredientRepository;

    @Test
    @DisplayName("GET Recipe Ingredients - OK")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe", "migrations/ingredient", "migrations/recipe-ingredient"})
    public void givenValidRecipeName_whenGetRecipeIngredients_thenExpect200() {
        List<RecipeIngredientDTO> ingredients = RecipeIngredientTestClient.getRecipeIngredients(RECIPE_NAME)
                .then()
                .statusCode(200)
                .and().extract()
                .body().as(new TypeRef<>() {});
        assertThat(ingredients).isNotNull();
        assertThat(ingredients.size()).isEqualTo(RECIPE_INGREDIENTS_COUNT);
    }

    @Test
    @DisplayName("POST Recipe Ingredient - OK")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe", "migrations/ingredient", "migrations/recipe-ingredient"})
    public void givenValidRecipeNameAndValidRequest_whenCreateRecipeIngredient_thenExpect200() {
        RecipeIngredientDTO recipeIngredientDTO = RecipeIngredientTestClient.createRecipeIngredient(ADMIN_USERNAME, ADMIN_PASSWORD,
                        NON_EXISTING_RECIPE_INGREDIENT_RECIPE_NAME, RecipeIngredientFactory.validRequest())
                .then()
                .statusCode(200)
                .and().extract()
                .body().as(RecipeIngredientDTO.class);
        assertThat(recipeIngredientDTO.getRecipeName()).isEqualTo(NON_EXISTING_RECIPE_INGREDIENT_RECIPE_NAME);
        assertThat(recipeIngredientDTO.getIngredientName()).isEqualTo(NON_EXISTING_RECIPE_INGREDIENT_INGREDIENT_NAME);
        assertThat(recipeIngredientDTO.getUnit()).isEqualTo(String.valueOf(NON_EXISTING_RECIPE_INGREDIENT_UNIT));
        assertThat(recipeIngredientDTO.getAmount()).isEqualTo(NON_EXISTING_RECIPE_INGREDIENT_AMOUNT);
    }

    @Test
    @DisplayName("POST Recipe Ingredient - Recipe Not Found")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe", "migrations/ingredient", "migrations/recipe-ingredient"})
    public void givenInvalidRecipeNameAndValidRequest_whenCreateRecipeIngredient_thenExpect404() {
        RecipeIngredientTestClient.createRecipeIngredient(ADMIN_USERNAME, ADMIN_PASSWORD, NON_EXISTING_RECIPE_NAME, RecipeIngredientFactory.validRequest())
                .then()
                .statusCode(404)
                .body("errorKey", equalTo(RECIPE_NOT_FOUND));
    }

    @Test
    @DisplayName("POST Recipe Ingredient - Ingredient Not Found")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe", "migrations/ingredient", "migrations/recipe-ingredient"})
    public void givenValidRecipeNameAndInvalidRequest_whenCreateRecipeIngredient_thenExpect404() {
        RecipeIngredientTestClient.createRecipeIngredient(ADMIN_USERNAME, ADMIN_PASSWORD, RECIPE_INGREDIENT_RECIPE_NAME,
                        RecipeIngredientFactory.paramRequest(NOT_IN_DATABASE_RECIPE_INGREDIENT_INGREDIENT_NAME, NON_EXISTING_RECIPE_INGREDIENT_AMOUNT, NON_EXISTING_RECIPE_INGREDIENT_UNIT))
                .then()
                .statusCode(404)
                .body("errorKey", equalTo(INGREDIENT_NOT_FOUND));
    }

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.RecipeIngredientParameters#badRequestForCreateRecipeIngredient")
    @DisplayName("POST Recipe Ingredient - Bad Request")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe", "migrations/ingredient", "migrations/recipe-ingredient"})
    public void givenValidRecipeNameAndInvalidRequest_whenCreateRecipeIngredient_thenExpect400(String recipeName, RecipeIngredientRequest request, String errorKey) {
        RecipeIngredientTestClient.createRecipeIngredient(ADMIN_USERNAME, ADMIN_PASSWORD, recipeName, request)
                .then()
                .statusCode(400)
                .body("errorKey", equalTo(errorKey));
    }

    @Test
    @DisplayName("DELETE Recipe Ingredient - OK")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe", "migrations/ingredient", "migrations/recipe-ingredient"})
    public void givenValidRecipeNameAndValidIngredientName_whenDeleteRecipeIngredient_thenExpect200() {
        RecipeIngredientTestClient.deleteRecipeIngredient(ADMIN_USERNAME, ADMIN_PASSWORD, RECIPE_INGREDIENT_RECIPE_NAME, RECIPE_INGREDIENT_INGREDIENT_NAME)
                .then()
                .statusCode(200);
        assertThat(recipeIngredientRepository.findById(RECIPE_INGREDIENT_ID)).isNotPresent();
    }

    @Test
    @DisplayName("DELETE Recipe Ingredient - Recipe Not Found")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe", "migrations/ingredient", "migrations/recipe-ingredient"})
    public void givenInvalidRecipeNameAndValidIngredientName_whenDeleteRecipeIngredient_thenExpect404() {
        RecipeIngredientTestClient.deleteRecipeIngredient(ADMIN_USERNAME, ADMIN_PASSWORD, NON_EXISTING_RECIPE_NAME, RECIPE_INGREDIENT_INGREDIENT_NAME)
                .then()
                .statusCode(403)
                .body("errorKey", equalTo(RECIPE_ACCESS_DENIED));
    }

    @Test
    @DisplayName("DELETE Recipe Ingredient - Ingredient Not Found")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe", "migrations/ingredient", "migrations/recipe-ingredient"})
    public void givenValidRecipeNameAndInvalidIngredientName_whenDeleteRecipeIngredient_thenExpect404() {
        RecipeIngredientTestClient.deleteRecipeIngredient(ADMIN_USERNAME, ADMIN_PASSWORD, RECIPE_INGREDIENT_RECIPE_NAME, NON_EXISTING_INGREDIENT_NAME)
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("PUT Recipe Ingredient - OK")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe", "migrations/ingredient", "migrations/recipe-ingredient"})
    public void givenValidRecipeNameAndValidRequest_whenUpdateRecipeIngredient_thenExpect200() {
        RecipeIngredientDTO recipeIngredient = RecipeIngredientTestClient.updateRecipeIngredient(ADMIN_USERNAME, ADMIN_PASSWORD, RECIPE_INGREDIENT_RECIPE_NAME, RECIPE_INGREDIENT_INGREDIENT_NAME,
                        RecipeIngredientFactory.paramRequest(UPDATED_RECIPE_INGREDIENT_INGREDIENT_NAME, UPDATED_RECIPE_INGREDIENT_AMOUNT, UPDATE_RECIPE_INGREDIENT_UNIT))
                .then()
                .statusCode(200)
                .and().extract()
                .body().as(RecipeIngredientDTO.class);
        assertThat(recipeIngredient.getIngredientName()).isEqualTo(UPDATED_RECIPE_INGREDIENT_INGREDIENT_NAME);
        assertThat(recipeIngredient.getRecipeName()).isEqualTo(RECIPE_INGREDIENT_RECIPE_NAME);
        assertThat(recipeIngredient.getAmount()).isEqualTo(UPDATED_RECIPE_INGREDIENT_AMOUNT);
        assertThat(recipeIngredient.getUnit()).isEqualTo(UPDATE_RECIPE_INGREDIENT_UNIT);
    }

    @Test
    @DisplayName("PUT Recipe Ingredient - Recipe Not Found")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe", "migrations/ingredient", "migrations/recipe-ingredient"})
    public void givenInvalidRecipeNameAndValidRequest_whenUpdateRecipeIngredient_thenExpect200() {
        RecipeIngredientTestClient.updateRecipeIngredient(ADMIN_USERNAME, ADMIN_PASSWORD, NON_EXISTING_RECIPE_NAME, RECIPE_INGREDIENT_RECIPE_NAME,
                        RecipeIngredientFactory.paramRequest(NON_EXISTING_RECIPE_INGREDIENT_INGREDIENT_NAME, NON_EXISTING_RECIPE_INGREDIENT_AMOUNT, NON_EXISTING_RECIPE_INGREDIENT_UNIT))
                .then()
                .statusCode(404)
                .body("errorKey", equalTo(RECIPE_NOT_FOUND));
    }

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.RecipeIngredientParameters#badRequestForUpdateRecipeIngredient")
    @DisplayName("PUT Recipe Ingredient - Bad Request")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe", "migrations/ingredient", "migrations/recipe-ingredient"})
    public void givenValidRecipeNameAndInvalidRequest_whenUpdateRecipeIngredient_thenExpect400(RecipeIngredientRequest request, String errorKey) {
        RecipeIngredientTestClient.updateRecipeIngredient(ADMIN_USERNAME, ADMIN_PASSWORD, RECIPE_INGREDIENT_RECIPE_NAME, RECIPE_INGREDIENT_INGREDIENT_NAME, request)
                .then()
                .statusCode(400)
                .body("errorKey", equalTo(errorKey));
    }
}
