package openapi.parser.deserializer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import openapi.model.v310.security.*;
import openapi.model.v310.security.oauth.OAuthFlow;

import java.io.IOException;

public class SecuritySchemeDeserializer extends StdDeserializer<SecurityScheme> {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(OAuthFlow.class, new OAuthFlowDeserializer());
        module.addDeserializer(Scheme.class, new SchemeDeserializer());
        objectMapper.registerModule(module);
    }

    public SecuritySchemeDeserializer() {
        this(null);
    }

    public SecuritySchemeDeserializer(Class<SecurityScheme> vc) {
        super(vc);
    }

    @Override
    public SecurityScheme deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);
        Type securityType = Type.valueOf(node.get("type").asText());
        Class<? extends SecurityScheme> type = switch (securityType) {
            case apiKey -> ApiKey.class;
            case http -> Http.class;
            case mutualTLS -> MutualTLS.class;
            case oauth2 -> OAuth2.class;
            case openIdConnect -> OpenIdConnect.class;
        };
        return objectMapper.convertValue(node, type);
    }

}
