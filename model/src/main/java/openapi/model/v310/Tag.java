package openapi.model.v310;

import javax.validation.constraints.NotNull;

/**
 * Adds metadata to a single tag that is used by the {@link openapi.model.v310.Operation Operation Object}.
 * It is not mandatory to have a Tag Object per tag defined in the Operation Object instances.
 *
 * @param name         The name of the tag.
 * @param description  A description for the tag. CommonMark syntax MAY be used for rich text representation.
 * @param externalDocs Additional external documentation for this tag.
 */
public record Tag(@NotNull String name, String description, ExternalDocumentation externalDocs) {
}
