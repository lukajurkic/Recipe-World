package hr.dice.luka_jurkic.rest.dto;

import hr.dice.luka_jurkic.rest.base.BaseRecipe;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecipeDetailDTO extends BaseRecipe {
    
    private Long id;
    
    private UserDTO user;
    
    private List<ImageDTO> images;

    private List<StepDTO> steps;
}

