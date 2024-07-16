package hr.dice.luka_jurkic.factory;

import hr.dice.luka_jurkic.rest.request.CommentRequest;

import static hr.dice.luka_jurkic.constants.CommentTestConstants.NON_EXISTING_COMMENT_RECIPE_RECIPE_NAME;
import static hr.dice.luka_jurkic.constants.CommentTestConstants.NON_EXISTING_COMMENT_TEXT;
import static hr.dice.luka_jurkic.constants.CommentTestConstants.NON_EXISTING_COMMENT_TITLE;

public class CommentFactory {
    
    public static CommentRequest validRequest() {
        CommentRequest request = new CommentRequest();
        request.setTitle(NON_EXISTING_COMMENT_TITLE);
        request.setText(NON_EXISTING_COMMENT_TEXT);
        request.setRecipeName(NON_EXISTING_COMMENT_RECIPE_RECIPE_NAME);
        return request;
    }

    public static CommentRequest paramRequest(String title, String text, String recipeName) {
        CommentRequest request = new CommentRequest();
        request.setTitle(title);
        request.setText(text);
        request.setRecipeName(recipeName);
        return request;
    }
}
