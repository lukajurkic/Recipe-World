package hr.dice.luka_jurkic.parameters;

import hr.dice.luka_jurkic.factory.CommentFactory;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static hr.dice.luka_jurkic.constants.CommentTestConstants.NON_EXISTING_COMMENT_RECIPE_RECIPE_NAME;
import static hr.dice.luka_jurkic.constants.CommentTestConstants.NON_EXISTING_COMMENT_TITLE;

public class CommentParameters {

    public static Stream<Arguments> badRequestForCreateAndUpdateComment() {
        return Stream.of(
            Arguments.of(CommentFactory.paramRequest(null, NON_EXISTING_COMMENT_TITLE, NON_EXISTING_COMMENT_RECIPE_RECIPE_NAME)),
                Arguments.of(CommentFactory.paramRequest("", NON_EXISTING_COMMENT_TITLE, NON_EXISTING_COMMENT_RECIPE_RECIPE_NAME)),
                Arguments.of(CommentFactory.paramRequest(NON_EXISTING_COMMENT_TITLE, null, NON_EXISTING_COMMENT_RECIPE_RECIPE_NAME)),
                Arguments.of(CommentFactory.paramRequest(NON_EXISTING_COMMENT_TITLE, ".".repeat(1025), NON_EXISTING_COMMENT_RECIPE_RECIPE_NAME)),
                Arguments.of(CommentFactory.paramRequest(NON_EXISTING_COMMENT_TITLE, null, NON_EXISTING_COMMENT_RECIPE_RECIPE_NAME)),
                Arguments.of(CommentFactory.paramRequest(NON_EXISTING_COMMENT_TITLE, NON_EXISTING_COMMENT_TITLE, null))

        );
    }
}
