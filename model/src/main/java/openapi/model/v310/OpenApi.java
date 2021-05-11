package openapi.model.v310;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Map;

record Version(@NotNull int major, @NotNull int minor, int patch) {}

//  TODO: webhooks Map value should be a path or a reference Object
public record OpenApi(@NotNull Version openapi, @NotNull Info info, URI jsonSchemaDialect, List<Server> servers, Map<String, PathItem> paths, Map<String, PathItem> webhooks, Components components, List<SecurityRequirement> security, List<Tag> tags, ExternalDocumentation externalDocs) {
}
