package openapi.model.v310;

import java.util.Map;

public record Callback(Map<String, PathItem> callbackPaths, Map<String, Object> extensions) {}
