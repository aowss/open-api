package openapi.model.v310;

import java.util.Map;

public record MediaType(Schema schema, Object example, Map<String, Example> examples, Map<String, Encoding> encoding) {}
