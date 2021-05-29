package openapi.model.v310.security.oauth;

public sealed interface OAuthFlow permits Implicit, Password, ClientCredentials, AuthorizationCode {
}
