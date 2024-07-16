package hr.dice.luka_jurkic.rest.dto;

import hr.dice.luka_jurkic.rest.base.BaseUser;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserDetailsDTO extends BaseUser {
    
    private Long id;
    
    private String firstName;
    
    private String lastName;
    
    private LocalDate dateOfBirth;

    private String role;
}
