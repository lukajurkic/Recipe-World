package hr.dice.luka_jurkic.clients;

import hr.dice.luka_jurkic.rest.request.StepRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class StepTestClient {

    private static final String STEP_BASE_URL = "/api/steps";
    private static final String STEP_FULL_PARAM_URL = STEP_BASE_URL +  "/{recipeName}/step/{stepNumber}";
    private static final String STEP_RECIPE_PARAM_URL = STEP_BASE_URL + "/{recipeName}";
    private static final String STEP_STEP_PARAM_URL = STEP_BASE_URL + "/step/{stepNumber}";

    public static Response getStep(String username, String password, String recipeName, Integer stepNumber) {
        return RestAssured
                .given()
                .auth().basic(username, password)
                .get(STEP_FULL_PARAM_URL, recipeName, stepNumber);
    }

    public static Response getSteps(String username, String password, String recipeName) {
        return RestAssured
                .given()
                .auth().basic(username, password)
                .get(STEP_RECIPE_PARAM_URL, recipeName);
    }

    public static Response createStep(String username, String password, StepRequest request) {
        return RestAssured
                .given()
                .auth().basic(username, password)
                .body(request)
                .contentType("application/json")
                .post(STEP_BASE_URL);
    }

    public static Response deleteStep(String username, String password, String recipeName, Integer stepNumber) {
        return RestAssured
                .given()
                .auth().basic(username, password)
                .delete(STEP_FULL_PARAM_URL, recipeName, stepNumber);
    }

    public static Response updateStep(String username, String password, Integer stepNumber, StepRequest request) {
        return RestAssured
                .given()
                .auth().basic(username, password)
                .body(request)
                .contentType("application/json")
                .put(STEP_STEP_PARAM_URL, stepNumber);
    }
    
}
