package hr.dice.luka_jurkic.frontend;

import hr.dice.luka_jurkic.AbstractIT;
import hr.dice.luka_jurkic.clients.CommentTestClient;
import hr.dice.luka_jurkic.factory.CommentFactory;
import hr.dice.luka_jurkic.persistence.repository.CommentRepository;
import hr.dice.luka_jurkic.rest.dto.CommentContentDTO;
import hr.dice.luka_jurkic.rest.dto.CommentDTO;
import hr.dice.luka_jurkic.rest.request.CommentRequest;
import io.restassured.common.mapper.TypeRef;
import jakarta.inject.Inject;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static hr.dice.luka_jurkic.constants.CommentTestConstants.COMMENT_BAD_REQUEST;
import static hr.dice.luka_jurkic.constants.CommentTestConstants.COMMENT_COUNT_FOR_RECIPE;
import static hr.dice.luka_jurkic.constants.CommentTestConstants.COMMENT_COUNT_FOR_USER;
import static hr.dice.luka_jurkic.constants.CommentTestConstants.COMMENT_ID;
import static hr.dice.luka_jurkic.constants.CommentTestConstants.COMMENT_NOT_FOUND;
import static hr.dice.luka_jurkic.constants.CommentTestConstants.COMMENT_RECIPE_RECIPE_NAME;
import static hr.dice.luka_jurkic.constants.CommentTestConstants.NON_EXISTING_COMMENT_ID;
import static hr.dice.luka_jurkic.constants.CommentTestConstants.NON_EXISTING_COMMENT_RECIPE_RECIPE_NAME;
import static hr.dice.luka_jurkic.constants.CommentTestConstants.NON_EXISTING_COMMENT_TEXT;
import static hr.dice.luka_jurkic.constants.CommentTestConstants.NON_EXISTING_COMMENT_TITLE;
import static hr.dice.luka_jurkic.constants.CommentTestConstants.UPDATED_COMMENT_TEXT;
import static hr.dice.luka_jurkic.constants.CommentTestConstants.UPDATED_COMMENT_TITLE;
import static hr.dice.luka_jurkic.constants.UserTestConstants.ADMIN_PASSWORD;
import static hr.dice.luka_jurkic.constants.UserTestConstants.ADMIN_USERNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CommentApiIT extends AbstractIT {

    @Inject CommentRepository commentRepository;

    @Test
    @DisplayName("GET Comment by User - OK")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe", "migrations/comment"})
    public void givenValidUsername_whenGetCommentByUser_thenExpect200() {
        List<CommentDTO> comments = CommentTestClient.getCommentsByUser(ADMIN_USERNAME, ADMIN_PASSWORD)
                .then()
                .statusCode(200)
                .and().extract()
                .body().as(new TypeRef<>() {
                });
        assertThat(comments).isNotNull();
        assertThat(comments.size()).isEqualTo(COMMENT_COUNT_FOR_USER);
    }

    @Test
    @DisplayName("GET Comment by Recipe - OK")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe", "migrations/comment"})
    public void givenValidRecipeName_whenGetCommentByRecipe_thenExpect200() {
        List<CommentDTO> comments = CommentTestClient.getCommentsByRecipe(COMMENT_RECIPE_RECIPE_NAME)
                .then()
                .statusCode(200)
                .and().extract()
                .body().as(new TypeRef<>() {
                });
        assertThat(comments).isNotNull();
        assertThat(comments.size()).isEqualTo(COMMENT_COUNT_FOR_RECIPE);
    }

    @Test
    @DisplayName("GET Comment Content by User - OK")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe", "migrations/comment"})
    public void givenValidUsername_whenGetCommentContentByUser_thenExpect200() {
        List<CommentContentDTO> comments = CommentTestClient.getCommentDetailsByUser(ADMIN_USERNAME, ADMIN_PASSWORD)
                .then()
                .statusCode(200)
                .and().extract()
                .body().as(new TypeRef<>() {
                });
        assertThat(comments).isNotNull();
        assertThat(comments.size()).isEqualTo(COMMENT_COUNT_FOR_USER);
    }

    @Test
    @DisplayName("GET Comment Content by Recipe - OK")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe", "migrations/comment"})
    public void givenValidRecipeName_whenGetCommentContentByRecipe_thenExpect200() {
        List<CommentContentDTO> comments = CommentTestClient.getCommentDetailsByRecipe(COMMENT_RECIPE_RECIPE_NAME)
                .then()
                .statusCode(200)
                .and().extract()
                .body().as(new TypeRef<>() {
                });
        assertThat(comments).isNotNull();
        assertThat(comments.size()).isEqualTo(COMMENT_COUNT_FOR_RECIPE);
    }

    @Test
    @DisplayName("POST Comment - OK")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe", "migrations/comment"})
    public void givenValidRequest_whenCreateComment_thenExpect200() {
        CommentContentDTO comment = CommentTestClient.createComment(ADMIN_USERNAME, ADMIN_PASSWORD, CommentFactory.validRequest())
                .then()
                .statusCode(200)
                .and().extract()
                .body().as(CommentContentDTO.class);
        assertThat(comment.getText()).isEqualTo(NON_EXISTING_COMMENT_TEXT);
        assertThat(comment.getUsername()).isEqualTo(ADMIN_USERNAME);
        assertThat(comment.getTitle()).isEqualTo(NON_EXISTING_COMMENT_TITLE);
        assertThat(comment.getRecipeName()).isEqualTo(NON_EXISTING_COMMENT_RECIPE_RECIPE_NAME);
    }

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.CommentParameters#badRequestForCreateAndUpdateComment")
    @DisplayName("POST Comment - Bad Request")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe", "migrations/comment"})
    public void givenInvalidRequest_whenUpdateComment_thenExpect400(CommentRequest request) {
        CommentTestClient.createComment(ADMIN_USERNAME, ADMIN_PASSWORD, request)
                .then()
                .statusCode(400)
                .body("errorKey", equalTo(COMMENT_BAD_REQUEST));
    }

    @Test
    @DisplayName("DELETE Comment - OK")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe", "migrations/comment"})
    public void givenValidId_whenDeleteComment_thenExpect200() {
        CommentTestClient.deleteComment(ADMIN_USERNAME, ADMIN_PASSWORD, COMMENT_ID)
                .then()
                .statusCode(200);
        assertThat(commentRepository.findById(COMMENT_ID)).isNotPresent();
    }

    @Test
    @DisplayName("DELETE Comment - Not Found")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe", "migrations/comment"})
    public void givenInvalidId_whenDeleteComment_thenExpect404() {
        CommentTestClient.deleteComment(ADMIN_USERNAME, ADMIN_PASSWORD, NON_EXISTING_COMMENT_ID)
                .then()
                .statusCode(404)
                .body("errorKey", equalTo(COMMENT_NOT_FOUND));
    }
    @Test
    @DisplayName("PUT Comment - OK")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe", "migrations/comment"})
    public void givenValidIdAndValidRequest_whenUpdateComment_thenExpect200() {
        CommentContentDTO comment = CommentTestClient.updateComment(ADMIN_USERNAME, ADMIN_PASSWORD, COMMENT_ID,
                CommentFactory.paramRequest(UPDATED_COMMENT_TITLE, UPDATED_COMMENT_TEXT, COMMENT_RECIPE_RECIPE_NAME))
                .then()
                .statusCode(200)
                .and().extract()
                .body().as(CommentContentDTO.class);
        assertThat(comment.getRecipeName()).isEqualTo(COMMENT_RECIPE_RECIPE_NAME);
        assertThat(comment.getTitle()).isEqualTo(UPDATED_COMMENT_TITLE);
        assertThat(comment.getText()).isEqualTo(UPDATED_COMMENT_TEXT);
        assertThat(comment.getUsername()).isEqualTo(ADMIN_USERNAME);
    }

    @Test
    @DisplayName("PUT Comment - Not Found")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe", "migrations/comment"})
    public void givenInvalidIdAndValidRequest_whenUpdateComment_thenExpect404() {
        CommentTestClient.updateComment(ADMIN_USERNAME, ADMIN_PASSWORD, NON_EXISTING_COMMENT_ID,
                        CommentFactory.paramRequest(UPDATED_COMMENT_TITLE, UPDATED_COMMENT_TEXT, COMMENT_RECIPE_RECIPE_NAME))
                .then()
                .statusCode(404)
                .body("errorKey", equalTo(COMMENT_NOT_FOUND));
    }

    @ParameterizedTest
    @MethodSource("hr.dice.luka_jurkic.parameters.CommentParameters#badRequestForCreateAndUpdateComment")
    @DisplayName("PUT Comment - Bad Request")
    @FlywayTest(locationsForMigrate = {"migrations/user", "migrations/recipe", "migrations/comment"})
    public void givenValidIdAndInvalidRequest_whenUpdateComment_thenExpect400(CommentRequest request) {
        CommentTestClient.updateComment(ADMIN_USERNAME, ADMIN_PASSWORD, COMMENT_ID, request)
                .then()
                .statusCode(400)
                .body("errorKey", equalTo(COMMENT_BAD_REQUEST));
    }

}
