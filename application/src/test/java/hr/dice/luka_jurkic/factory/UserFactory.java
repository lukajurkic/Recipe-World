package hr.dice.luka_jurkic.factory;

import hr.dice.luka_jurkic.rest.request.UserRequest;

import java.sql.Date;
import java.time.LocalDate;

import static hr.dice.luka_jurkic.constants.UserTestConstants.NON_EXISTING_ADMIN_DATE_OF_BIRTH;
import static hr.dice.luka_jurkic.constants.UserTestConstants.NON_EXISTING_ADMIN_FIRST_NAME;
import static hr.dice.luka_jurkic.constants.UserTestConstants.NON_EXISTING_ADMIN_LAST_NAME;
import static hr.dice.luka_jurkic.constants.UserTestConstants.NON_EXISTING_ADMIN_PASSWORD;
import static hr.dice.luka_jurkic.constants.UserTestConstants.NON_EXISTING_ADMIN_USERNAME;

public class UserFactory {
    
    public static UserRequest validRequest() {
        UserRequest request = new UserRequest();
        request.setFirstName(NON_EXISTING_ADMIN_FIRST_NAME);
        request.setLastName(NON_EXISTING_ADMIN_LAST_NAME);
        request.setUsername(NON_EXISTING_ADMIN_USERNAME);
        request.setDateOfBirth(Date.valueOf(NON_EXISTING_ADMIN_DATE_OF_BIRTH));
        request.setPassword(NON_EXISTING_ADMIN_PASSWORD);
        return request;
    }

    public static UserRequest paramRequest(String firstName, String lastName, String username, String password, LocalDate dateOfBirth) {
        UserRequest request = new UserRequest();
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setUsername(username);
        request.setDateOfBirth(Date.valueOf(dateOfBirth));
        request.setPassword(password);
        return request;
    }
}
