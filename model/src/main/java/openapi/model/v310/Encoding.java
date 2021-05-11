package openapi.model.v310;

import java.util.Map;

public record Encoding(String contentType, Map<String, Header> headers, Style style, Boolean explode, Boolean allowReserved) {
}
