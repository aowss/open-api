package openapi.model.v310.security;

import javax.validation.constraints.NotNull;
import java.net.URL;

public record OpenIdConnect(String description, @NotNull URL openIdConnectUrl) implements SecurityScheme {
    @Override
    public Type type() {
        return Type.openIdConnect;
    }
}