package hr.dice.luka_jurkic.mapper;

import hr.dice.luka_jurkic.persistence.entity.RecipeEntity;
import hr.dice.luka_jurkic.persistence.entity.RecipeImageEntity;
import hr.dice.luka_jurkic.persistence.entity.StepEntity;
import hr.dice.luka_jurkic.persistence.entity.UserEntity;
import hr.dice.luka_jurkic.rest.dto.ImageDTO;
import hr.dice.luka_jurkic.rest.dto.RecipeDTO;
import hr.dice.luka_jurkic.rest.dto.RecipeDetailDTO;
import hr.dice.luka_jurkic.rest.dto.StepDTO;
import hr.dice.luka_jurkic.rest.dto.UserDTO;
import hr.dice.luka_jurkic.rest.request.RecipeRequest;
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
public class RecipeMapperImpl implements RecipeMapper {

    @Override
    public RecipeDTO toDto(RecipeEntity recipe) {
        if ( recipe == null ) {
            return null;
        }

        RecipeDTO recipeDTO = new RecipeDTO();

        recipeDTO.setName( recipe.getName() );
        recipeDTO.setId( recipe.getId() );
        recipeDTO.setUser( userEntityToUserDTO( recipe.getUser() ) );

        return recipeDTO;
    }

    @Override
    public List<RecipeDTO> toDto(List<RecipeEntity> recipes) {
        if ( recipes == null ) {
            return null;
        }

        List<RecipeDTO> list = new ArrayList<RecipeDTO>( recipes.size() );
        for ( RecipeEntity recipeEntity : recipes ) {
            list.add( toDto( recipeEntity ) );
        }

        return list;
    }

    @Override
    public RecipeDetailDTO toDetailDTO(RecipeEntity recipe) {
        if ( recipe == null ) {
            return null;
        }

        RecipeDetailDTO recipeDetailDTO = new RecipeDetailDTO();

        recipeDetailDTO.setName( recipe.getName() );
        recipeDetailDTO.setId( recipe.getId() );
        recipeDetailDTO.setUser( userEntityToUserDTO( recipe.getUser() ) );
        recipeDetailDTO.setImages( recipeImageEntityListToImageDTOList( recipe.getImages() ) );
        recipeDetailDTO.setSteps( stepEntityListToStepDTOList( recipe.getSteps() ) );

        return recipeDetailDTO;
    }

    @Override
    public List<RecipeDetailDTO> toDetailDTO(List<RecipeEntity> recipes) {
        if ( recipes == null ) {
            return null;
        }

        List<RecipeDetailDTO> list = new ArrayList<RecipeDetailDTO>( recipes.size() );
        for ( RecipeEntity recipeEntity : recipes ) {
            list.add( toDetailDTO( recipeEntity ) );
        }

        return list;
    }

    @Override
    public RecipeRequest recipeDTOToRequest(RecipeDTO recipeDTO) {
        if ( recipeDTO == null ) {
            return null;
        }

        RecipeRequest recipeRequest = new RecipeRequest();

        recipeRequest.setName( recipeDTO.getName() );

        return recipeRequest;
    }

    protected UserDTO userEntityToUserDTO(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setUsername( userEntity.getUsername() );
        userDTO.setId( userEntity.getId() );
        if ( userEntity.getRole() != null ) {
            userDTO.setRole( userEntity.getRole().name() );
        }

        return userDTO;
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

    protected List<ImageDTO> recipeImageEntityListToImageDTOList(List<RecipeImageEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<ImageDTO> list1 = new ArrayList<ImageDTO>( list.size() );
        for ( RecipeImageEntity recipeImageEntity : list ) {
            list1.add( recipeImageEntityToImageDTO( recipeImageEntity ) );
        }

        return list1;
    }

    protected StepDTO stepEntityToStepDTO(StepEntity stepEntity) {
        if ( stepEntity == null ) {
            return null;
        }

        StepDTO stepDTO = new StepDTO();

        stepDTO.setDescription( stepEntity.getDescription() );
        stepDTO.setId( stepEntity.getId() );
        stepDTO.setNumber( stepEntity.getNumber() );
        stepDTO.setRecipe( toDto( stepEntity.getRecipe() ) );

        return stepDTO;
    }

    protected List<StepDTO> stepEntityListToStepDTOList(List<StepEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<StepDTO> list1 = new ArrayList<StepDTO>( list.size() );
        for ( StepEntity stepEntity : list ) {
            list1.add( stepEntityToStepDTO( stepEntity ) );
        }

        return list1;
    }
}
