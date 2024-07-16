package hr.dice.luka_jurkic.service.exceptions.errors;

import hr.dice.luka_jurkic.service.exceptions.errors.base.ErrorKey;

public enum ImageErrorKey implements ErrorKey {
    NOT_FOUND, SIZE_TOO_LARGE, WRONG_CONTENT_TYPE, NOT_SUPPORTED_FORMAT;

    @Override
    public String getKey() {
        return String.format("image__%s", this.name().toLowerCase());
    }
}
