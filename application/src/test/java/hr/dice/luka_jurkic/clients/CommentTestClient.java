package hr.dice.luka_jurkic.clients;

import hr.dice.luka_jurkic.rest.request.CommentRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static hr.dice.luka_jurkic.constants.BaseConstants.SERVER_CONTEXT_PATH;

public class CommentTestClient {

    public static final String COMMENT_BASE_URL = SERVER_CONTEXT_PATH + "/comments";
    public static final String COMMENT_PARAM_URL = COMMENT_BASE_URL + "/{commentId}";
    public static final String COMMENT_RECIPE_URL = COMMENT_BASE_URL + "/recipe/{recipeName}";
    public static final String COMMENT_DETAILS_URL = COMMENT_BASE_URL + "/details";
    public static final String COMMENT_DETAILS_RECIPE_URL = COMMENT_BASE_URL + "/details/{recipeName}";

    public static Response getCommentsByUser(String username, String password) {
        return RestAssured
                .given()
                .auth().basic(username, password)
                .get(COMMENT_BASE_URL);
    }

    public static Response getCommentsByRecipe(String recipeName) {
        return RestAssured
                .given()
                .get(COMMENT_RECIPE_URL, recipeName);
    }

    public static Response getCommentDetailsByUser(String username, String password) {
        return RestAssured
                .given()
                .auth().basic(username, password)
                .get(COMMENT_DETAILS_URL);
    }

    public static Response getCommentDetailsByRecipe(String recipeName) {
        return RestAssured
                .given()
                .get(COMMENT_DETAILS_RECIPE_URL, recipeName);
    }

    public static Response createComment(String username, String password, CommentRequest commentRequest) {
        return RestAssured
                .given()
                .auth().basic(username, password)
                .body(commentRequest)
                .contentType("application/json")
                .post(COMMENT_BASE_URL);
    }

    public static Response deleteComment(String username, String password, Long commentId) {
        return RestAssured
                .given()
                .auth().basic(username, password)
                .delete(COMMENT_PARAM_URL, commentId);
    }
    public static Response updateComment(String username, String password, Long commentId, CommentRequest commentRequest) {
        return RestAssured
                .given()
                .auth().basic(username, password)
                .body(commentRequest)
                .contentType("application/json")
                .put(COMMENT_PARAM_URL, commentId);
    }
}
