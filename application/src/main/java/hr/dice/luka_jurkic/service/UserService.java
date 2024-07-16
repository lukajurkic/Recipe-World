package hr.dice.luka_jurkic.service;

import hr.dice.luka_jurkic.rest.dto.UserDTO;
import hr.dice.luka_jurkic.rest.dto.UserDetailsDTO;
import hr.dice.luka_jurkic.rest.request.UserRequest;

import java.util.List;

/**
 * Service interface for managing users
 */
public interface UserService {

    /**
     * Returns user with given username
     * @param username unique identifier for each user that is set by the user
     * @return User Data Transfer Object
     */
    UserDTO getUser(String username);

    /**
     * Returns user details with given username
     * @param username unique identifier for each username
     * @return User Details Data Transfer Object
     */
    UserDetailsDTO getUserDetails(String username);

    /**
     * Returns all users from datasource
     * @return List of User Data Transfer Objects
     */
    List<UserDTO> getUsers();

    /**
     * Returns all users details from datasource
     * @return List of User Details Data Transfer Objects
     */
    List<UserDetailsDTO> getUsersDetails();

    /**
     * Returns all possible roles for user
     * @return List of Strings
     */
    List<String> getUserRoles();

    /**
     * Returns newly created user
     * @param userRequest information for creating new user
     * @return User Details Data Transfer Object
     */
    UserDetailsDTO createUser(UserRequest userRequest);

    /**
     * Deletes user with given username
     * @param username unique identifier for each user that is set by the user
     */
    void deleteUser(String username);

    /**
     * Updates user with given username base on user request
     * @param username unique identifier for each user
     * @param userRequest information for updating user
     * @return User Details Data Transfer Object
     */
    UserDetailsDTO updateUser(String username, UserRequest userRequest);

    /**
     * Updates user rights from 'user' to 'admin' and then returns updated user
     * @param username unique identifier for each user - user whose rights will be upgraded
     * @return User Details Data Transfer Object
     */
    UserDetailsDTO upgradeUserRights(String username);

    /**
     * Downgrades user rights from 'admin' to 'user' and then returns downgraded user
     * @return User Details Data Transfer Object
     */
    UserDetailsDTO downgradeUserRights();

    /**
     * Returns true if username is unique in database
     * @param username name of user to be checked
     * @return true/false
     */
    boolean isUnique(String username);

    /**
     * Returns true if password meets requirements
     * @param password string to be checked
     * @return true/false
     */
    boolean isValidPassword(String password);
}
