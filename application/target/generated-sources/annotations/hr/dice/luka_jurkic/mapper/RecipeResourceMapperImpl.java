package hr.dice.luka_jurkic.mapper;

import hr.dice.luka_jurkic.rest.dto.ImportedRecipeDTO;
import hr.dice.luka_jurkic.rest.resources.RecipeResource;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-16T09:10:46+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (Red Hat, Inc.)"
)
@Component
public class RecipeResourceMapperImpl implements RecipeResourceMapper {

    @Override
    public ImportedRecipeDTO resourceToDto(RecipeResource recipeResource) {
        if ( recipeResource == null ) {
            return null;
        }

        ImportedRecipeDTO importedRecipeDTO = new ImportedRecipeDTO();

        importedRecipeDTO.setName( recipeResource.getTitle() );
        importedRecipeDTO.setImportedSteps( RecipeResourceMapper.instructionsToImportedSteps( recipeResource.getInstructions() ) );
        importedRecipeDTO.setImportedIngredients( RecipeResourceMapper.stringIngredientsToImportedIngredients( recipeResource.getIngredients() ) );

        return importedRecipeDTO;
    }

    @Override
    public List<ImportedRecipeDTO> resourceToDto(List<RecipeResource> resources) {
        if ( resources == null ) {
            return null;
        }

        List<ImportedRecipeDTO> list = new ArrayList<ImportedRecipeDTO>( resources.size() );
        for ( RecipeResource recipeResource : resources ) {
            list.add( resourceToDto( recipeResource ) );
        }

        return list;
    }
}
