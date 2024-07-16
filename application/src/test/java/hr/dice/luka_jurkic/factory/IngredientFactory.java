package hr.dice.luka_jurkic.factory;

import hr.dice.luka_jurkic.persistence.entity.IngredientCategory;
import hr.dice.luka_jurkic.rest.request.IngredientRequest;

import static hr.dice.luka_jurkic.constants.IngredientTestConstants.NON_EXISTING_INGREDIENT_CATEGORY;
import static hr.dice.luka_jurkic.constants.IngredientTestConstants.NON_EXISTING_INGREDIENT_NAME;

public class IngredientFactory {

    public static IngredientRequest validRequest() {
        IngredientRequest request = new IngredientRequest();
        request.setName(NON_EXISTING_INGREDIENT_NAME);
        request.setCategory(String.valueOf(NON_EXISTING_INGREDIENT_CATEGORY));
        return request;
    }

    public static IngredientRequest paramRequest(String name, IngredientCategory category) {
        IngredientRequest request = new IngredientRequest();
        request.setName(name);
        request.setCategory(String.valueOf(category));
        return request;
    }
}
