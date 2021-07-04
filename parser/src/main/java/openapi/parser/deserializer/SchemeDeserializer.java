package openapi.parser.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import openapi.model.v310.security.Scheme;

import java.io.IOException;

public class SchemeDeserializer extends StdDeserializer<Scheme> {

    public SchemeDeserializer() {
        this(null);
    }

    public SchemeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Scheme deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);
        String scheme = node.asText();
        return Scheme.from(scheme);
    }

}
