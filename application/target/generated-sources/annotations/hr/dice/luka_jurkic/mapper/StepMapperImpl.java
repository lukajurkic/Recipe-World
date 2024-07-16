package hr.dice.luka_jurkic.mapper;

import hr.dice.luka_jurkic.persistence.entity.RecipeEntity;
import hr.dice.luka_jurkic.persistence.entity.StepEntity;
import hr.dice.luka_jurkic.persistence.entity.UserEntity;
import hr.dice.luka_jurkic.rest.dto.RecipeDTO;
import hr.dice.luka_jurkic.rest.dto.StepDTO;
import hr.dice.luka_jurkic.rest.dto.UserDTO;
import hr.dice.luka_jurkic.rest.request.StepRequest;
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
public class StepMapperImpl implements StepMapper {

    @Override
    public StepDTO toDto(StepEntity step) {
        if ( step == null ) {
            return null;
        }

        StepDTO stepDTO = new StepDTO();

        stepDTO.setDescription( step.getDescription() );
        stepDTO.setId( step.getId() );
        stepDTO.setNumber( step.getNumber() );
        stepDTO.setRecipe( recipeEntityToRecipeDTO( step.getRecipe() ) );

        return stepDTO;
    }

    @Override
    public List<StepDTO> toDto(List<StepEntity> steps) {
        if ( steps == null ) {
            return null;
        }

        List<StepDTO> list = new ArrayList<StepDTO>( steps.size() );
        for ( StepEntity stepEntity : steps ) {
            list.add( toDto( stepEntity ) );
        }

        return list;
    }

    @Override
    public StepEntity requestToEntity(StepRequest request) {
        if ( request == null ) {
            return null;
        }

        StepEntity stepEntity = new StepEntity();

        stepEntity.setDescription( request.getDescription() );

        return stepEntity;
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

    protected RecipeDTO recipeEntityToRecipeDTO(RecipeEntity recipeEntity) {
        if ( recipeEntity == null ) {
            return null;
        }

        RecipeDTO recipeDTO = new RecipeDTO();

        recipeDTO.setName( recipeEntity.getName() );
        recipeDTO.setId( recipeEntity.getId() );
        recipeDTO.setUser( userEntityToUserDTO( recipeEntity.getUser() ) );

        return recipeDTO;
    }
}
