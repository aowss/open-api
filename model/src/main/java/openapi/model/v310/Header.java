package openapi.model.v310;

import java.util.Map;

public record Header(String description, Boolean required, Boolean deprecated, Style style, Boolean explode, Boolean allowReserved, Schema schema, Object example, Map<String, Example> examples, Map<String, MediaType> content) {
    public Header {
        if (schema != null && content != null) throw new IllegalArgumentException("A 'header' Object can't have both a 'schema' and a 'content' field");
        if (style == null) style = Style.simple;
        if (explode == null) {
            explode = switch (style) {
                case form -> true;
                default -> false;
            };
        }
        if (content != null && content.size() != 1) throw new IllegalArgumentException("The 'content' field map must only contain one entry");
    }
}
