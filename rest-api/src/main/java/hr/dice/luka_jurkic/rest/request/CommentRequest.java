package hr.dice.luka_jurkic.rest.request;

import hr.dice.luka_jurkic.rest.base.BaseComment;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest extends BaseComment {

    @Size(max = 1024, message = "Size must be up to 1024 characters")
    @NotNull(message = "Text is required")
    private String text;
}
