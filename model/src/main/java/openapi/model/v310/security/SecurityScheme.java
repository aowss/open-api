package openapi.model.v310.security;

public sealed interface SecurityScheme permits ApiKey, Http, MutualTLS, OAuth2, OpenIdConnect {
    Type type();
    String description();
}



