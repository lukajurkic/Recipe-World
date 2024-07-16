package hr.dice.luka_jurkic.mapper;

import hr.dice.luka_jurkic.persistence.entity.IngredientImageEntity;
import hr.dice.luka_jurkic.persistence.entity.RecipeImageEntity;
import hr.dice.luka_jurkic.rest.dto.ImageDTO;
import hr.dice.luka_jurkic.rest.request.ImageRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-07-16T09:10:47+0200",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (Red Hat, Inc.)"
)
@Component
public class ImageMapperImpl implements ImageMapper {

    @Override
    public List<ImageDTO> toRecipeImageDto(List<RecipeImageEntity> recipeImageEntities) {
        if ( recipeImageEntities == null ) {
            return null;
        }

        List<ImageDTO> list = new ArrayList<ImageDTO>( recipeImageEntities.size() );
        for ( RecipeImageEntity recipeImageEntity : recipeImageEntities ) {
            list.add( recipeImageEntityToImageDTO( recipeImageEntity ) );
        }

        return list;
    }

    @Override
    public List<ImageDTO> toIngredientImageDto(List<IngredientImageEntity> ingredientImageEntities) {
        if ( ingredientImageEntities == null ) {
            return null;
        }

        List<ImageDTO> list = new ArrayList<ImageDTO>( ingredientImageEntities.size() );
        for ( IngredientImageEntity ingredientImageEntity : ingredientImageEntities ) {
            list.add( ingredientImageEntityToImageDTO( ingredientImageEntity ) );
        }

        return list;
    }

    @Override
    public RecipeImageEntity toRecipeEntity(ImageRequest request) {
        if ( request == null ) {
            return null;
        }

        RecipeImageEntity recipeImageEntity = new RecipeImageEntity();

        byte[] pictureData = request.getPictureData();
        if ( pictureData != null ) {
            recipeImageEntity.setPictureData( Arrays.copyOf( pictureData, pictureData.length ) );
        }
        recipeImageEntity.setDescription( request.getDescription() );

        return recipeImageEntity;
    }

    @Override
    public IngredientImageEntity toIngredientEntity(ImageRequest request) {
        if ( request == null ) {
            return null;
        }

        IngredientImageEntity ingredientImageEntity = new IngredientImageEntity();

        byte[] pictureData = request.getPictureData();
        if ( pictureData != null ) {
            ingredientImageEntity.setPictureData( Arrays.copyOf( pictureData, pictureData.length ) );
        }
        ingredientImageEntity.setDescription( request.getDescription() );

        return ingredientImageEntity;
    }

    protected ImageDTO recipeImageEntityToImageDTO(RecipeImageEntity recipeImageEntity) {
        if ( recipeImageEntity == null ) {
            return null;
        }

        ImageDTO imageDTO = new ImageDTO();

        byte[] pictureData = recipeImageEntity.getPictureData();
        if ( pictureData != null ) {
            imageDTO.setPictureData( Arrays.copyOf( pictureData, pictureData.length ) );
        }
        imageDTO.setDescription( recipeImageEntity.getDescription() );
        imageDTO.setId( recipeImageEntity.getId() );

        return imageDTO;
    }

    protected ImageDTO ingredientImageEntityToImageDTO(IngredientImageEntity ingredientImageEntity) {
        if ( ingredientImageEntity == null ) {
            return null;
        }

        ImageDTO imageDTO = new ImageDTO();

        byte[] pictureData = ingredientImageEntity.getPictureData();
        if ( pictureData != null ) {
            imageDTO.setPictureData( Arrays.copyOf( pictureData, pictureData.length ) );
        }
        imageDTO.setDescription( ingredientImageEntity.getDescription() );
        imageDTO.setId( ingredientImageEntity.getId() );

        return imageDTO;
    }
}
