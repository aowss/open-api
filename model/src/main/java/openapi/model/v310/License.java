package openapi.model.v310;

import javax.validation.constraints.NotNull;
import java.net.URL;

//  TODO: should we add SPDX ( https://spdx.dev/spdx-specification-21-web-version/#h.jxpfx0ykyb60 ) validation for the identifier
record License(@NotNull String name, String identifier, URL url) {
    License {
        if (identifier != null && url != null) throw new IllegalArgumentException("A license can't have both an identifier and a url");
    }

    License(@NotNull String name, String identifier) {
        this(name, identifier, null);
    }

    License(@NotNull String name, URL url) {
        this(name, null, url);
    }
}
