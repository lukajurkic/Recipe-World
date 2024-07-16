package hr.dice.luka_jurkic.service.exceptions.errors;

import hr.dice.luka_jurkic.service.exceptions.errors.base.ErrorKey;

public enum CommentErrorKey implements ErrorKey {
    NOT_FOUND;

    @Override
    public String getKey() {
        return String.format("comment__%s", this.name().toLowerCase());
    }
}
