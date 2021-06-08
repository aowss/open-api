package openapi.model.v310;

import java.util.List;
import java.util.Map;

//  TODO: Responses vs List<Response>
public record Operation(List<String> tags, String summary, String description, ExternalDocumentation externalDocs, String operationId, List<Parameter> parameters, RequestBody requestBody, Map<String, Response> responses, Map<String, Callback> callbacks, Boolean deprecated, List<Map<String, List<String>>> security, List<Server> servers) {
}