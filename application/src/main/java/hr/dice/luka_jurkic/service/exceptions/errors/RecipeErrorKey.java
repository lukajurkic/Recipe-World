
package hr.dice.luka_jurkic.service.exceptions.errors;

import hr.dice.luka_jurkic.service.exceptions.errors.base.ErrorKey;

public enum RecipeErrorKey implements ErrorKey {
    NOT_FOUND, ALREADY_EXISTS, ACCESS_DENIED, IMAGE_LIMIT_REACHED;

    @Override
    public String getKey() {
        return String.format("recipe__%s", this.name().toLowerCase());
    }
}
