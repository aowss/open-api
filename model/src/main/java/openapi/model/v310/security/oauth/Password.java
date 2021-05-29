package openapi.model.v310.security.oauth;

import javax.validation.constraints.NotNull;
import java.net.URL;
import java.util.Map;

public record Password(@NotNull URL tokenUrl, URL refreshUrl, @NotNull Map<String, String> scopes) implements OAuthFlow {
}
