package openapi.model.v310;

import javax.validation.constraints.NotNull;
import java.net.URL;

/**
 * The object provides metadata about the API. The metadata MAY be used by the clients if needed, and MAY be presented in editing or documentation generation tools for convenience.
 *
 * @param title          The title of the API.
 * @param summary        A short summary of the API.
 * @param description    A description of the API. CommonMark syntax MAY be used for rich text representation.
 * @param termsOfService A URL to the Terms of Service for the API.
 * @param contact        The contact information for the exposed API.
 * @param license        The license information for the exposed API.
 * @param version        The version of the OpenAPI document (which is distinct from the OpenAPI Specification version or the API implementation version).
 */
public record Info(@NotNull String title, String summary, String description, URL termsOfService, Contact contact, License license, @NotNull String version) {

    public Info(@NotNull String title, @NotNull String version) {
        this(title, null, null, null, null, null, version);
    }

}
