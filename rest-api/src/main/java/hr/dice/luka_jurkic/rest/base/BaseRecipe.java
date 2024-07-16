package hr.dice.luka_jurkic.rest.base;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BaseRecipe {

    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 128, message = "Size must be between 1 and 128")
    private String name;

}
