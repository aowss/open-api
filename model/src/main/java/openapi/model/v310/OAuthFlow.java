package openapi.model.v310;

import javax.validation.constraints.NotNull;
import java.net.URL;
import java.util.Map;

public sealed interface OAuthFlow permits Implicit, Password, ClientCredentials, AuthorizationCode {
}

record Implicit(@NotNull URL authorizationUrl, URL refreshUrl, @NotNull Map<String, String> scopes) implements OAuthFlow {}
record Password(@NotNull URL tokenUrl, URL refreshUrl, @NotNull Map<String, String> scopes) implements OAuthFlow {}
record ClientCredentials(@NotNull URL tokenUrl, URL refreshUrl, @NotNull Map<String, String> scopes) implements OAuthFlow {}
record AuthorizationCode(@NotNull URL authorizationUrl, @NotNull URL tokenUrl, URL refreshUrl, @NotNull Map<String, String> scopes) implements OAuthFlow {}
