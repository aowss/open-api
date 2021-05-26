package openapi.model.v310;

import javax.validation.constraints.NotNull;
import java.util.Map;

public record Parameter(@NotNull String name, @NotNull Location in, String description, Boolean required, Boolean depracted, Boolean allowEmptyValue, Style style, Boolean explode, Boolean allowReserved, Schema schema, Object example, Map<String, Example> examples, Map<String, MediaType> content) {
    public Parameter {
        if (schema != null && content != null) throw new IllegalArgumentException("A 'parameter' Object can't have both a 'schema' and a 'content' field");
        if (in == Location.path && (required != null && required == false)) throw new IllegalArgumentException("A 'path' parameter must be required");
        if (allowEmptyValue != null && in != Location.query) throw new IllegalArgumentException("The 'allowEmptyValueOnly' field is only valid for 'query' parameters");
        if (style == null) {
            style = switch (in) {
                case query, cookie -> Style.form;
                case path, header -> Style.simple;
            };
        }
        if (explode == null) {
            explode = switch (style) {
                case form -> true;
                default -> false;
            };
        }
        if (content != null && content.size() != 1) throw new IllegalArgumentException("The 'content' field map must only contain one entry");
    }
    //  TODO: deal with Boolean default values
    //  TODO: should we create 2 constructors ?
}
