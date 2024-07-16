package hr.dice.luka_jurkic.rest.dto;

import hr.dice.luka_jurkic.rest.base.BaseRecipe;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecipeDTO extends BaseRecipe {

    private Long id;

    private UserDTO user;
}
