package openapi.model.v310;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * An object representing a Server Variable for server URL template substitution.
 *
 * @param enums        An enumeration of string values to be used if the substitution options are from a limited set. The array MUST NOT be empty. <br/>
 *                     The field name is {@code enum}.
 * @param defaultValue The default value to use for substitution, which SHALL be sent if an alternate value is not supplied. <br/>
 *                     Note this behavior is different than the {@link openapi.model.v310.Schema Schema Object}'s treatment of default values, because in those cases parameter values are optional. <br/>
 *                     If the enum is defined, the value MUST exist in the enum's values. <br/>
 *                     The field name is {@code default}.
 * @param description  An optional description for the server variable. CommonMark syntax MAY be used for rich text representation.
 */
//  Can't use @NotEmpty for enums because it internally calls @NotNull and enums is nullable
public record ServerVariable(List<String> enums, @NotNull String defaultValue, String description) {

    /**
     * Creates an instance of a {@code ServerVariable} record class.
     *
     * @param enums        An enumeration of string values to be used if the substitution options are from a limited set. The array MUST NOT be empty. <br/>
     *                     The field name is {@code enum}.
     * @param defaultValue The default value to use for substitution, which SHALL be sent if an alternate value is not supplied. <br/>
     *                     Note this behavior is different than the {@link openapi.model.v310.Schema Schema Object}'s treatment of default values, because in those cases parameter values are optional. <br/>
     *                     If the enum is defined, the value MUST exist in the enum's values. <br/>
     *                     The field name is {@code default}.
     * @param description  An optional description for the server variable. CommonMark syntax MAY be used for rich text representation.
     * @throws IllegalArgumentException if the {@code enums} is empty; <br/>
     *                                  if the {@code defaultValue} is not one of the {@code enums} value.
     */
    public ServerVariable {
        if (enums != null && enums.size() == 0) throw new IllegalArgumentException("The 'enum' array can't be empty");
        if (enums != null && !enums.contains(defaultValue)) throw new IllegalArgumentException("The 'default' value must be one of the 'enum' values");
    }

}
