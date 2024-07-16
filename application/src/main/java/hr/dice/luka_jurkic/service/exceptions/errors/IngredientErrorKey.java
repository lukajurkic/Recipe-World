package hr.dice.luka_jurkic.service.exceptions.errors;

import hr.dice.luka_jurkic.service.exceptions.errors.base.ErrorKey;

public enum IngredientErrorKey implements ErrorKey {
    NOT_FOUND, ALREADY_EXISTS, IMAGE_LIMIT_REACHED, IN_USE;

    @Override
    public String getKey() {
        return String.format("ingredient__%s", this.name().toLowerCase());
    }
}
