package hr.dice.luka_jurkic.clients;

import hr.dice.luka_jurkic.rest.request.IngredientRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static hr.dice.luka_jurkic.constants.BaseConstants.SERVER_CONTEXT_PATH;

public class IngredientTestClient {

    public static final String INGREDIENT_BASE_URL = SERVER_CONTEXT_PATH + "/ingredients";
    public static final String INGREDIENT_PARAM_URL = INGREDIENT_BASE_URL + "/{ingredientId}";
    public static final String INGREDIENT_CATEGORY_PARAM_URL = INGREDIENT_BASE_URL + "/category/{ingredientCategory}";
    public static final String INGREDIENT_NAME_PARAM_URL = INGREDIENT_BASE_URL + "/name/{ingredientName}";

    public static Response getIngredient(Long ingredientId) {
        return RestAssured
                .given()
                .get(INGREDIENT_PARAM_URL, ingredientId);
    }

    public static Response getIngredients() {
        return RestAssured
                .given()
                .get(INGREDIENT_BASE_URL);
    }

    public static Response getIngredientsByName(String ingredientName) {
        return RestAssured
                .given()
                .get(INGREDIENT_NAME_PARAM_URL, ingredientName);
    }

    public static Response getIngredientsByCategory(String ingredientCategory) {
        return RestAssured
                .given()
                .get(INGREDIENT_CATEGORY_PARAM_URL, ingredientCategory);
    }

    public static Response createIngredient(String username, String password, IngredientRequest ingredientRequest) {
        return RestAssured
                .given()
                .auth().basic(username, password)
                .body(ingredientRequest)
                .contentType("application/json")
                .post(INGREDIENT_BASE_URL);
    }

    public static Response deleteIngredient(String username, String password, Long ingredientId) {
        return RestAssured
                .given()
                .auth().basic(username, password)
                .delete(INGREDIENT_PARAM_URL, ingredientId);
    }

    public static Response updateIngredient(String username, String password, Long ingredientId, IngredientRequest ingredientRequest) {
        return RestAssured
                .given()
                .auth().basic(username, password)
                .body(ingredientRequest)
                .contentType("application/json")
                .put(INGREDIENT_PARAM_URL, ingredientId);
    }
}
