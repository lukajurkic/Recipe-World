package hr.dice.luka_jurkic.rest.dto;

import hr.dice.luka_jurkic.rest.base.BaseUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO extends BaseUser {
    
    private Long id;

    private String role;
}

