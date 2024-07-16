package hr.dice.luka_jurkic.frontend;

import hr.dice.luka_jurkic.AbstractIT;
import hr.dice.luka_jurkic.clients.IngredientTestClient;
import hr.dice.luka_jurkic.factory.IngredientFactory;
import hr.dice.luka_jurkic.persistence.entity.IngredientCategory;
import hr.dice.luka_jurkic.persistence.repository.IngredientRepository;
import hr.dice.luka_jurkic.rest.dto.IngredientDTO;
import hr.dice.luka_jurkic.rest.request.IngredientRequest;
import io.restassured.common.mapper.TypeRef;
import jakarta.inject.Inject;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static hr.dice.luka_jurkic.constants.IngredientTestConstants.INGREDIENTS_COUNT;
import static hr.dice.luka_jurkic.constants.IngredientTestConstants.INGREDIENT_BAD_REQUEST;
import static hr.dice.luka_jurkic.constants.IngredientTestConstants.INGREDIENT_CATEGORY;
import static hr.dice.luka_jurkic.constants.IngredientTestConstants.INGREDIENT_ID;
import static hr.dice.luka_jurkic.constants.IngredientTestConstants.INGREDIENT_NAME;
import static hr.dice.luka_jurkic.constants.IngredientTestConstants.INGREDIENT_NOT_FOUND;
import static hr.dice.luka_jurkic.constants.IngredientTestConstants.NON_EXISTING_INGREDIENT_CATEGORY;
import static hr.dice.luka_jurkic.constants.IngredientTestConstants.NON_EXISTING_INGREDIENT_ID;
import static hr.dice.luka_jurkic.constants.IngredientTestConstants.NON_EXISTING_INGREDIENT_NAME;
import static hr.dice.luka_jurkic.constants.IngredientTestConstants.UPDATED_INGREDIENT_CATEGORY;
import static hr.dice.luka_jurkic.constants.IngredientTestConstants.UPDATED_INGREDIENT_NAME;
import static hr.dice.luka_jurkic.constants.UserTestConstants.ADMIN_PASSWORD;
import static hr.dice.luka_jurkic.constants.UserTestConstants.ADMIN_USERNAME;
import static hr.dice.luka_jurkic.constants.UserTestConstants.USER_ACCESS_DENIED;
import static hr.dice.luka_jurkic.constants.UserTestConstants.USER_PASSWORD;
import static hr.dice.luka_jurkic.constants.UserTestConstants.USER_USERNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class IngredientApiIT extends AbstractIT {

    @Inject IngredientRepository ingredientRepository;

    @Test
    @DisplayName("GET Ingredient - OK")
    @FlywayTest(locationsForMigrate = {"migrations/ingredient"})
    public void givenValidId_whenGetIngredient_thenExpect200() {
        IngredientDTO ingredient = IngredientTestClient.getIngredient(INGREDIENT_ID)
                .then()
                .statusCode(200)
                .and().extract()
                .body().as(IngredientDTO.class);
        assertThat(ingredient.getName()).isEqualTo(INGREDIENT_NAME);
        assertThat(ingredient.getCategory()).isEqualTo(String.valueOf(INGREDIENT_CATEGORY));
    }

    @Test
    @DisplayName("GET Ingredient - Not found")
    @FlywayTest(locationsForMigrate = {"migrations/ingredient"})
    public void givenInvalidId_whenGetIngredient_thenExpect404() {
        IngredientTestClient.getIngredient(NON_EXISTING_INGREDIENT_ID)
                .then()
                .statusCode(404)
                .body("errorKey", equalTo(INGREDIENT_NOT_FOUND));
    }

    @Test
    @DisplayName("GET Ingredients by Name - OK")
    @FlywayTest(locationsForMigrate = {"migrations/ingredient"})
    public void givenValidName_whenGetIngredientsByName_thenExpect200() {
        IngredientDTO ingredient = IngredientTestClient.getIngredientsByName(INGREDIENT_NAME)
                .then()
                .statusCode(200)
                .and().extract()
                .body().as(IngredientDTO.class);
        assertThat(ingredient.getName()).isEqualTo(INGREDIENT_NAME);
        assertThat(ingredient.getCategory()).isEqualTo(String.valueOf(INGREDIENT_CATEGORY));
        assertThat(ingredient.getId()).isEqualTo(INGREDIENT_ID);
    }

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.IngredientParameters#okForGetIngredientsByCategory")
    @DisplayName("GET Ingredients by Category - OK")
    @FlywayTest(locationsForMigrate = {"migrations/ingredient"})
    public void givenValidCategory_whenGetIngredientsByCategory_thenExpect200(IngredientCategory category, Integer count) {
        List<IngredientDTO> ingredients = IngredientTestClient.getIngredientsByCategory(String.valueOf(category))
                .then()
                .statusCode(200)
                .and().extract()
                .body().as(new TypeRef<>() {
                });
        assertThat(ingredients).isNotNull();
        assertThat(ingredients.size()).isEqualTo(count);
    }

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.IngredientParameters#notFoundForGetIngredientsByCategory")
    @DisplayName("GET Ingredients by Category - Bad Request")
    @FlywayTest(locationsForMigrate = {"migrations/ingredient"})
    public void givenInvalidCategory_whenGetIngredientsByCategory_thenExpect400(String category) {
        IngredientTestClient.getIngredientsByCategory(category)
                .then()
                .statusCode(400)
                .body("errorKey", equalTo(INGREDIENT_BAD_REQUEST));
    }

    @Test
    @DisplayName("GET Ingredients - OK")
    @FlywayTest(locationsForMigrate = {"migrations/ingredient"})
    public void givenNothing_whenGetIngredients_thenExpect200() {
        List<IngredientDTO> ingredients = IngredientTestClient.getIngredients()
                .then()
                .statusCode(200)
                .and().extract()
                .body().as(new TypeRef<>() {
                });
        assertThat(ingredients).isNotNull();
        assertThat(ingredients.size()).isEqualTo(INGREDIENTS_COUNT);
    }

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.IngredientParameters#okForCreateIngredient")
    @DisplayName("POST Ingredient - OK")
    @FlywayTest(locationsForMigrate = {"migrations/ingredient"})
    public void givenValidRequest_whenCreateIngredient_thenExpect200(String username, String password) {
        IngredientDTO ingredient = IngredientTestClient.createIngredient(username, password, IngredientFactory.validRequest())
                .then()
                .statusCode(200)
                .and().extract()
                .body().as(IngredientDTO.class);
        assertThat(ingredient.getName()).isEqualTo(NON_EXISTING_INGREDIENT_NAME);
        assertThat(ingredient.getCategory()).isEqualTo(String.valueOf(NON_EXISTING_INGREDIENT_CATEGORY));
    }

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.IngredientParameters#badRequestForCreateIngredients")
    @DisplayName("POST Ingredient - Bad Request")
    @FlywayTest(locationsForMigrate = {"migrations/ingredient"})
    public void givenInvalidRequest_whenCreateIngredient_thenExpect400(String username, String password, IngredientRequest request, String errorKey) {
        IngredientTestClient.createIngredient(username, password, request)
                .then()
                .statusCode(400)
                .body("errorKey", equalTo(errorKey));
    }

    @Test
    @DisplayName("DELETE Ingredient - OK")
    @FlywayTest(locationsForMigrate = {"migrations/ingredient"})
    public void givenValidId_whenDeleteIngredient_thenExpect200() {
        IngredientTestClient.deleteIngredient(ADMIN_USERNAME, ADMIN_PASSWORD, INGREDIENT_ID)
                .then()
                .statusCode(200);
        assertThat(ingredientRepository.findById(INGREDIENT_ID)).isNotPresent();
    }

    @Test
    @DisplayName("DELETE Ingredient - Not Found")
    @FlywayTest(locationsForMigrate = {"migrations/ingredient"})
    public void givenInvalidId_whenDeleteIngredient_thenExpect404() {
        IngredientTestClient.deleteIngredient(ADMIN_USERNAME, ADMIN_PASSWORD, NON_EXISTING_INGREDIENT_ID)
                .then()
                .statusCode(404)
                .body("errorKey", equalTo(INGREDIENT_NOT_FOUND));
    }

    @Test
    @DisplayName("DELETE Ingredient - Access Denied")
    @FlywayTest(locationsForMigrate = {"migrations/ingredient"})
    public void givenValidId_whenDeleteIngredient_thenExpect401() {
        IngredientTestClient.deleteIngredient(USER_USERNAME, USER_PASSWORD, INGREDIENT_ID)
                .then()
                .statusCode(401)
                .body("errorKey", equalTo(USER_ACCESS_DENIED));
    }

    @Test
    @DisplayName("PUT Ingredient - OK")
    @FlywayTest(locationsForMigrate = {"migrations/ingredient"})
    public void givenValidIdAndValidRequest_whenUpdateIngredient_thenExpect200() {
        IngredientDTO ingredient = IngredientTestClient.updateIngredient(ADMIN_USERNAME, ADMIN_PASSWORD, INGREDIENT_ID,
                        IngredientFactory.paramRequest(UPDATED_INGREDIENT_NAME, INGREDIENT_CATEGORY))
                .then()
                .statusCode(200)
                .and().extract()
                .body().as(IngredientDTO.class);
        assertThat(ingredient.getName()).isEqualTo(UPDATED_INGREDIENT_NAME);
        assertThat(ingredient.getCategory()).isEqualTo(String.valueOf(INGREDIENT_CATEGORY));
    }

    @Test
    @DisplayName("PUT Ingredient - Access Denied")
    @FlywayTest(locationsForMigrate = {"migrations/ingredient"})
    public void givenValidIdAndValidRequest_whenUpdateIngredient_thenExpect401() {
        IngredientTestClient.updateIngredient(USER_USERNAME, USER_PASSWORD, INGREDIENT_ID,
                        IngredientFactory.paramRequest(UPDATED_INGREDIENT_NAME, INGREDIENT_CATEGORY))
                .then()
                .statusCode(401)
                .body("errorKey", equalTo(USER_ACCESS_DENIED));
    }

    @Test
    @DisplayName("PUT Ingredient - Not Found")
    @FlywayTest(locationsForMigrate = {"migrations/ingredient"})
    public void givenInvalidIdAndValidRequest_whenUpdateLeague_thenExpect404() {
        IngredientTestClient.updateIngredient(ADMIN_USERNAME, ADMIN_PASSWORD, NON_EXISTING_INGREDIENT_ID, IngredientFactory.paramRequest(UPDATED_INGREDIENT_NAME, UPDATED_INGREDIENT_CATEGORY))
                .then()
                .statusCode(404)
                .body("errorKey", equalTo(INGREDIENT_NOT_FOUND));
    }

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.IngredientParameters#badRequestForUpdateIngredient")
    @DisplayName("PUT Ingredient - Bad Request")
    @FlywayTest(locationsForMigrate = {"migrations/ingredient"})
    public void givenValidIdAndInvalidRequest_whenUpdateIngredient_thenExpect400(IngredientRequest request, String errorKey) {
        IngredientTestClient.updateIngredient(ADMIN_USERNAME, ADMIN_PASSWORD, INGREDIENT_ID, request)
                .then()
                .statusCode(400)
                .body("errorKey", equalTo(errorKey));
    }

}
