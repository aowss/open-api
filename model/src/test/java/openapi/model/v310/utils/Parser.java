package openapi.model.v310.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import openapi.model.v310.ServerVariable;
import openapi.model.v310.ServerVariableTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    //  Didn't manage to make the mixin work with records
    public static class ServerVariableDeserializer extends StdDeserializer<ServerVariable> {

        public ServerVariableDeserializer() {
            this(null);
        }

        public ServerVariableDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public ServerVariable deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException {
            JsonNode node = parser.getCodec().readTree(parser);
//            try {
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
//            } catch (Exception e) {
//                //  TODO: check what needs to be done
//                e.printStackTrace();
//                return null;
//            }
        }
    }

    public static final ObjectMapper jsonMapper = new ObjectMapper();
    public static final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

    static {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(ServerVariable.class, new ServerVariableDeserializer());
        jsonMapper.registerModule(module);
        yamlMapper.registerModule(module);
    }
}
