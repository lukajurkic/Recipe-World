package hr.dice.luka_jurkic.rest.dto;

import hr.dice.luka_jurkic.rest.base.BaseRecipeIngredient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeIngredientDTO extends BaseRecipeIngredient {

    private Long id;

    private String recipeName;

    private String ingredientName;
}
