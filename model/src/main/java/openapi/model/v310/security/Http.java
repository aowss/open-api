package openapi.model.v310.security;

import javax.validation.constraints.NotNull;

enum Scheme {
    Basic, Bearer,Digest, HOBA, Mutual, Negotiate, OAuth, SCRAM_SHA_1, SCRAM_SHA_256, vapid
}

public record Http(String description, @NotNull Scheme scheme, String bearerFormat) implements SecurityScheme {
    public Http {
        if (scheme != Scheme.Bearer && bearerFormat != null) throw new IllegalArgumentException("In an 'http' 'securityScheme' the 'bearerFormat' property can only be specified in the 'scheme' is 'bearer'");
    }

    @Override
    public Type type() {
        return Type.http;
    }
}

