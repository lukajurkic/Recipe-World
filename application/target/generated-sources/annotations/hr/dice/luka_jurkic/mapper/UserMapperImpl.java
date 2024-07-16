package hr.dice.luka_jurkic.mapper;

import hr.dice.luka_jurkic.persistence.entity.UserEntity;
import hr.dice.luka_jurkic.rest.dto.UserDTO;
import hr.dice.luka_jurkic.rest.dto.UserDetailsDTO;
import hr.dice.luka_jurkic.rest.request.UserRequest;
import java.sql.Date;
import java.time.ZoneOffset;
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
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toDto(UserEntity user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setUsername( user.getUsername() );
        userDTO.setId( user.getId() );
        if ( user.getRole() != null ) {
            userDTO.setRole( user.getRole().name() );
        }

        return userDTO;
    }

    @Override
    public List<UserDTO> toDto(List<UserEntity> users) {
        if ( users == null ) {
            return null;
        }

        List<UserDTO> list = new ArrayList<UserDTO>( users.size() );
        for ( UserEntity userEntity : users ) {
            list.add( toDto( userEntity ) );
        }

        return list;
    }

    @Override
    public UserDetailsDTO toDetailedDto(UserEntity user) {
        if ( user == null ) {
            return null;
        }

        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();

        userDetailsDTO.setUsername( user.getUsername() );
        userDetailsDTO.setId( user.getId() );
        userDetailsDTO.setFirstName( user.getFirstName() );
        userDetailsDTO.setLastName( user.getLastName() );
        userDetailsDTO.setDateOfBirth( user.getDateOfBirth() );
        if ( user.getRole() != null ) {
            userDetailsDTO.setRole( user.getRole().name() );
        }

        return userDetailsDTO;
    }

    @Override
    public List<UserDetailsDTO> toDetailedDto(List<UserEntity> users) {
        if ( users == null ) {
            return null;
        }

        List<UserDetailsDTO> list = new ArrayList<UserDetailsDTO>( users.size() );
        for ( UserEntity userEntity : users ) {
            list.add( toDetailedDto( userEntity ) );
        }

        return list;
    }

    @Override
    public UserEntity requestToEntity(UserRequest request) {
        if ( request == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setFirstName( request.getFirstName() );
        userEntity.setLastName( request.getLastName() );
        userEntity.setUsername( request.getUsername() );
        userEntity.setPassword( request.getPassword() );
        if ( request.getDateOfBirth() != null ) {
            userEntity.setDateOfBirth( request.getDateOfBirth().toLocalDate() );
        }

        return userEntity;
    }

    @Override
    public UserRequest detailDTOToRequest(UserDetailsDTO userDetailsDTO) {
        if ( userDetailsDTO == null ) {
            return null;
        }

        UserRequest userRequest = new UserRequest();

        userRequest.setUsername( userDetailsDTO.getUsername() );
        userRequest.setFirstName( userDetailsDTO.getFirstName() );
        userRequest.setLastName( userDetailsDTO.getLastName() );
        if ( userDetailsDTO.getDateOfBirth() != null ) {
            userRequest.setDateOfBirth( new Date( userDetailsDTO.getDateOfBirth().atStartOfDay( ZoneOffset.UTC ).toInstant().toEpochMilli() ) );
        }

        return userRequest;
    }
}
