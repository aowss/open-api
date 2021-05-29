package openapi.model.v310;

import openapi.model.v310.security.SecurityScheme;

import java.util.Map;

public record Components(Map<String, Schema> schemas, Map<String, Response> responses, Map<String, Parameter> parameters, Map<String, Example> examples, Map<String, RequestBody> requestBodies, Map<String, Header> headers, Map<String, SecurityScheme> securitySchemes, Map<String, Link> links, Map<String, Callback> callbacks, Map<String, PathItem> pathItems) {
}
