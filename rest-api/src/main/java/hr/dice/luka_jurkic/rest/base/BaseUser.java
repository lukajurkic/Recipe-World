
package hr.dice.luka_jurkic.rest.base;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BaseUser {
    
    @NotBlank(message = "Username is required")
    @Size(min = 1, max = 64, message = "Size must be between 1 and 64")
    private String username;


    
}
