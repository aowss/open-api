package openapi.model.v310;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Map;

//  TODO: webhooks Map value should be a path or a reference Object
public record OpenApi(@NotNull Version openapi, @NotNull Info info, URI jsonSchemaDialect, List<Server> servers, Map<String, PathItem> paths, Map<String, PathItem> webhooks, Components components, List<SecurityRequirement> security, List<Tag> tags, ExternalDocumentation externalDocs) {
    public OpenApi(@NotNull String openapi, @NotNull Info info, URI jsonSchemaDialect, List<Server> servers, Map<String, PathItem> paths, Map<String, PathItem> webhooks, Components components, List<SecurityRequirement> security, List<Tag> tags, ExternalDocumentation externalDocs) {
        this(new Version(openapi), info, jsonSchemaDialect, servers, paths, webhooks, components, security, tags, externalDocs);
    }
}
