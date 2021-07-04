package openapi.parser.deserializer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import openapi.model.v310.security.oauth.*;

import java.io.IOException;

public class OAuthFlowDeserializer extends StdDeserializer<OAuthFlow> {

    private static ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
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
