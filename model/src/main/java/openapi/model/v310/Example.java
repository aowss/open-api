package openapi.model.v310;

import java.net.URI;

public record Example(String summary, String description, Object value, URI externalValue) {
    public Example {
        if (value != null && externalValue != null) throw new IllegalArgumentException("An 'example' Object can't have both a 'value' and an 'externalValue' field");
    }
}
