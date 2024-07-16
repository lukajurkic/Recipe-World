package hr.dice.luka_jurkic.mapper;

import hr.dice.luka_jurkic.persistence.entity.RecipeIngredientEntity;
import hr.dice.luka_jurkic.rest.dto.RecipeIngredientDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecipeIngredientMapper {

    @Mapping(target = "ingredientName", source = "ingredient.name")
    @Mapping(target = "recipeName", source = "recipe.name")
    RecipeIngredientDTO toDto(RecipeIngredientEntity recipeIngredient);

    List<RecipeIngredientDTO> toDto(List<RecipeIngredientEntity> recipeIngredients);
}
