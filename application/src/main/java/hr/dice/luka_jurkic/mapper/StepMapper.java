package hr.dice.luka_jurkic.mapper;

import hr.dice.luka_jurkic.persistence.entity.StepEntity;
import hr.dice.luka_jurkic.rest.dto.StepDTO;
import hr.dice.luka_jurkic.rest.request.StepRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StepMapper {

    StepDTO toDto(StepEntity step);

    List<StepDTO> toDto(List<StepEntity> steps);

    @Mapping(target = "version", ignore = true)
    @Mapping(target = "recipe", ignore = true)
    @Mapping(target = "number", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    StepEntity requestToEntity(StepRequest request);
    
}
