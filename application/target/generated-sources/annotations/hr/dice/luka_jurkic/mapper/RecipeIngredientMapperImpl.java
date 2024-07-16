package hr.dice.luka_jurkic.mapper;

import hr.dice.luka_jurkic.persistence.entity.IngredientEntity;
import hr.dice.luka_jurkic.persistence.entity.RecipeEntity;
import hr.dice.luka_jurkic.persistence.entity.RecipeIngredientEntity;
import hr.dice.luka_jurkic.rest.dto.RecipeIngredientDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-16T09:10:47+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (Red Hat, Inc.)"
)
@Component
public class RecipeIngredientMapperImpl implements RecipeIngredientMapper {

    @Override
    public RecipeIngredientDTO toDto(RecipeIngredientEntity recipeIngredient) {
        if ( recipeIngredient == null ) {
            return null;
        }

        RecipeIngredientDTO recipeIngredientDTO = new RecipeIngredientDTO();

        recipeIngredientDTO.setIngredientName( recipeIngredientIngredientName( recipeIngredient ) );
        recipeIngredientDTO.setRecipeName( recipeIngredientRecipeName( recipeIngredient ) );
        recipeIngredientDTO.setAmount( recipeIngredient.getAmount() );
        if ( recipeIngredient.getUnit() != null ) {
            recipeIngredientDTO.setUnit( recipeIngredient.getUnit().name() );
        }
        recipeIngredientDTO.setId( recipeIngredient.getId() );

        return recipeIngredientDTO;
    }

    @Override
    public List<RecipeIngredientDTO> toDto(List<RecipeIngredientEntity> recipeIngredients) {
        if ( recipeIngredients == null ) {
            return null;
        }

        List<RecipeIngredientDTO> list = new ArrayList<RecipeIngredientDTO>( recipeIngredients.size() );
        for ( RecipeIngredientEntity recipeIngredientEntity : recipeIngredients ) {
            list.add( toDto( recipeIngredientEntity ) );
        }

        return list;
    }

    private String recipeIngredientIngredientName(RecipeIngredientEntity recipeIngredientEntity) {
        if ( recipeIngredientEntity == null ) {
            return null;
        }
        IngredientEntity ingredient = recipeIngredientEntity.getIngredient();
        if ( ingredient == null ) {
            return null;
        }
        String name = ingredient.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String recipeIngredientRecipeName(RecipeIngredientEntity recipeIngredientEntity) {
        if ( recipeIngredientEntity == null ) {
            return null;
        }
        RecipeEntity recipe = recipeIngredientEntity.getRecipe();
        if ( recipe == null ) {
            return null;
        }
        String name = recipe.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
