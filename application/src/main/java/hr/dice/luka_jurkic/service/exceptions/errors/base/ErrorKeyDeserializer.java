package hr.dice.luka_jurkic.service.exceptions.errors.base;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class ErrorKeyDeserializer extends JsonDeserializer<JacksonParsableErrorKey> {

    @Override
    public JacksonParsableErrorKey deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        return new JacksonParsableErrorKey(jsonParser.getValueAsString());
    }
}
