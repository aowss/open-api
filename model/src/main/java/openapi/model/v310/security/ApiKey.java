package openapi.model.v310.security;

import openapi.model.v310.Location;

import javax.validation.constraints.NotNull;

public record ApiKey(String description, @NotNull String name, @NotNull Location in) implements SecurityScheme {
    public ApiKey {
        if (in == Location.path) throw new IllegalArgumentException("In an 'apiKey' 'securityScheme' the 'in' property can't be 'path'");
    }

    @Override
    public Type type() {
        return Type.apiKey;
    }
}
