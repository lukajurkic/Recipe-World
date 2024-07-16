package hr.dice.luka_jurkic.rest.request;

import hr.dice.luka_jurkic.rest.base.BaseUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class UserRequest extends BaseUser {

    @NotBlank(message = "First name is required")
    @Size(min = 1, max = 64, message = "Size must be between 1 and 64")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 1, max = 64, message = "Size must be between 1 and 64")
    private String lastName;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 20, message = "Size must be between 8 and 20")
    private String password;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date must be in past")
    private Date dateOfBirth;
}
