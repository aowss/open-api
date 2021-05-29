package openapi.parser.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import openapi.model.v310.Version;

import java.io.IOException;

public class VersionDeserializer extends StdDeserializer<Version> {

    public VersionDeserializer() {
        this(null);
    }

    public VersionDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Version deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);
        String version = node.asText();
        return new Version(Integer.parseInt(version.split("\\.")[0]), Integer.parseInt(version.split("\\.")[1]), Integer.parseInt(version.split("\\.")[2]));
    }

}
