package openapi.model.v310;

import javax.validation.constraints.NotNull;
import java.util.Map;

public record RequestBody(String description, @NotNull Map<String, MediaType> content, Boolean required) {
}
