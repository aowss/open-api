package openapi.model.v310.security;

import javax.validation.constraints.NotNull;

public record Http(String description, @NotNull Scheme scheme, String bearerFormat) implements SecurityScheme {
    public Http {
        if (scheme != Scheme.Bearer && bearerFormat != null) throw new IllegalArgumentException("In an 'http' 'securityScheme' the 'bearerFormat' property can only be specified if the 'scheme' is 'bearer'");
    }

    public Http(String description, @NotNull String scheme, String bearerFormat) {
        this(description, Scheme.from(scheme), bearerFormat);
    }

    @Override
    public Type type() {
        return Type.http;
    }
}

