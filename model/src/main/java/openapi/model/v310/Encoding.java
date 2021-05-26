package openapi.model.v310;

import java.util.Map;

/**
 * A single encoding definition applied to a single schema property.
 *
 * @param contentType   The Content-Type for encoding a specific property.
 *                      Default value depends on the property type: for object - application/json; for array – the default is defined based on the inner type; for all other cases the default is application/octet-stream.
 *                      The value can be a specific media type (e.g. application/json), a wildcard media type (e.g. image/*), or a comma-separated list of the two types.
 * @param headers
 * @param style
 * @param explode
 * @param allowReserved
 */
public record Encoding(String contentType, Map<String, Header> headers, Style style, Boolean explode, Boolean allowReserved) {
}
