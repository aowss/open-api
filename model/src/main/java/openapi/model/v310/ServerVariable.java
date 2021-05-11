package openapi.model.v310;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

//  TODO: check why the field name is enum and not enums in the spec; we can't use enum because it's a reserved word
public record ServerVariable(@NotEmpty List<String> enums, @NotNull String defaultValue, String description) {
}
