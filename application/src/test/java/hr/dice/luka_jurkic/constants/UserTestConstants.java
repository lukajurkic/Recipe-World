package hr.dice.luka_jurkic.constants;

import java.time.LocalDate;

public class UserTestConstants {

    // admin constants
    public static Long ADMIN_ID = 1L;
    public static String ADMIN_FIRST_NAME = "Admin";
    public static String ADMIN_LAST_NAME = "Admin";
    public static String ADMIN_USERNAME = "admin";
    public static String ADMIN_PASSWORD = "admin";
    public static LocalDate ADMIN_DATE_OF_BIRTH = LocalDate.of(1999,1,1);
    public static String ADMIN_ROLE = "ADMIN";

    // users constants
    public static String USER_USERNAME = "user";
    public static Long USER_ID = 2L;
    public static String USER_FIRST_NAME = "User";
    public static String USER_LAST_NAME = "User";
    public static String USER_PASSWORD = "user";
    public static LocalDate USER_DATE_OF_BIRTH = LocalDate.of(1999,1,1);
    public static String USER_ROLE = "USER";

    // updated admin constants
    public static String UPDATED_ADMIN_FIRST_NAME = "admin";
    public static String UPDATED_ADMIN_LAST_NAME = "admin";
    public static String UPDATED_ADMIN_USERNAME = "Admin";
    public static String UPDATED_ADMIN_PASSWORD = "adminAdmin_adminAdmin";
    public static LocalDate UPDATED_ADMIN_DATE_OF_BIRTH = LocalDate.of(2000, 1,1);
    public static String UPDATED_ADMIN_ROLE = "USER";

    // updated user constants
    public static String UPDATED_USER_FIRST_NAME = "user";
    public static String UPDATED_USER_LAST_NAME = "user";
    public static String UPDATED_USER_USERNAME = "User";
    public static String UPDATED_USER_PASSWORD = "userUser_userUserUSER";
    public static LocalDate UPDATED_USER_DATE_OF_BIRTH = LocalDate.of(2000, 1,1);
    public static String UPDATED_USER_ROLE = "ADMIN";

    // non-existing admin constants
    public static String NON_EXISTING_ADMIN_FIRST_NAME = "new Admin";
    public static String NON_EXISTING_ADMIN_LAST_NAME = "better Admin";
    public static String NON_EXISTING_ADMIN_USERNAME = "admin123";
    public static String NON_EXISTING_ADMIN_PASSWORD = "asdfASDF.1_asdfASDF.1";
    public static LocalDate NON_EXISTING_ADMIN_DATE_OF_BIRTH = LocalDate.of(2000,1,1);
    public static String NON_EXISTING_ADMIN_ROLE = "ADMIN";

    // non-existing user constants
    public static String NON_EXISTING_USER_FIRST_NAME = "new User";
    public static String NON_EXISTING_USER_LAST_NAME = "better User";
    public static String NON_EXISTING_USER_USERNAME = "user123";
    public static String NON_EXISTING_USER_PASSWORD = "asdfASDF.1_asdfASDF.1";
    public static LocalDate NON_EXISTING_USER_DATE_OF_BIRTH = LocalDate.of(2000,1,1);
    public static String NON_EXISTING_USER_ROLE = "USER";

    public static Integer USERS_COUNT = 8;

    // exception constants
    public static String USER_BAD_REQUEST = "Bad Request";
    public static String USER_NOT_FOUND = "user__not_found";
    public static String USER_ALREADY_EXISTS = "user__already_exists";
    public static String USER_ACCESS_DENIED = "Access Denied";

    
}
