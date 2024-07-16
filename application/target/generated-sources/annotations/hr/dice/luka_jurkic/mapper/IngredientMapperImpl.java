package hr.dice.luka_jurkic.mapper;

import hr.dice.luka_jurkic.persistence.entity.IngredientCategory;
import hr.dice.luka_jurkic.persistence.entity.IngredientEntity;
import hr.dice.luka_jurkic.persistence.entity.IngredientImageEntity;
import hr.dice.luka_jurkic.rest.dto.ImageDTO;
import hr.dice.luka_jurkic.rest.dto.IngredientDTO;
import hr.dice.luka_jurkic.rest.dto.IngredientDetailDTO;
import hr.dice.luka_jurkic.rest.request.IngredientRequest;
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
public class IngredientMapperImpl implements IngredientMapper {

    @Override
    public IngredientDTO toDto(IngredientEntity ingredient) {
        if ( ingredient == null ) {
            return null;
        }

        IngredientDTO ingredientDTO = new IngredientDTO();

        ingredientDTO.setName( ingredient.getName() );
        if ( ingredient.getCategory() != null ) {
            ingredientDTO.setCategory( ingredient.getCategory().name() );
        }
        ingredientDTO.setId( ingredient.getId() );

        return ingredientDTO;
    }

    @Override
    public List<IngredientDTO> toDto(List<IngredientEntity> ingredients) {
        if ( ingredients == null ) {
            return null;
        }

        List<IngredientDTO> list = new ArrayList<IngredientDTO>( ingredients.size() );
        for ( IngredientEntity ingredientEntity : ingredients ) {
            list.add( toDto( ingredientEntity ) );
        }

        return list;
    }

    @Override
    public IngredientDetailDTO toDetailDTO(IngredientEntity ingredient) {
        if ( ingredient == null ) {
            return null;
        }

        IngredientDetailDTO ingredientDetailDTO = new IngredientDetailDTO();

        ingredientDetailDTO.setName( ingredient.getName() );
        if ( ingredient.getCategory() != null ) {
            ingredientDetailDTO.setCategory( ingredient.getCategory().name() );
        }
        ingredientDetailDTO.setId( ingredient.getId() );
        ingredientDetailDTO.setImages( ingredientImageEntityListToImageDTOList( ingredient.getImages() ) );

        return ingredientDetailDTO;
    }

    @Override
    public List<IngredientDetailDTO> toDetailDTO(List<IngredientEntity> ingredients) {
        if ( ingredients == null ) {
            return null;
        }

        List<IngredientDetailDTO> list = new ArrayList<IngredientDetailDTO>( ingredients.size() );
        for ( IngredientEntity ingredientEntity : ingredients ) {
            list.add( toDetailDTO( ingredientEntity ) );
        }

        return list;
    }

    @Override
    public IngredientEntity requestToEntity(IngredientRequest request) {
        if ( request == null ) {
            return null;
        }

        IngredientEntity ingredientEntity = new IngredientEntity();

        ingredientEntity.setName( request.getName() );
        if ( request.getCategory() != null ) {
            ingredientEntity.setCategory( Enum.valueOf( IngredientCategory.class, request.getCategory() ) );
        }

        return ingredientEntity;
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

    protected List<ImageDTO> ingredientImageEntityListToImageDTOList(List<IngredientImageEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<ImageDTO> list1 = new ArrayList<ImageDTO>( list.size() );
        for ( IngredientImageEntity ingredientImageEntity : list ) {
            list1.add( ingredientImageEntityToImageDTO( ingredientImageEntity ) );
        }

        return list1;
    }
}
