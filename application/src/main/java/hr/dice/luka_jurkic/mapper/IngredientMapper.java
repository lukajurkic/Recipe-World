package hr.dice.luka_jurkic.mapper;

import hr.dice.luka_jurkic.persistence.entity.IngredientEntity;
import hr.dice.luka_jurkic.rest.dto.IngredientDTO;
import hr.dice.luka_jurkic.rest.dto.IngredientDetailDTO;
import hr.dice.luka_jurkic.rest.request.IngredientRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IngredientMapper {

    IngredientDTO toDto(IngredientEntity ingredient);

    List<IngredientDTO> toDto(List<IngredientEntity> ingredients);

    IngredientDetailDTO toDetailDTO(IngredientEntity ingredient);

    List<IngredientDetailDTO> toDetailDTO(List<IngredientEntity> ingredients);

    @Mapping(target = "images", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    IngredientEntity requestToEntity(IngredientRequest request);

}
