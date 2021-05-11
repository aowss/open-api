package openapi.model.v310;

import javax.validation.constraints.NotNull;

public record Tag(@NotNull String name, String description, ExternalDocumentation externalDocs) {
}
