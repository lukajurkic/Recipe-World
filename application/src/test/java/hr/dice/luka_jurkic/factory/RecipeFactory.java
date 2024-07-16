package hr.dice.luka_jurkic.factory;

import hr.dice.luka_jurkic.rest.request.RecipeRequest;

import static hr.dice.luka_jurkic.constants.RecipeTestConstants.NON_EXISTING_RECIPE_NAME;

public class RecipeFactory {

    public static RecipeRequest validRequest() {
        RecipeRequest request = new RecipeRequest();
        request.setName(NON_EXISTING_RECIPE_NAME);
        return request;
    }

    public static RecipeRequest paramRequest(String name) {
        RecipeRequest request = new RecipeRequest();
        request.setName(name);
        return request;
    }
}
