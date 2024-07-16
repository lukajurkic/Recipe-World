package hr.dice.luka_jurkic.service.exceptions.errors;

import hr.dice.luka_jurkic.service.exceptions.errors.base.ErrorKey;

public enum StepErrorKey implements ErrorKey {
    NOt_FOUND;

    @Override
    public String getKey() {
        return String.format("step__%s", this.name().toLowerCase());
    }
}
