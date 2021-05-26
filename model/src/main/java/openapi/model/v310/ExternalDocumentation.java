package openapi.model.v310;

import javax.validation.constraints.NotNull;
import java.net.URL;

/**
 * Allows referencing an external resource for extended documentation.
 *
 * @param description A description of the target documentation. CommonMark syntax MAY be used for rich text representation.
 * @param url The URL for the target documentation. This MUST be in the form of a URL.
 */
public record ExternalDocumentation(String description, @NotNull URL url) {}
