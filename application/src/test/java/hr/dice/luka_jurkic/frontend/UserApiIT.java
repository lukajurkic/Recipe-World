package hr.dice.luka_jurkic.frontend;

import hr.dice.luka_jurkic.AbstractIT;
import hr.dice.luka_jurkic.clients.UserTestClient;
import hr.dice.luka_jurkic.factory.UserFactory;
import hr.dice.luka_jurkic.persistence.repository.UserRepository;
import hr.dice.luka_jurkic.rest.dto.UserDTO;
import hr.dice.luka_jurkic.rest.dto.UserDetailsDTO;
import hr.dice.luka_jurkic.rest.request.UserRequest;
import io.restassured.common.mapper.TypeRef;
import jakarta.inject.Inject;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.List;

import static hr.dice.luka_jurkic.constants.UserTestConstants.ADMIN_PASSWORD;
import static hr.dice.luka_jurkic.constants.UserTestConstants.ADMIN_USERNAME;
import static hr.dice.luka_jurkic.constants.UserTestConstants.NON_EXISTING_ADMIN_DATE_OF_BIRTH;
import static hr.dice.luka_jurkic.constants.UserTestConstants.NON_EXISTING_ADMIN_FIRST_NAME;
import static hr.dice.luka_jurkic.constants.UserTestConstants.NON_EXISTING_ADMIN_LAST_NAME;
import static hr.dice.luka_jurkic.constants.UserTestConstants.NON_EXISTING_ADMIN_ROLE;
import static hr.dice.luka_jurkic.constants.UserTestConstants.NON_EXISTING_ADMIN_USERNAME;
import static hr.dice.luka_jurkic.constants.UserTestConstants.USERS_COUNT;
import static hr.dice.luka_jurkic.constants.UserTestConstants.USER_ACCESS_DENIED;
import static hr.dice.luka_jurkic.constants.UserTestConstants.USER_ALREADY_EXISTS;
import static hr.dice.luka_jurkic.constants.UserTestConstants.USER_BAD_REQUEST;
import static hr.dice.luka_jurkic.constants.UserTestConstants.USER_NOT_FOUND;
import static hr.dice.luka_jurkic.constants.UserTestConstants.USER_PASSWORD;
import static hr.dice.luka_jurkic.constants.UserTestConstants.USER_USERNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class UserApiIT extends AbstractIT {

    @Inject UserRepository userRepository;

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.UserParameters#okForGetUser")
    @DisplayName("GET User - OK")
    @FlywayTest(locationsForMigrate = {"migrations/user"})
    public void givenValidUsername_whenGetUser_thenExpect200(String username, String role, Long id) {
        UserDTO user = UserTestClient.getUser(username)
                .then()
                .statusCode(200)
                .and().extract()
                .body().as(UserDTO.class);
        assertThat(user.getUsername()).isEqualTo(username);
        assertThat(user.getRole()).isEqualTo(String.valueOf(role));
        assertThat(user.getId()).isEqualTo(id);
    }

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.UserParameters#notFoundForGetUserAndGetUserDetails")
    @DisplayName("GET User - Not Found")
    @FlywayTest(locationsForMigrate = {"migrations/user"})
    public void givenNonExistingUsername_whenGetUser_thenExpect404(String username) {
        UserTestClient.getUser(username)
                .then()
                .statusCode(404)
                .body("errorKey", equalTo(USER_NOT_FOUND));
    }

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.UserParameters#okForGetUserDetails")
    @DisplayName("GET User Details - OK")
    @FlywayTest(locationsForMigrate = {"migrations/user"})
    public void givenValidCredentials_whenGetUserDetails_thenExpect200(String firstName, String lastName, String username, Long id, LocalDate dob, String role, String password) {
        UserDetailsDTO user = UserTestClient.getUserDetails(username, password)
                .then()
                .statusCode(200)
                .and().extract()
                .body().as(UserDetailsDTO.class);
        assertThat(user.getUsername()).isEqualTo(username);
        assertThat(user.getRole()).isEqualTo(role);
        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getDateOfBirth()).isEqualTo(dob);
        assertThat(user.getFirstName()).isEqualTo(firstName);
        assertThat(user.getLastName()).isEqualTo(lastName);
    }

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.UserParameters#notFoundForGetUserAndGetUserDetails")
    @DisplayName("GET User Details - Not Found")
    @FlywayTest(locationsForMigrate = {"migrations/user"})
    public void givenNonExistingUsername_whenGetUserDetails_thenExpect404(String username) {
        UserTestClient.getUser(username)
                .then()
                .statusCode(404)
                .body("errorKey", equalTo(USER_NOT_FOUND));
    }

    @Test
    @DisplayName("GET Users - OK")
    @FlywayTest(locationsForMigrate = {"migrations/user"})
    public void givenValidCredentials_whenGetUsers_thenExpect200() {
        List<UserDTO> users = UserTestClient.getUsers(ADMIN_USERNAME, ADMIN_PASSWORD)
                .then()
                .statusCode(200)
                .and().extract()
                .body().as(new TypeRef<>() {
                });
        assertThat(users).isNotNull();
        assertThat(users.size()).isEqualTo(USERS_COUNT);
    }

    @Test
    @DisplayName("GET Users - Access Denied")
    @FlywayTest(locationsForMigrate = {"migrations/user"})
    public void givenValidCredentials_whenGetUsers_thenExpect401() {
        UserTestClient.getUsers(USER_USERNAME, USER_PASSWORD)
                .then()
                .statusCode(401)
                .body("errorKey", equalTo(USER_ACCESS_DENIED));
    }


    @Test
    @DisplayName("GET Users Details - OK")
    @FlywayTest(locationsForMigrate = {"migrations/user"})
    public void givenValidCredentials_whenGetUsersDetails_thenExpect200() {
        List<UserDetailsDTO> users = UserTestClient.getUsersDetails(ADMIN_USERNAME, ADMIN_PASSWORD)
                .then()
                .statusCode(200)
                .and().extract()
                .body().as(new TypeRef<>() {
                });
        assertThat(users).isNotNull();
        assertThat(users.size()).isEqualTo(USERS_COUNT);
    }

    @Test
    @DisplayName("GET Users Details - Access Denied")
    @FlywayTest(locationsForMigrate = {"migrations/user"})
    public void givenValidCredentials_whenGetUsersDetails_thenExpect401() {
        UserTestClient.getUsersDetails(USER_USERNAME, USER_PASSWORD)
                .then()
                .statusCode(401)
                .body("errorKey", equalTo(USER_ACCESS_DENIED));
    }

    @Test
    @DisplayName("POST User - OK")
    @FlywayTest(locationsForMigrate = {"migrations/user"})
    public void givenValidUserRequest_whenCreateUser_thenExpect200() {
        UserDetailsDTO user = UserTestClient.createUser(UserFactory.validRequest())
                .then()
                .statusCode(200)
                .and().extract()
                .body().as(UserDetailsDTO.class);
        assertThat(user.getUsername()).isEqualTo(NON_EXISTING_ADMIN_USERNAME);
        assertThat(user.getRole()).isEqualTo(String.valueOf(NON_EXISTING_ADMIN_ROLE));
        assertThat(user.getDateOfBirth()).isEqualTo(NON_EXISTING_ADMIN_DATE_OF_BIRTH);
        assertThat(user.getFirstName()).isEqualTo(NON_EXISTING_ADMIN_FIRST_NAME);
        assertThat(user.getLastName()).isEqualTo(NON_EXISTING_ADMIN_LAST_NAME);
    }

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.UserParameters#badRequestForPostUser")
    @DisplayName("POST User - Bad Request")
    @FlywayTest(locationsForMigrate = {"migrations/user"})
    public void givenInvalidRequest_whenCreateUser_thenExpect400(UserRequest request, String errorKey) {
        UserTestClient.createUser(request)
                .then()
                .statusCode(400)
                .body("errorKey", equalTo(errorKey));
    }

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.UserParameters#okForDeleteUser")
    @DisplayName("DELETE User - OK")
    @FlywayTest(locationsForMigrate = {"migrations/user"})
    public void givenValidCredentials_whenDeleteUser_thenExpect200(String username, String password, Long id) {
        UserTestClient.deleteUser(username, password)
                .then()
                .statusCode(200);
        assertThat(userRepository.findById(id)).isNotPresent();
    }

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.UserParameters#okForPutUser")
    @DisplayName("PUT User - OK")
    @FlywayTest(locationsForMigrate = {"migrations/user"})
    public void givenValidCredentialsAndValidUserRequest_whenUpdateUser_thenExpect200(String username,
                                                                                      String password,
                                                                                      String updatedFirstName,
                                                                                      String updateLastName,
                                                                                      String updatedUsername,
                                                                                      String updatedPassword,
                                                                                      LocalDate updatedDob,
                                                                                      String updatedRole,
                                                                                      Long id) {
        UserDetailsDTO user = UserTestClient.updateUser(username, password,
                        UserFactory.paramRequest(updatedFirstName, updateLastName, updatedUsername,
                                updatedPassword, updatedDob))
                .then()
                .statusCode(200)
                .and().extract()
                .body().as(UserDetailsDTO.class);
        assertThat(user.getId()).isEqualTo(id);
        assertThat(user.getUsername()).isEqualTo(updatedUsername);
        assertThat(user.getRole()).isEqualTo(updatedRole);
        assertThat(user.getDateOfBirth()).isEqualTo(updatedDob);
        assertThat(user.getFirstName()).isEqualTo(updatedFirstName);
        assertThat(user.getLastName()).isEqualTo(updateLastName);
    }

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.UserParameters#badRequestForPutAdminUser")
    @DisplayName("Put Admin User - Bad Request")
    @FlywayTest(locationsForMigrate = {"migrations/user"})
    public void givenInvalidRequest_whenUpdateAdminUser_thenExpect400(UserRequest request) {
        UserTestClient.updateUser(ADMIN_USERNAME, ADMIN_PASSWORD, request)
                .then()
                .statusCode(400)
                .body("errorKey", equalTo(USER_BAD_REQUEST));
    }

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.UserParameters#badRequestForPutRegularUser")
    @DisplayName("Put Regular User - Bad Request")
    @FlywayTest(locationsForMigrate = {"migrations/user"})
    public void givenInvalidRequest_whenUpdateRegularUser_thenExpect400(UserRequest request) {
        UserTestClient.updateUser(USER_USERNAME, USER_PASSWORD, request)
                .then()
                .statusCode(400)
                .body("errorKey", equalTo(USER_BAD_REQUEST));
    }

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.UserParameters#alreadyExistsForUpdateUser")
    @DisplayName("PUT User - Already Exists")
    @FlywayTest(locationsForMigrate = {"migrations/user"})
    public void givenValidRequest_whenUpdateUser_thenExpect400(String username, String password, UserRequest request) {
        UserTestClient.updateUser(username, password, request)
                .then()
                .statusCode(400)
                .body("errorKey", equalTo(USER_ALREADY_EXISTS));
    }

}
