package hr.dice.luka_jurkic.clients;

import hr.dice.luka_jurkic.rest.request.RecipeRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static hr.dice.luka_jurkic.constants.BaseConstants.SERVER_CONTEXT_PATH;

public class RecipeTestClient {

    public static final String RECIPE_BASE_URL = SERVER_CONTEXT_PATH + "/recipes";
    public static final String RECIPE_PARAM_URL = RECIPE_BASE_URL + "/id/{recipeId}";
    public static final String RECIPE_NAME_PARAM_URL = RECIPE_BASE_URL + "/{recipeName}";
    public static final String RECIPE_USER_PARAM_URL = RECIPE_BASE_URL + "/user/{username}";

    public static Response getRecipe(Long userId) {
        return RestAssured
                .given()
                .get(RECIPE_PARAM_URL, userId);
    }

    public static Response getRecipeByName(String recipeName) {
        return RestAssured
                .given()
                .get(RECIPE_NAME_PARAM_URL, recipeName);
    }

    public static Response getRecipes() {
        return RestAssured
                .given()
                .get(RECIPE_BASE_URL);
    }

    public static Response getRecipesByUser(String username) {
        return RestAssured
                .given()
                .get(RECIPE_USER_PARAM_URL, username);
    }

    public static Response createRecipe(String username, String password, RecipeRequest recipeRequest) {
        return RestAssured
                .given()
                .auth().basic(username, password)
                .body(recipeRequest)
                .contentType("application/json")
                .post(RECIPE_BASE_URL);
    }

    public static Response deleteRecipe(String username, String password, Long recipeId) {
        return RestAssured
                .given()
                .auth().basic(username, password)
                .delete(RECIPE_PARAM_URL, recipeId);
    }

    public static Response deleteRecipeByName(String username, String password, String recipeName) {
        return RestAssured
                .given()
                .auth().basic(username, password)
                .delete(RECIPE_NAME_PARAM_URL, recipeName);
    }

    public static Response updateRecipe(String username, String password, Long recipeId, RecipeRequest recipeRequest) {
        return RestAssured
                .given()
                .auth().basic(username, password)
                .body(recipeRequest)
                .contentType("application/json")
                .put(RECIPE_PARAM_URL, recipeId);
    }

    public static Response updateRecipeByName(String username, String password, String recipeName, RecipeRequest recipeRequest) {
        return RestAssured
                .given()
                .auth().basic(username, password)
                .body(recipeRequest)
                .contentType("application/json")
                .put(RECIPE_NAME_PARAM_URL, recipeName);
    }
}
