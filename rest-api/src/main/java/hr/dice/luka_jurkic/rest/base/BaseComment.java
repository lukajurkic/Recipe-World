package hr.dice.luka_jurkic.rest.base;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BaseComment {

    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 64, message = "Size must be between 1 and 64")
    private String title;

    @NotEmpty(message = "Recipe is required")
    private String recipeName;
}
