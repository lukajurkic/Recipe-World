package hr.dice.luka_jurkic.mapper;

import hr.dice.luka_jurkic.persistence.entity.CommentEntity;
import hr.dice.luka_jurkic.persistence.entity.RecipeEntity;
import hr.dice.luka_jurkic.persistence.entity.UserEntity;
import hr.dice.luka_jurkic.rest.dto.CommentContentDTO;
import hr.dice.luka_jurkic.rest.dto.CommentDTO;
import hr.dice.luka_jurkic.rest.request.CommentRequest;
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
public class CommentMapperImpl implements CommentMapper {

    @Override
    public CommentDTO toDto(CommentEntity comment) {
        if ( comment == null ) {
            return null;
        }

        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setUsername( commentUserUsername( comment ) );
        commentDTO.setRecipeName( commentRecipeName( comment ) );
        commentDTO.setTitle( comment.getTitle() );
        commentDTO.setId( comment.getId() );

        return commentDTO;
    }

    @Override
    public List<CommentDTO> toDto(List<CommentEntity> comments) {
        if ( comments == null ) {
            return null;
        }

        List<CommentDTO> list = new ArrayList<CommentDTO>( comments.size() );
        for ( CommentEntity commentEntity : comments ) {
            list.add( toDto( commentEntity ) );
        }

        return list;
    }

    @Override
    public CommentContentDTO toContentDto(CommentEntity comment) {
        if ( comment == null ) {
            return null;
        }

        CommentContentDTO commentContentDTO = new CommentContentDTO();

        commentContentDTO.setUsername( commentUserUsername( comment ) );
        commentContentDTO.setRecipeName( commentRecipeName( comment ) );
        commentContentDTO.setTitle( comment.getTitle() );
        commentContentDTO.setId( comment.getId() );
        commentContentDTO.setText( comment.getText() );

        return commentContentDTO;
    }

    @Override
    public List<CommentContentDTO> toContentDto(List<CommentEntity> comments) {
        if ( comments == null ) {
            return null;
        }

        List<CommentContentDTO> list = new ArrayList<CommentContentDTO>( comments.size() );
        for ( CommentEntity commentEntity : comments ) {
            list.add( toContentDto( commentEntity ) );
        }

        return list;
    }

    @Override
    public CommentRequest contentDtoToRequest(CommentContentDTO contentDTO) {
        if ( contentDTO == null ) {
            return null;
        }

        CommentRequest commentRequest = new CommentRequest();

        commentRequest.setTitle( contentDTO.getTitle() );
        commentRequest.setRecipeName( contentDTO.getRecipeName() );
        commentRequest.setText( contentDTO.getText() );

        return commentRequest;
    }

    private String commentUserUsername(CommentEntity commentEntity) {
        if ( commentEntity == null ) {
            return null;
        }
        UserEntity user = commentEntity.getUser();
        if ( user == null ) {
            return null;
        }
        String username = user.getUsername();
        if ( username == null ) {
            return null;
        }
        return username;
    }

    private String commentRecipeName(CommentEntity commentEntity) {
        if ( commentEntity == null ) {
            return null;
        }
        RecipeEntity recipe = commentEntity.getRecipe();
        if ( recipe == null ) {
            return null;
        }
        String name = recipe.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
