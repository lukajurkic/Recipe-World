package hr.dice.luka_jurkic.rest.base;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class BaseRecipeIngredient{

    @NotNull(message = "Amount is required")
    @Positive
    private BigDecimal amount;

    @NotEmpty(message = "Unit is required")
    private String unit;

}
