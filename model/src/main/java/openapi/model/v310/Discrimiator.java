package openapi.model.v310;

import javax.validation.constraints.NotNull;
import java.util.Map;

public record Discrimiator(@NotNull String propertyName, Map<String, String> mapping) {
}
