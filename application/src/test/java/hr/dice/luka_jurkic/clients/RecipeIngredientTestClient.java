package hr.dice.luka_jurkic.clients;

import hr.dice.luka_jurkic.rest.request.RecipeIngredientRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static hr.dice.luka_jurkic.constants.BaseConstants.SERVER_CONTEXT_PATH;

public class RecipeIngredientTestClient {
    
    public static final String RECIPE_BASE_URL = SERVER_CONTEXT_PATH + "/recipes/{recipeName}/ingredient";
    public static final String RECIPE_INGREDIENTS_URL = RECIPE_BASE_URL + "s";
    public static final String RECIPE_PARAM_URL = RECIPE_BASE_URL + "/{ingredientName}";

    public static Response getRecipeIngredients(String recipeName) {
        return RestAssured
                .given()
                .get(RECIPE_INGREDIENTS_URL, recipeName);
    }

    public static Response createRecipeIngredient(String username, String password, String recipeName, RecipeIngredientRequest request) {
        return RestAssured
                .given()
                .body(request)
                .auth().basic(username, password)
                .contentType("application/json")
                .post(RECIPE_BASE_URL, recipeName);
    }

    public static Response deleteRecipeIngredient(String username, String password, String recipeName, String ingredientName) {
        return RestAssured
                .given()
                .auth().basic(username, password)
                .delete(RECIPE_PARAM_URL, recipeName, ingredientName);
    }

    public static Response updateRecipeIngredient(String username, String password, String recipeName, String ingredientName, RecipeIngredientRequest request) {
        return RestAssured
                .given()
                .auth().basic(username, password)
                .body(request)
                .contentType("application/json")
                .put(RECIPE_PARAM_URL, recipeName, ingredientName);
    }
}
