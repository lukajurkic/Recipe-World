package hr.dice.luka_jurkic.service.exceptions.errors.base;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Error Key interface that only contain getKey method
 */
@FunctionalInterface
@JsonDeserialize(using = ErrorKeyDeserializer.class)
public interface ErrorKey {

    String getKey();
}

