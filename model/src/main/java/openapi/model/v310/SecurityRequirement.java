package openapi.model.v310;

import java.util.List;
import java.util.Map;

public record SecurityRequirement(Map<String, List<String>> requirements) {
}
