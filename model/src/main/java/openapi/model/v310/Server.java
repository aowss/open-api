package openapi.model.v310;

import javax.validation.constraints.NotNull;
import java.net.URL;
import java.util.Map;

//  TODO: should we use a URL for url knowing that it should support Server Variables and may be relative
public record Server(@NotNull URL url, String description, Map<String, ServerVariable> variables) {
}
