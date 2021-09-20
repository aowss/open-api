package openapi.parser.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import openapi.model.v310.security.*;
import openapi.model.v310.security.oauth.*;
import openapi.parser.ParsingException;

import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class SecuritySchemeSerializer extends StdSerializer<SecurityScheme> {

    public SecuritySchemeSerializer() {
        this(null);
    }

    public SecuritySchemeSerializer(Class<SecurityScheme> t) {
        super(t);
    }

    @Override
    public void serialize(SecurityScheme securityScheme, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        //  Didn't manage to make this work using default's serializer even with a bean serializer modifier ( see https://www.baeldung.com/jackson-call-default-serializer-from-custom-serializer )
//        jsonGenerator.writeStringField("type", securityScheme.type().name());
//        defaultSerializer.serialize(securityScheme, jsonGenerator, serializerProvider);
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("type", securityScheme.type().name());
        if (securityScheme.description() != null) jsonGenerator.writeStringField("description", securityScheme.description());
        switch (securityScheme) {
            case ApiKey a -> {
                jsonGenerator.writeStringField("name", a.name());
                jsonGenerator.writeStringField("in", a.in().name());
            }
            case  Http h -> {
                jsonGenerator.writeStringField("scheme", h.scheme().name());
                if (h.bearerFormat() != null) jsonGenerator.writeStringField("bearerFormat", h.bearerFormat());
            }
            case OpenIdConnect c -> jsonGenerator.writeStringField("openIdConnectUrl", c.openIdConnectUrl().toString());
            case OAuth2 o -> {
                jsonGenerator.writeFieldName("flows");
                jsonGenerator.writeStartObject();
                o.flows().entrySet().forEach(serializeOAuthFlow.apply(jsonGenerator));
                jsonGenerator.writeEndObject();
            }
            case MutualTLS tls -> {}
        }
        jsonGenerator.writeEndObject();
    }

    private Function<JsonGenerator, Consumer<Map.Entry<OAuthFlowType, OAuthFlow>>> serializeOAuthFlow = jsonGenerator -> flow -> {
        try {
            jsonGenerator.writeFieldName(flow.getKey().name());
            jsonGenerator.writeStartObject();
            OAuthFlow oAuthFlow = flow.getValue();
            switch (oAuthFlow) {
                case Implicit i -> jsonGenerator.writeStringField("authorizationUrl", i.authorizationUrl().toString());
                case AuthorizationCode ac -> {
                    jsonGenerator.writeStringField("authorizationUrl", ac.authorizationUrl().toString());
                    jsonGenerator.writeStringField("tokenUrl", ac.tokenUrl().toString());
                }
                case Password p -> jsonGenerator.writeStringField("tokenUrl", p.tokenUrl().toString());
                case ClientCredentials cc -> jsonGenerator.writeStringField("tokenUrl", cc.tokenUrl().toString());
            }
            if (oAuthFlow.refreshUrl() != null) jsonGenerator.writeStringField("refreshUrl", oAuthFlow.refreshUrl().toString());
            jsonGenerator.writeObjectField("scopes", oAuthFlow.scopes());
            jsonGenerator.writeEndObject();
        } catch (Exception e) {
            //  TODO: handle exceptions properly
            throw new RuntimeException(new ParsingException("Invalid OAuth 'flow' value: " + flow, e));
        }
    };

}
