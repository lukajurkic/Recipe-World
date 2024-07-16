package hr.dice.luka_jurkic.rest.dto;

import hr.dice.luka_jurkic.rest.base.BaseRecipe;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ImportedRecipeDTO extends BaseRecipe {

    private List<ImportedIngredientDTO> importedIngredients;

    private List<ImportedStepDTO> importedSteps;
}
