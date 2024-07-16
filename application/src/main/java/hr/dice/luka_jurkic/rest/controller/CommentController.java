package hr.dice.luka_jurkic.rest.controller;

import hr.dice.luka_jurkic.rest.dto.CommentContentDTO;
import hr.dice.luka_jurkic.rest.dto.CommentDTO;
import hr.dice.luka_jurkic.rest.request.CommentRequest;
import hr.dice.luka_jurkic.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/comments")
@Validated
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public List<CommentDTO> getCommentsByUser(Authentication authentication) {
        return commentService.getCommentsByUser(authentication.getName());
    }

    @GetMapping("/recipe/{recipeName}")
    public List<CommentDTO> getCommentsByRecipe(@PathVariable String recipeName) {
        return commentService.getCommentsByRecipe(recipeName);
    }

    @GetMapping("/details")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public List<CommentContentDTO> getCommentContentsByUser(Authentication authentication) {
        return commentService.getCommentsContentByUser(authentication.getName());
    }

    @GetMapping("/details/{recipeName}")
    public List<CommentContentDTO> getCommentContentsByRecipe(@PathVariable String recipeName) {
        return commentService.getCommentContentByRecipe(recipeName);
    }

    @PostMapping
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public CommentContentDTO createComment(Authentication authentication, @Valid @RequestBody CommentRequest commentRequest) {
        return commentService.createComment(authentication.getName(), commentRequest);
    }

    @DeleteMapping("/{commentId}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public void deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
    }

    @PutMapping("/{commentId}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public CommentContentDTO updateComment(@PathVariable Long commentId, @Valid @RequestBody CommentRequest commentRequest) {
        return commentService.updateComment(commentId, commentRequest);
    }
}
