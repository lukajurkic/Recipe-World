package hr.dice.luka_jurkic.mapper;

import hr.dice.luka_jurkic.persistence.entity.IngredientImageEntity;
import hr.dice.luka_jurkic.persistence.entity.RecipeImageEntity;
import hr.dice.luka_jurkic.rest.dto.ImageDTO;
import hr.dice.luka_jurkic.rest.request.ImageRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    List<ImageDTO> toRecipeImageDto(List<RecipeImageEntity> recipeImageEntities);

    List<ImageDTO> toIngredientImageDto(List<IngredientImageEntity> ingredientImageEntities);

    @Mapping(target = "version", ignore = true)
    @Mapping(target = "recipe", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    RecipeImageEntity toRecipeEntity(ImageRequest request);

    @Mapping(target = "version", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "ingredient", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    IngredientImageEntity toIngredientEntity(ImageRequest request);
}
