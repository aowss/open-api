package openapi.model.v310;

import javax.validation.constraints.NotNull;
import java.util.List;

//  Can't use @NotEmpty because it internally calls @NotNull and enums is nullable
public record ServerVariable(List<String> enums, @NotNull String defaultValue, String description) {
    public ServerVariable {
        if (enums != null && enums.size() == 0) throw new IllegalArgumentException("The 'enum' array can't be empty");
        if (enums != null && !enums.contains(defaultValue)) throw new IllegalArgumentException("The 'default' value must be one of the 'enum' values");
    }
}
