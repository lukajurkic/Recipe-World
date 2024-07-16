package hr.dice.luka_jurkic.mapper;

import hr.dice.luka_jurkic.persistence.entity.RecipeEntity;
import hr.dice.luka_jurkic.rest.dto.RecipeDTO;
import hr.dice.luka_jurkic.rest.dto.RecipeDetailDTO;
import hr.dice.luka_jurkic.rest.request.RecipeRequest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecipeMapper {
    RecipeDTO toDto(RecipeEntity recipe);

    List<RecipeDTO> toDto(List<RecipeEntity> recipes);

    RecipeDetailDTO toDetailDTO(RecipeEntity recipe);

    List<RecipeDetailDTO> toDetailDTO(List<RecipeEntity> recipes);

    RecipeRequest recipeDTOToRequest(RecipeDTO recipeDTO);


}
