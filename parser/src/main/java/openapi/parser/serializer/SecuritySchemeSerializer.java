package openapi.parser.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import openapi.model.v310.security.*;
import openapi.model.v310.security.oauth.*;
import openapi.parser.ParsingException;

import java.io.IOException;

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
        //  TODO: Change that to switch when java 17 is here
        if (securityScheme instanceof ApiKey a) {
            jsonGenerator.writeStringField("name", a.name());
            jsonGenerator.writeStringField("in", a.in().name());
        } else if (securityScheme instanceof Http h) {
            jsonGenerator.writeStringField("scheme", h.scheme().name());
            if (h.bearerFormat() != null) jsonGenerator.writeStringField("bearerFormat", h.bearerFormat());
        } else if (securityScheme instanceof OpenIdConnect c) {
            jsonGenerator.writeStringField("openIdConnectUrl", c.openIdConnectUrl().toString());
        } else if (securityScheme instanceof OAuth2 o) {
            jsonGenerator.writeFieldName("flows");
            jsonGenerator.writeStartObject();
            o.flows().entrySet().forEach(flow -> {
                try {
                    jsonGenerator.writeFieldName(flow.getKey().name());
                    jsonGenerator.writeStartObject();
                    OAuthFlow oAuthFlow = flow.getValue();
                    if (oAuthFlow instanceof Implicit i) jsonGenerator.writeStringField("authorizationUrl", i.authorizationUrl().toString());
                    if (oAuthFlow instanceof AuthorizationCode ac) {
                        jsonGenerator.writeStringField("authorizationUrl", ac.authorizationUrl().toString());
                        jsonGenerator.writeStringField("tokenUrl", ac.tokenUrl().toString());
                    }
                    if (oAuthFlow instanceof Password p) jsonGenerator.writeStringField("tokenUrl", p.tokenUrl().toString());
                    if (oAuthFlow instanceof ClientCredentials cc) jsonGenerator.writeStringField("tokenUrl", cc.tokenUrl().toString());
                    if (oAuthFlow.refreshUrl() != null) jsonGenerator.writeStringField("refreshUrl", oAuthFlow.refreshUrl().toString());
                    jsonGenerator.writeObjectField("scopes", oAuthFlow.scopes());
                    jsonGenerator.writeEndObject();
                } catch (Exception e) {
                    //  TODO: handle exceptions properly
                    throw new RuntimeException(new ParsingException("Invalid OAuth 'flow' value: " + flow, e));
                }
            });
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndObject();
    }

}
