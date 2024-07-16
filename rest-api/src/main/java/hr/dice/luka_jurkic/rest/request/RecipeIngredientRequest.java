package hr.dice.luka_jurkic.rest.request;

import hr.dice.luka_jurkic.rest.base.BaseRecipeIngredient;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeIngredientRequest extends BaseRecipeIngredient {

    @NotNull(message = "Ingredient is required")
    private String ingredientName;
}
