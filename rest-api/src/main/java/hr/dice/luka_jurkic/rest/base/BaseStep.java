
package hr.dice.luka_jurkic.rest.base;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BaseStep {

    @NotBlank(message = "Description is required")
    @Size(max = 2048, message = "Size must be up to 2048 characters")
    private String description;
}
