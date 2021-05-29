package openapi.model.v310;

import javax.validation.constraints.NotNull;
import java.util.Map;

public record Response(@NotNull String description, Map<String, Header> headers, Map<String, MediaType> content, Map<String, Link> links) {
}
