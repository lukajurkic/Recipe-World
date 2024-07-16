package hr.dice.luka_jurkic.service.exceptions.errors;

import hr.dice.luka_jurkic.service.exceptions.errors.base.ErrorKey;

public enum UserErrorKey implements ErrorKey {
    NOT_FOUND, ALREADY_EXISTS, IS_LAST_ADMIN;

    @Override
    public String getKey() {
        return String.format("user__%s", this.name().toLowerCase());
    }
}
