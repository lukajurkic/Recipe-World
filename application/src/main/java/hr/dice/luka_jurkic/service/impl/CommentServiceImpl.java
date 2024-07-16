package hr.dice.luka_jurkic.service.impl;

import hr.dice.luka_jurkic.mapper.CommentMapper;
import hr.dice.luka_jurkic.persistence.entity.CommentEntity;
import hr.dice.luka_jurkic.persistence.entity.RecipeEntity;
import hr.dice.luka_jurkic.persistence.entity.UserEntity;
import hr.dice.luka_jurkic.persistence.repository.CommentRepository;
import hr.dice.luka_jurkic.persistence.repository.RecipeRepository;
import hr.dice.luka_jurkic.persistence.repository.UserRepository;
import hr.dice.luka_jurkic.rest.dto.CommentContentDTO;
import hr.dice.luka_jurkic.rest.dto.CommentDTO;
import hr.dice.luka_jurkic.rest.request.CommentRequest;
import hr.dice.luka_jurkic.service.CommentService;
import hr.dice.luka_jurkic.service.exceptions.CommentServiceException;
import hr.dice.luka_jurkic.service.exceptions.RecipeServiceException;
import hr.dice.luka_jurkic.service.exceptions.UserServiceException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentRepository commentRepository, RecipeRepository recipeRepository, UserRepository userRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public CommentContentDTO getComment(Long commentId) {
        return commentMapper.toContentDto(fetchComment(commentId));
    }

    @Override
    public List<CommentDTO> getCommentsByUser(String username) {
        return commentMapper.toDto(commentRepository.findByUserUsername(username));
    }

    @Override
    public List<CommentDTO> getCommentsByRecipe(String recipeName) {
        return commentMapper.toDto(commentRepository.findByRecipeName(recipeName));
    }

    @Override
    public List<CommentContentDTO> getCommentsContentByUser(String username) {
        return commentMapper.toContentDto(commentRepository.findByUserUsername(username));
    }

    @Override
    public List<CommentContentDTO> getCommentContentByRecipe(String recipeName) {
        return commentMapper.toContentDto(commentRepository.findByRecipeName(recipeName));
    }

    @Override
    public List<CommentContentDTO> getCommentContentsByUserAndRecipe(String username, String recipeName) {
        return commentMapper.toContentDto(commentRepository.findByUserUsernameAndRecipeName(username, recipeName));
    }

    @Override
    public CommentContentDTO createComment(String username, CommentRequest commentRequest) {
        CommentEntity comment = new CommentEntity(
                commentRequest.getTitle(),
                commentRequest.getText(),
                fetchUser(username),
                fetchRecipe(commentRequest.getRecipeName())
        );
        return commentMapper.toContentDto(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepository.delete(fetchComment(commentId));
    }

    @Override
    public void deleteCommentsByUser(String username) {
        commentRepository.deleteByUserUsername(username);
    }

    @Override
    public CommentContentDTO updateComment(Long commentId, CommentRequest commentRequest) {
        CommentEntity comment = fetchComment(commentId);
        comment.setText(commentRequest.getText());
        comment.setTitle(commentRequest.getTitle());
        return commentMapper.toContentDto(commentRepository.save(comment));
    }

    private RecipeEntity fetchRecipe(String recipeName) {
        return recipeRepository.findByName(recipeName).orElseThrow(RecipeServiceException::notFound);
    }

    private UserEntity fetchUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(UserServiceException::notFound);
    }

    private CommentEntity fetchComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(CommentServiceException::notFound);
    }
}
