package openapi.model.v310;

import javax.validation.constraints.NotNull;
import java.net.URL;

/**
 * License information for the exposed API.
 *
 * @param name The license name used for the API.
 * @param identifier An SPDX license expression for the API. The identifier field is mutually exclusive of the url field.
 * @param url A URL to the license used for the API. This MUST be in the form of a URL. The url field is mutually exclusive of the identifier field.
 */
public record License(@NotNull String name, String identifier, URL url) {

    /**
     * Creates an instance of a {@code License} record class.
     *
     * @param name The license name used for the API.
     * @param identifier An SPDX license expression for the API. The identifier field is mutually exclusive of the url field.
     * @param url A URL to the license used for the API. This MUST be in the form of a URL. The url field is mutually exclusive of the identifier field.
     * @throws IllegalArgumentException if both {@code identifier} and {@code url} are specified
     */
    public License {
        if (identifier != null && url != null) throw new IllegalArgumentException("A license can't have both an identifier and a url");
        //  TODO: should we add SPDX ( https://spdx.dev/spdx-specification-21-web-version/#h.jxpfx0ykyb60 ) validation for the identifier
    }

    /**
     * Creates an instance of a {@code License} record class.
     *
     * @param name The license name used for the API.
     * @param identifier An SPDX license expression for the API. The identifier field is mutually exclusive of the url field.
     */
    public License(@NotNull String name, String identifier) {
        this(name, identifier, null);
    }

    /**
     * Creates an instance of a {@code License} record class.
     *
     * @param name The license name used for the API.
     * @param url A URL to the license used for the API. This MUST be in the form of a URL. The url field is mutually exclusive of the identifier field.
     */
    public License(@NotNull String name, URL url) {
        this(name, null, url);
    }
}
