package hr.dice.luka_jurkic.service.exceptions.errors;

import hr.dice.luka_jurkic.service.exceptions.errors.base.ErrorKey;

public enum RecipeIngredientErrorKey implements ErrorKey {
    NOT_FOUND;

    @Override
    public String getKey() {
        return String.format("ingredient_in_recipe__%s", this.name().toLowerCase());
    }
}
