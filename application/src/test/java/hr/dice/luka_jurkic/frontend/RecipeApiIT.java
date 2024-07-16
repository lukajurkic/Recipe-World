package hr.dice.luka_jurkic.frontend;

import hr.dice.luka_jurkic.AbstractIT;
import hr.dice.luka_jurkic.clients.RecipeTestClient;
import hr.dice.luka_jurkic.factory.RecipeFactory;
import hr.dice.luka_jurkic.persistence.repository.RecipeRepository;
import hr.dice.luka_jurkic.rest.dto.RecipeDTO;
import hr.dice.luka_jurkic.rest.request.RecipeRequest;
import io.restassured.common.mapper.TypeRef;
import jakarta.inject.Inject;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static hr.dice.luka_jurkic.constants.RecipeTestConstants.NON_EXISTING_RECIPE_ID;
import static hr.dice.luka_jurkic.constants.RecipeTestConstants.NON_EXISTING_RECIPE_NAME;
import static hr.dice.luka_jurkic.constants.RecipeTestConstants.NON_EXISTING_USER;
import static hr.dice.luka_jurkic.constants.RecipeTestConstants.RECIPES_COUNT;
import static hr.dice.luka_jurkic.constants.RecipeTestConstants.RECIPES_COUNT_FOR_USER;
import static hr.dice.luka_jurkic.constants.RecipeTestConstants.RECIPE_ID;
import static hr.dice.luka_jurkic.constants.RecipeTestConstants.RECIPE_NAME;
import static hr.dice.luka_jurkic.constants.RecipeTestConstants.RECIPE_NOT_FOUND;
import static hr.dice.luka_jurkic.constants.RecipeTestConstants.RECIPE_USER_NOT_FOUND;
import static hr.dice.luka_jurkic.constants.RecipeTestConstants.RECIPE_USER_PASSWORD;
import static hr.dice.luka_jurkic.constants.RecipeTestConstants.RECIPE_USER_USERNAME;
import static hr.dice.luka_jurkic.constants.RecipeTestConstants.UPDATED_RECIPE_NAME;
import static hr.dice.luka_jurkic.constants.RecipeTestConstants.USER_WITH_MULTIPLE_RECIPES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class RecipeApiIT extends AbstractIT {

    @Inject RecipeRepository recipeRepository;

    @Test
    @DisplayName("GET Recipe - OK")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe"})
    public void givenValidId_whenGetRecipe_thanExpect200() {
        RecipeDTO recipe = RecipeTestClient.getRecipe(RECIPE_ID)
                .then()
                .statusCode(200)
                .and().extract()
                .body().as(RecipeDTO.class);
        assertThat(recipe.getName()).isEqualTo(RECIPE_NAME);
        assertThat(recipe.getUser().getUsername()).isEqualTo(RECIPE_USER_USERNAME);
    }

    @Test
    @DisplayName("GET Recipe - Not Found")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe"})
    public void givenInvalidId_whenGetRecipe_theExpect404() {
        RecipeTestClient.getRecipe(NON_EXISTING_RECIPE_ID)
                .then()
                .statusCode(404)
                .body("errorKey", equalTo(RECIPE_NOT_FOUND));
    }

    @Test
    @DisplayName("GET Recipes by Name - OK")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe"})
    public void givenValidName_whenGetRecipeByName_thenExpect200() {
        RecipeDTO recipe = RecipeTestClient.getRecipeByName(RECIPE_NAME)
                .then()
                .statusCode(200)
                .and().extract()
                .body().as(RecipeDTO.class);
        assertThat(recipe.getName()).isEqualTo(RECIPE_NAME);
        assertThat(recipe.getUser().getUsername()).isEqualTo(RECIPE_USER_USERNAME);
    }

    @Test
    @DisplayName("GET Recipe by Name - Not Found")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe"})
    public void givenInvalidName_whenGetRecipeByName_thenExpect404() {
        RecipeTestClient.getRecipeByName(NON_EXISTING_RECIPE_NAME)
                .then()
                .statusCode(404)
                .body("errorKey", equalTo(RECIPE_NOT_FOUND));
    }

    @Test
    @DisplayName("GET Recipes - OK")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe"})
    public void givenNothing_whenGetRecipes_thanExpect200() {
        List<RecipeDTO> recipes = RecipeTestClient.getRecipes()
                .then()
                .statusCode(200)
                .and().extract()
                .body().as(new TypeRef<>() {
                });
        assertThat(recipes).isNotNull();
        assertThat(recipes.size()).isEqualTo(RECIPES_COUNT);
    }

    @Test
    @DisplayName("GET Recipes by User - OK")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe"})
    public void givenValidUsername_whenGetRecipesByUser_thenExpect200() {
        List<RecipeDTO> recipes = RecipeTestClient.getRecipesByUser(USER_WITH_MULTIPLE_RECIPES)
                .then()
                .statusCode(200)
                .and().extract()
                .body().as(new TypeRef<>() {
                });
        assertThat(recipes).isNotNull();
        assertThat(recipes.size()).isEqualTo(RECIPES_COUNT_FOR_USER);
    }

    @Test
    @DisplayName("GET Recipes by User - Not Found")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe"})
    public void givenInvalidUsername_whenGetRecipesByUser_thenExpect404() {
        RecipeTestClient.getRecipesByUser(NON_EXISTING_USER)
                .then()
                .statusCode(404)
                .body("errorKey", equalTo(RECIPE_USER_NOT_FOUND));
    }

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.RecipeParameters#loginCredentials")
    @DisplayName("POST Recipe - OK")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe"})
    public void givenValidRequest_whenCreateRecipe_thenExpect200(String username, String password) {
        RecipeDTO recipe = RecipeTestClient.createRecipe(username, password, RecipeFactory.validRequest())
                .then()
                .statusCode(200)
                .and().extract()
                .body().as(RecipeDTO.class);

        assertThat(recipe.getName()).isEqualTo(NON_EXISTING_RECIPE_NAME);
        assertThat(recipe.getUser().getUsername()).isEqualTo(username);
    }

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.RecipeParameters#badRequestForCreateAndUpdateAndUpdateByNameRecipe")
    @DisplayName("POST Recipe - Bad Request")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe"})
    public void givenInvalidRequest_whenCreateUser_thenExpect400(RecipeRequest request, String errorKey) {
        RecipeTestClient.createRecipe(RECIPE_USER_USERNAME, RECIPE_USER_PASSWORD, request)
                .then()
                .statusCode(400)
                .body("errorKey", equalTo(errorKey));
    }

    @Test
    @DisplayName("DELETE Recipe - OK")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe"})
    public void givenValidId_whenDeleteRecipe_thenExpect200() {
        RecipeTestClient.deleteRecipe(RECIPE_USER_USERNAME, RECIPE_USER_PASSWORD, RECIPE_ID)
                .then()
                .statusCode(200);
        assertThat(recipeRepository.findById(RECIPE_ID)).isNotPresent();
    }

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.RecipeParameters#loginCredentials")
    @DisplayName("DELETE Recipe - Not Found")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe"})
    public void givenInvalidId_whenDeleteRecipe_thenExpect404(String username, String password) {
        RecipeTestClient.deleteRecipe(username, password, NON_EXISTING_RECIPE_ID)
                .then()
                .statusCode(404)
                .body("errorKey", equalTo(RECIPE_NOT_FOUND));
    }

    @Test
    @DisplayName("DELETE Recipe by Name - OK")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe"})
    public void givenValidName_whenDeleteRecipeByName_thenExpect200() {
        RecipeTestClient.deleteRecipeByName(RECIPE_USER_USERNAME, RECIPE_USER_PASSWORD, RECIPE_NAME)
                .then()
                .statusCode(200);
        assertThat(recipeRepository.findByName(RECIPE_NAME)).isNotPresent();
    }

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.RecipeParameters#loginCredentials")
    @DisplayName("DELETE Recipe by Name - Not Found")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe"})
    public void givenInvalidName_whenDeleteRecipeByName_thenExpect404(String username, String password) {
        RecipeTestClient.deleteRecipeByName(username, password, NON_EXISTING_RECIPE_NAME)
                .then()
                .statusCode(404)
                .body("errorKey", equalTo(RECIPE_NOT_FOUND));
    }

    @Test
    @DisplayName("PUT Recipe - OK")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe"})
    public void givenValidIdAndValidRequest_whenUpdateRecipe_thenExpect200() {
        RecipeDTO recipe = RecipeTestClient.updateRecipe(RECIPE_USER_USERNAME, RECIPE_USER_PASSWORD, RECIPE_ID,
                RecipeFactory.paramRequest(UPDATED_RECIPE_NAME))
                .then()
                .statusCode(200)
                .and().extract()
                .body().as(RecipeDTO.class);
        assertThat(recipe.getName()).isEqualTo(UPDATED_RECIPE_NAME);
        assertThat(recipe.getUser().getUsername()).isEqualTo(RECIPE_USER_USERNAME);
    }

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.RecipeParameters#loginCredentials")
    @DisplayName("PUT Recipe - Not Found")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe"})
    public void givenInvalidIdAndValidRequest_whenUpdateRecipe_thenExpect404(String username, String password) {
        RecipeTestClient.updateRecipe(username, password, NON_EXISTING_RECIPE_ID,
                        RecipeFactory.paramRequest(UPDATED_RECIPE_NAME))
                .then()
                .statusCode(404)
                .body("errorKey", equalTo(RECIPE_NOT_FOUND));
    }

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.RecipeParameters#badRequestForCreateAndUpdateAndUpdateByNameRecipe")
    @DisplayName("PUT Recipe - Bad Request")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe"})
    public void givenValidIdAndInvalidRequest_whenUpdateRecipe_thenExpect400(RecipeRequest request, String errorKey) {
        RecipeTestClient.updateRecipe(RECIPE_USER_USERNAME, RECIPE_USER_PASSWORD, RECIPE_ID, request)
                .then()
                .statusCode(400)
                .body("errorKey", equalTo(errorKey));
    }

    @Test
    @DisplayName("PUT Recipe by Name - OK")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe"})
    public void givenValidNameAndValidRequest_whenUpdateRecipeByName_thenExpect200() {
        RecipeDTO recipe = RecipeTestClient.updateRecipeByName(RECIPE_USER_USERNAME, RECIPE_USER_PASSWORD, RECIPE_NAME,
                        RecipeFactory.paramRequest(UPDATED_RECIPE_NAME))
                .then()
                .statusCode(200)
                .and().extract()
                .body().as(RecipeDTO.class);

        assertThat(recipe.getName()).isEqualTo(UPDATED_RECIPE_NAME);
        assertThat(recipe.getUser().getUsername()).isEqualTo(RECIPE_USER_USERNAME);
    }

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.RecipeParameters#loginCredentials")
    @DisplayName("PUT Recipe by Name - Not Found")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe"})
    public void givenInvalidNameAndValidRequest_whenUpdateRecipeByName_thenExpect404(String username, String password) {
        RecipeTestClient.updateRecipeByName(username, password, NON_EXISTING_RECIPE_NAME,
                        RecipeFactory.paramRequest(UPDATED_RECIPE_NAME))
                .then()
                .statusCode(404)
                .body("errorKey", equalTo(RECIPE_NOT_FOUND));
    }

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.RecipeParameters#badRequestForCreateAndUpdateAndUpdateByNameRecipe")
    @DisplayName("PUT Recipe by Name - Bad Request")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe"})
    public void givenValidNameAndInvalidRequest_whenUpdateRecipeByName_thenExpect400(RecipeRequest request, String errorKey) {
        RecipeTestClient.updateRecipeByName(RECIPE_USER_USERNAME, RECIPE_USER_PASSWORD, RECIPE_NAME, request)
                .then()
                .statusCode(400)
                .body("errorKey", equalTo(errorKey));
    }

}
