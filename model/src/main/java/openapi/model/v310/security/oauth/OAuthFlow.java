package openapi.model.v310.security.oauth;

import java.net.URL;
import java.util.Map;

public sealed interface OAuthFlow permits Implicit, Password, ClientCredentials, AuthorizationCode {
    URL refreshUrl();
    Map<String, String> scopes();
}
