package hr.dice.luka_jurkic.service;

import hr.dice.luka_jurkic.rest.dto.CommentContentDTO;
import hr.dice.luka_jurkic.rest.dto.CommentDTO;
import hr.dice.luka_jurkic.rest.request.CommentRequest;

import java.util.List;

/**
 * Service interface for managing comments
 */
public interface CommentService {

    /**
     * Returns comment with given comment id
     * @param commentId unique identifier for each comment
     * @return Comment Content Data Transfer Object
     */
    CommentContentDTO getComment(Long commentId);

    /**
     * Returns list of comments made by user with given username
     * @param username unique identifier for each user
     * @return List of Comment Data Transfer Object
     */
    List<CommentDTO> getCommentsByUser(String username);

    /**
     * Returns list of comments made on recipe with given recipe name
     * @param recipeName unique identifier for each recipe
     * @return List of Comment Data Transfer Objects
     */
    List<CommentDTO> getCommentsByRecipe(String recipeName);

    /**
     * Returns list of comments with content made by user with given username
     * @param username unique identifier for each user
     * @return List of Comment Content Data Transfer Objects
     */
    List<CommentContentDTO> getCommentsContentByUser(String username);

    /**
     * Returns list of comments with content made on recipe with given recipe name
     * @param recipeName unique identifier for each recipe
     * @return List of Comment Content Data Transfer Objects
     */
    List<CommentContentDTO> getCommentContentByRecipe(String recipeName);

    /**
     * Returns a list of comments with content made on recipe with given recipe name by user with given username
     * @param username unique identifier for each user
     * @param recipeName unique identifier for each recipeName
     * @return List of Comment Contents Data Transfer Objects
     */
    List<CommentContentDTO> getCommentContentsByUserAndRecipe(String username, String recipeName);

    /**
     * Returns newly created comment base on information in request
     *
     * @param username authenticated user username
     * @param commentRequest information for creating comment
     * @return Comment Content Data Transfer Object
     */
    CommentContentDTO createComment(String username, CommentRequest commentRequest);

    /**
     * Deletes comment with given comment id
     * @param commentId unique identifier for each comment
     */
    void deleteComment(Long commentId);

    /**
     * Deletes all comments made y user with given username
     * @param username unique identifier for each user
     */
    void deleteCommentsByUser(String username);

    /**
     * Returns updated comment with given comment id base on information from request
     * @param commentId unique identifier for each comment
     * @param commentRequest information for updating comment
     * @return Comment Content Data Transfer Object
     */
    CommentContentDTO updateComment(Long commentId, CommentRequest commentRequest);
}
