package openapi.model.v310;

import javax.validation.constraints.NotNull;
import java.util.List;

//  Can't use @NotEmpty because it internally calls @NotNull and enums is nullable

/**
 * An object representing a Server Variable for server URL template substitution.
 *
 * @param enums An enumeration of string values to be used if the substitution options are from a limited set. The array MUST NOT be empty.
 *              The field name is {@code enum}.
 * @param defaultValue The default value to use for substitution, which SHALL be sent if an alternate value is not supplied. Note this behavior is different than the Schema Object's treatment of default values, because in those cases parameter values are optional. If the enum is defined, the value MUST exist in the enum's values.
 *                     The field name is {@code default}.
 * @param description An optional description for the server variable. CommonMark syntax MAY be used for rich text representation.
 * @throws IllegalArgumentException if the {@code enums} is empty;
 *                                  if the {@code defaultValue} is not one of the {@code enums} value.
 */
public record ServerVariable(List<String> enums, @NotNull String defaultValue, String description) {
    public ServerVariable {
        if (enums != null && enums.size() == 0) throw new IllegalArgumentException("The 'enum' array can't be empty");
        if (enums != null && !enums.contains(defaultValue)) throw new IllegalArgumentException("The 'default' value must be one of the 'enum' values");
    }
}
