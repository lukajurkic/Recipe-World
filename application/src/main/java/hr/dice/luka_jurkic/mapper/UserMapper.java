package hr.dice.luka_jurkic.mapper;

import hr.dice.luka_jurkic.persistence.entity.UserEntity;
import hr.dice.luka_jurkic.rest.dto.UserDTO;
import hr.dice.luka_jurkic.rest.dto.UserDetailsDTO;
import hr.dice.luka_jurkic.rest.request.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(UserEntity user);
    
    List<UserDTO> toDto(List<UserEntity> users);
    
    UserDetailsDTO toDetailedDto(UserEntity user);
    
    List<UserDetailsDTO> toDetailedDto(List<UserEntity> users);
    
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "recipes", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "comments", ignore = true)
    UserEntity requestToEntity(UserRequest request);

    @Mapping(target = "password", ignore = true)
    UserRequest detailDTOToRequest(UserDetailsDTO userDetailsDTO);
}
