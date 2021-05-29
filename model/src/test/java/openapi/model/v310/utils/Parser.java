package openapi.model.v310.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import openapi.model.v310.*;
import openapi.model.v310.security.*;
import openapi.model.v310.security.oauth.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toMap;

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

    public static class VersionDeserializer extends StdDeserializer<Version> {

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

    public static class SecurityRequirementDeserializer extends StdDeserializer<SecurityRequirement> {

        public SecurityRequirementDeserializer() {
            this(null);
        }

        public SecurityRequirementDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public SecurityRequirement deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException {
            JsonNode node = parser.getCodec().readTree(parser);
            Iterator<Map.Entry<String, JsonNode>> iterator = ((ObjectNode)node).fields();
            Iterable<Map.Entry<String, JsonNode>> iterable = () -> iterator;
            Map<String, List<String>> requirements = StreamSupport.stream(iterable.spliterator(), false)
                .collect(toMap(
                    entry -> entry.getKey(),
                    entry -> {
                        JsonNode value = entry.getValue();
                        List<String> values = new ArrayList<>();
                        if (value == null) {
                            values = null;
                        } else if (value.isArray()) {
                            for (JsonNode v : value) {
                                values.add(v.asText());
                            }
                        } else {
                            throw new IllegalArgumentException("The 'security' field must be an array");
                        }
                        return values;
                    }
                ));
            return new SecurityRequirement(requirements);
        }
    }

    public static class SecuritySchemeDeserializer extends StdDeserializer<SecurityScheme> {

        private static ObjectMapper objectMapper = new ObjectMapper();
        static {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            SimpleModule module = new SimpleModule();
            module.addDeserializer(OAuthFlow.class, new OAuthFlowDeserializer());
            objectMapper.registerModule(module);
        }

        public SecuritySchemeDeserializer() {
            this(null);
        }

        public SecuritySchemeDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public SecurityScheme deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException {
            JsonNode node = parser.getCodec().readTree(parser);
            Type securityType = Type.valueOf(((ObjectNode)node).get("type").asText());
            Class<? extends SecurityScheme> type = switch (securityType) {
                case apiKey -> ApiKey.class;
                case http -> Http.class;
                case mutualTLS -> MutualTLS.class;
                case oauth2 -> OAuth2.class;
                case openIdConnect -> OpenIdConnect.class;
            };
            SecurityScheme scheme = objectMapper.convertValue(node, type);
            return scheme;
        }
    }

    public static class OAuthFlowDeserializer extends StdDeserializer<OAuthFlow> {

        private static ObjectMapper objectMapper = new ObjectMapper();
        static {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }

        public OAuthFlowDeserializer() {
            this(null);
        }

        public OAuthFlowDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public OAuthFlow deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException {
            OAuthFlowType flowType = OAuthFlowType.valueOf(parser.getParsingContext().getParent().getCurrentName());
            JsonNode node = parser.getCodec().readTree(parser);
            Class<? extends OAuthFlow> type = switch (flowType) {
                case implicit -> Implicit.class;
                case password -> Password.class;
                case clientCredentials -> ClientCredentials.class;
                case authorizationCode -> AuthorizationCode.class;
            };
            OAuthFlow flow = objectMapper.convertValue(node, type);
            return flow;
        }
    }

    public static final ObjectMapper jsonMapper = new ObjectMapper();
    public static final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

    static {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(ServerVariable.class, new ServerVariableDeserializer());
        module.addDeserializer(Version.class, new VersionDeserializer());
        module.addDeserializer(SecurityRequirement.class, new SecurityRequirementDeserializer());
        module.addDeserializer(SecurityScheme.class, new SecuritySchemeDeserializer());
        module.addDeserializer(OAuthFlow.class, new OAuthFlowDeserializer());
        jsonMapper.registerModule(module);
        yamlMapper.registerModule(module);
    }
}
