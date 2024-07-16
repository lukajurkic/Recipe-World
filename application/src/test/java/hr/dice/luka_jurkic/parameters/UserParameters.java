package hr.dice.luka_jurkic.parameters;

import hr.dice.luka_jurkic.factory.UserFactory;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static hr.dice.luka_jurkic.constants.UserTestConstants.ADMIN_DATE_OF_BIRTH;
import static hr.dice.luka_jurkic.constants.UserTestConstants.ADMIN_FIRST_NAME;
import static hr.dice.luka_jurkic.constants.UserTestConstants.ADMIN_ID;
import static hr.dice.luka_jurkic.constants.UserTestConstants.ADMIN_LAST_NAME;
import static hr.dice.luka_jurkic.constants.UserTestConstants.ADMIN_PASSWORD;
import static hr.dice.luka_jurkic.constants.UserTestConstants.ADMIN_ROLE;
import static hr.dice.luka_jurkic.constants.UserTestConstants.ADMIN_USERNAME;
import static hr.dice.luka_jurkic.constants.UserTestConstants.NON_EXISTING_ADMIN_DATE_OF_BIRTH;
import static hr.dice.luka_jurkic.constants.UserTestConstants.NON_EXISTING_ADMIN_FIRST_NAME;
import static hr.dice.luka_jurkic.constants.UserTestConstants.NON_EXISTING_ADMIN_LAST_NAME;
import static hr.dice.luka_jurkic.constants.UserTestConstants.NON_EXISTING_ADMIN_PASSWORD;
import static hr.dice.luka_jurkic.constants.UserTestConstants.NON_EXISTING_ADMIN_USERNAME;
import static hr.dice.luka_jurkic.constants.UserTestConstants.NON_EXISTING_USER_DATE_OF_BIRTH;
import static hr.dice.luka_jurkic.constants.UserTestConstants.NON_EXISTING_USER_FIRST_NAME;
import static hr.dice.luka_jurkic.constants.UserTestConstants.NON_EXISTING_USER_LAST_NAME;
import static hr.dice.luka_jurkic.constants.UserTestConstants.NON_EXISTING_USER_PASSWORD;
import static hr.dice.luka_jurkic.constants.UserTestConstants.NON_EXISTING_USER_USERNAME;
import static hr.dice.luka_jurkic.constants.UserTestConstants.UPDATED_ADMIN_DATE_OF_BIRTH;
import static hr.dice.luka_jurkic.constants.UserTestConstants.UPDATED_ADMIN_FIRST_NAME;
import static hr.dice.luka_jurkic.constants.UserTestConstants.UPDATED_ADMIN_LAST_NAME;
import static hr.dice.luka_jurkic.constants.UserTestConstants.UPDATED_ADMIN_PASSWORD;
import static hr.dice.luka_jurkic.constants.UserTestConstants.UPDATED_ADMIN_ROLE;
import static hr.dice.luka_jurkic.constants.UserTestConstants.UPDATED_ADMIN_USERNAME;
import static hr.dice.luka_jurkic.constants.UserTestConstants.UPDATED_USER_DATE_OF_BIRTH;
import static hr.dice.luka_jurkic.constants.UserTestConstants.UPDATED_USER_FIRST_NAME;
import static hr.dice.luka_jurkic.constants.UserTestConstants.UPDATED_USER_LAST_NAME;
import static hr.dice.luka_jurkic.constants.UserTestConstants.UPDATED_USER_PASSWORD;
import static hr.dice.luka_jurkic.constants.UserTestConstants.UPDATED_USER_ROLE;
import static hr.dice.luka_jurkic.constants.UserTestConstants.UPDATED_USER_USERNAME;
import static hr.dice.luka_jurkic.constants.UserTestConstants.USER_ALREADY_EXISTS;
import static hr.dice.luka_jurkic.constants.UserTestConstants.USER_BAD_REQUEST;
import static hr.dice.luka_jurkic.constants.UserTestConstants.USER_DATE_OF_BIRTH;
import static hr.dice.luka_jurkic.constants.UserTestConstants.USER_FIRST_NAME;
import static hr.dice.luka_jurkic.constants.UserTestConstants.USER_ID;
import static hr.dice.luka_jurkic.constants.UserTestConstants.USER_LAST_NAME;
import static hr.dice.luka_jurkic.constants.UserTestConstants.USER_PASSWORD;
import static hr.dice.luka_jurkic.constants.UserTestConstants.USER_ROLE;
import static hr.dice.luka_jurkic.constants.UserTestConstants.USER_USERNAME;

public class UserParameters {

