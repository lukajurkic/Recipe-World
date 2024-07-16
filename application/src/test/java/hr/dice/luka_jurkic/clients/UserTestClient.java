package hr.dice.luka_jurkic.clients;

import hr.dice.luka_jurkic.rest.request.UserRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static hr.dice.luka_jurkic.constants.BaseConstants.SERVER_CONTEXT_PATH;

public class UserTestClient {

    public static final String USER_BASE_URL = SERVER_CONTEXT_PATH + "/users";
    public static final String USER_PARAM_URL = USER_BASE_URL + "/{username}";
    public static final String USERS_DETAILS_BASE_URL = USER_BASE_URL + "/all/details";
    public static final String USER_DETAILS_BASE_URL = USER_BASE_URL + "/user/details";

    public static Response getUser(String username) {
        return RestAssured
                .given()
                .get(USER_PARAM_URL, username);
    }

    public static Response getUserDetails(String username, String password) {
        return RestAssured
                .given()
                .auth().basic(username, password)
                .get(USER_DETAILS_BASE_URL);
    }

    public static Response getUsers(String username, String password) {
        return RestAssured
                .given()
                .auth().basic(username, password)
                .get(USER_BASE_URL);
    }

    public static Response getUsersDetails(String username, String password) {
        return RestAssured
                .given()
                .auth().basic(username, password)
                .get(USERS_DETAILS_BASE_URL);
    }

    public static Response createUser(UserRequest userRequest) {
        return RestAssured
                .given()
                .body(userRequest)
                .contentType("application/json")
                .post(USER_BASE_URL);
    }

    public static Response deleteUser(String username, String password) {
        return RestAssured
                .given()
                .auth().basic(username, password)
                .delete(USER_BASE_URL);
    }

    public static Response updateUser(String username, String password, UserRequest userRequest) {
        return RestAssured
                .given()
                .auth().basic(username, password)
                .body(userRequest)
                .contentType("application/json")
                .put(USER_BASE_URL);
    }
}
