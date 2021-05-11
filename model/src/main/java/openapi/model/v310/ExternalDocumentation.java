package openapi.model.v310;

import javax.validation.constraints.NotNull;
import java.net.URL;

public record ExternalDocumentation(String description, @NotNull URL url) {}