    public static Stream<Arguments> okForGetUser() {
        return Stream.of(
                Arguments.of(ADMIN_USERNAME, ADMIN_ROLE, ADMIN_ID),
                Arguments.of(USER_USERNAME, USER_ROLE, USER_ID)
        );
    }

    public static Stream<Arguments> okForGetUserDetails() {
        return Stream.of(
                Arguments.of(ADMIN_FIRST_NAME, ADMIN_LAST_NAME, ADMIN_USERNAME, ADMIN_ID, ADMIN_DATE_OF_BIRTH, ADMIN_ROLE, ADMIN_PASSWORD),
                Arguments.of(USER_FIRST_NAME, USER_LAST_NAME, USER_USERNAME, USER_ID, USER_DATE_OF_BIRTH, USER_ROLE, USER_PASSWORD)
        );
    }

    public static Stream<Arguments> notFoundForGetUserAndGetUserDetails() {
        return Stream.of(
                Arguments.of(NON_EXISTING_ADMIN_USERNAME),
                Arguments.of(NON_EXISTING_USER_FIRST_NAME)
        );
    }

    public static Stream<Arguments> okForDeleteUser() {
        return Stream.of(
                Arguments.of(ADMIN_USERNAME, ADMIN_PASSWORD, ADMIN_ID),
                Arguments.of(USER_USERNAME, USER_PASSWORD, USER_ID)
        );
    }


    public static Stream<Arguments> badRequestForPostUser() {
        return Stream.of(
                Arguments.of(
                        UserFactory.paramRequest(null, NON_EXISTING_ADMIN_LAST_NAME, NON_EXISTING_ADMIN_USERNAME, NON_EXISTING_ADMIN_PASSWORD, NON_EXISTING_ADMIN_DATE_OF_BIRTH), USER_BAD_REQUEST),
                Arguments.of(
                        UserFactory.paramRequest(NON_EXISTING_ADMIN_FIRST_NAME, null, NON_EXISTING_ADMIN_USERNAME, NON_EXISTING_ADMIN_PASSWORD, NON_EXISTING_ADMIN_DATE_OF_BIRTH), USER_BAD_REQUEST),
                Arguments.of(
                        UserFactory.paramRequest(NON_EXISTING_ADMIN_FIRST_NAME, NON_EXISTING_ADMIN_LAST_NAME, null, NON_EXISTING_ADMIN_PASSWORD, NON_EXISTING_ADMIN_DATE_OF_BIRTH), USER_BAD_REQUEST),
                Arguments.of(
                        UserFactory.paramRequest(NON_EXISTING_ADMIN_FIRST_NAME, NON_EXISTING_ADMIN_LAST_NAME, NON_EXISTING_ADMIN_USERNAME, null, NON_EXISTING_ADMIN_DATE_OF_BIRTH), USER_BAD_REQUEST),
                Arguments.of(UserFactory.paramRequest(NON_EXISTING_ADMIN_FIRST_NAME, NON_EXISTING_ADMIN_LAST_NAME, NON_EXISTING_ADMIN_USERNAME, NON_EXISTING_ADMIN_PASSWORD, null), USER_BAD_REQUEST),
                Arguments.of(UserFactory.paramRequest(NON_EXISTING_ADMIN_FIRST_NAME, NON_EXISTING_ADMIN_LAST_NAME, NON_EXISTING_ADMIN_USERNAME, NON_EXISTING_ADMIN_PASSWORD,
                        NON_EXISTING_ADMIN_DATE_OF_BIRTH), USER_BAD_REQUEST),
                Arguments.of(UserFactory.paramRequest(NON_EXISTING_ADMIN_FIRST_NAME, NON_EXISTING_ADMIN_LAST_NAME, ADMIN_USERNAME, NON_EXISTING_ADMIN_PASSWORD,
                        NON_EXISTING_ADMIN_DATE_OF_BIRTH), USER_ALREADY_EXISTS)
        );
    }

    public static Stream<Arguments> okForPutUser() {
        return Stream.of(
                Arguments.of(ADMIN_USERNAME, ADMIN_PASSWORD, UPDATED_ADMIN_FIRST_NAME, UPDATED_ADMIN_LAST_NAME, UPDATED_ADMIN_USERNAME, UPDATED_ADMIN_PASSWORD,
                        UPDATED_ADMIN_DATE_OF_BIRTH, UPDATED_ADMIN_ROLE, ADMIN_ID),
                Arguments.of(USER_USERNAME, USER_PASSWORD, UPDATED_USER_FIRST_NAME, UPDATED_USER_LAST_NAME, UPDATED_USER_USERNAME, UPDATED_USER_PASSWORD,
                        UPDATED_USER_DATE_OF_BIRTH, UPDATED_USER_ROLE, USER_ID)
        );
    }

