package hr.dice.luka_jurkic.mapper;

import hr.dice.luka_jurkic.persistence.entity.CommentEntity;
import hr.dice.luka_jurkic.rest.dto.CommentContentDTO;
import hr.dice.luka_jurkic.rest.dto.CommentDTO;
import hr.dice.luka_jurkic.rest.request.CommentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "recipeName", source = "recipe.name")
    CommentDTO toDto(CommentEntity comment);

    List<CommentDTO> toDto(List<CommentEntity> comments);

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "recipeName", source = "recipe.name")
    CommentContentDTO toContentDto(CommentEntity comment);

    List<CommentContentDTO> toContentDto(List<CommentEntity> comments);

    CommentRequest contentDtoToRequest(CommentContentDTO  contentDTO);
}
