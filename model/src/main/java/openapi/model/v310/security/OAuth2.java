package openapi.model.v310.security;

import openapi.model.v310.security.oauth.OAuthFlow;
import openapi.model.v310.security.oauth.OAuthFlowType;

import javax.validation.constraints.NotEmpty;
import java.util.Map;

public record OAuth2(String description, @NotEmpty Map<OAuthFlowType, OAuthFlow> flows) implements SecurityScheme {
    @Override
    public Type type() {
        return Type.oauth2;
    }
}