    public static Stream<Arguments> badRequestForPutAdminUser() {
        return Stream.of(
                Arguments.of(
                        UserFactory.paramRequest(null, NON_EXISTING_ADMIN_LAST_NAME, NON_EXISTING_ADMIN_USERNAME, NON_EXISTING_ADMIN_PASSWORD, NON_EXISTING_ADMIN_DATE_OF_BIRTH)),
                Arguments.of(
                        UserFactory.paramRequest(NON_EXISTING_ADMIN_FIRST_NAME, null, NON_EXISTING_ADMIN_USERNAME, NON_EXISTING_ADMIN_PASSWORD, NON_EXISTING_ADMIN_DATE_OF_BIRTH)),
                Arguments.of(
                        UserFactory.paramRequest(NON_EXISTING_ADMIN_FIRST_NAME, NON_EXISTING_ADMIN_LAST_NAME, null, NON_EXISTING_ADMIN_PASSWORD, NON_EXISTING_ADMIN_DATE_OF_BIRTH)),
                Arguments.of(
                        UserFactory.paramRequest(NON_EXISTING_ADMIN_FIRST_NAME, NON_EXISTING_ADMIN_LAST_NAME, NON_EXISTING_ADMIN_USERNAME, null, NON_EXISTING_ADMIN_DATE_OF_BIRTH)),
                Arguments.of(UserFactory.paramRequest(NON_EXISTING_ADMIN_FIRST_NAME, NON_EXISTING_ADMIN_LAST_NAME, NON_EXISTING_ADMIN_USERNAME, NON_EXISTING_ADMIN_PASSWORD, null)),
                Arguments.of(UserFactory.paramRequest(NON_EXISTING_ADMIN_FIRST_NAME, NON_EXISTING_ADMIN_LAST_NAME, NON_EXISTING_ADMIN_USERNAME, NON_EXISTING_ADMIN_PASSWORD,
                        NON_EXISTING_ADMIN_DATE_OF_BIRTH))
        );
    }

    public static Stream<Arguments> badRequestForPutRegularUser() {
        return Stream.of(
                Arguments.of(UserFactory.paramRequest(null, NON_EXISTING_USER_LAST_NAME, NON_EXISTING_USER_USERNAME, NON_EXISTING_USER_PASSWORD, NON_EXISTING_USER_DATE_OF_BIRTH)),
                Arguments.of(UserFactory.paramRequest(NON_EXISTING_USER_FIRST_NAME, null, NON_EXISTING_USER_USERNAME, NON_EXISTING_USER_PASSWORD, NON_EXISTING_USER_DATE_OF_BIRTH)),
                Arguments.of(UserFactory.paramRequest(NON_EXISTING_USER_FIRST_NAME, NON_EXISTING_USER_LAST_NAME, null, NON_EXISTING_USER_PASSWORD, NON_EXISTING_USER_DATE_OF_BIRTH)),
                Arguments.of(UserFactory.paramRequest(NON_EXISTING_USER_FIRST_NAME, NON_EXISTING_USER_LAST_NAME, NON_EXISTING_USER_USERNAME, null, NON_EXISTING_USER_DATE_OF_BIRTH)),
                Arguments.of(UserFactory.paramRequest(NON_EXISTING_USER_FIRST_NAME, NON_EXISTING_USER_LAST_NAME, NON_EXISTING_USER_USERNAME, NON_EXISTING_USER_PASSWORD, null)),
                Arguments.of(UserFactory.paramRequest(NON_EXISTING_USER_FIRST_NAME, NON_EXISTING_USER_LAST_NAME, NON_EXISTING_USER_USERNAME, NON_EXISTING_USER_PASSWORD,
                        NON_EXISTING_USER_DATE_OF_BIRTH))
        );
    }

    public static Stream<Arguments> alreadyExistsForUpdateUser() {
        return Stream.of(
                Arguments.of(ADMIN_USERNAME, ADMIN_PASSWORD, UserFactory.paramRequest(NON_EXISTING_ADMIN_FIRST_NAME, NON_EXISTING_ADMIN_LAST_NAME,
                        USER_USERNAME, NON_EXISTING_ADMIN_PASSWORD, NON_EXISTING_ADMIN_DATE_OF_BIRTH)),
                Arguments.of(USER_USERNAME, USER_PASSWORD, UserFactory.paramRequest(NON_EXISTING_USER_FIRST_NAME, NON_EXISTING_USER_LAST_NAME,
                        ADMIN_USERNAME, NON_EXISTING_USER_PASSWORD, NON_EXISTING_USER_DATE_OF_BIRTH))
        );
    }
}
