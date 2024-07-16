package hr.dice.luka_jurkic.rest.dto;

import hr.dice.luka_jurkic.rest.base.BaseComment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO extends BaseComment {
    
    private Long id;

    private String username;
}
