
package hr.dice.luka_jurkic.service.exceptions.errors.base;

public class JacksonParsableErrorKey implements ErrorKey {

    private final String errorKey;

    public JacksonParsableErrorKey(String errorKey) {
        this.errorKey = errorKey;
    }

    @Override
    public String getKey() {
        return errorKey;
    }
}
