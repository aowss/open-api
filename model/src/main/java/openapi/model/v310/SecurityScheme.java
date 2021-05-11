package openapi.model.v310;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.net.URL;
import java.util.Map;

enum Type {
    apiKey, http, mutualTLS, oauth2, openIdConnect
}

public sealed interface SecurityScheme permits ApiKey, Http, MutualTLS, OAuth2, OpenIdConnect {
    Type type();
}

record ApiKey(String description, @NotNull String name, @NotNull Location in) implements SecurityScheme {
    ApiKey {
        if (in == Location.path) throw new IllegalArgumentException("In an 'apiKey' 'securityScheme' the 'in' property can't be 'path'");
    }

    @Override
    public Type type() {
        return Type.apiKey;
    }
}

enum Scheme {
    Basic, Bearer,Digest, HOBA, Mutual, Negotiate, OAuth, SCRAM_SHA_1, SCRAM_SHA_256, vapid
}

record Http(String description, @NotNull Scheme scheme, String bearerFormat) implements SecurityScheme {
    Http {
        if (scheme != Scheme.Bearer && bearerFormat != null) throw new IllegalArgumentException("In an 'http' 'securityScheme' the 'bearerFormat' property can only be specified in the 'scheme' is 'bearer'");
    }

    @Override
    public Type type() {
        return Type.http;
    }
}

record MutualTLS(String description) implements SecurityScheme {
    @Override
    public Type type() {
        return Type.mutualTLS;
    }
}

record OAuth2(String description, @NotEmpty Map<String, OAuthFlow> flows) implements SecurityScheme {
    @Override
    public Type type() {
        return Type.oauth2;
    }
}

record OpenIdConnect(String description, @NotNull URL openIdConnectUrl) implements SecurityScheme {
    @Override
    public Type type() {
        return Type.openIdConnect;
    }
}