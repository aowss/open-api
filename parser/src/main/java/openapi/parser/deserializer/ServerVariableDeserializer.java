package openapi.parser.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import openapi.model.v310.ServerVariable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerVariableDeserializer extends StdDeserializer<ServerVariable> {

    public ServerVariableDeserializer() {
        this(null);
    }

    public ServerVariableDeserializer(Class<ServerVariable> vc) {
        super(vc);
    }

    @Override
    public ServerVariable deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);
        JsonNode enumsNode = node.get("enum");
        List<String> enums = new ArrayList<>();
        if (enumsNode == null) {
            enums = null;
        } else if (enumsNode.isArray()) {
            for (JsonNode value : enumsNode) {
                enums.add(value.asText());
            }
        } else {
            throw new IllegalArgumentException("The 'enum' field must be an array");
        }
        String defaultValue = node.get("default") != null ? node.get("default").asText() : null;
        String description = node.get("description") != null ? node.get("description").asText() : null;
        return new ServerVariable(enums, defaultValue, description);
    }

}