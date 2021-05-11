package openapi.model.v310;

import javax.validation.constraints.NotNull;
import java.net.URI;

public record Reference(@NotNull URI ref, String summary, String description) {
}
