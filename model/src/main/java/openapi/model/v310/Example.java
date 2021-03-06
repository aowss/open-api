package openapi.model.v310;

import java.net.URI;

/**
 * @param summary       Short description for the example.
 * @param description   Long description for the example. CommonMark syntax MAY be used for rich text representation.
 * @param value         Embedded literal example.
 *                      The {@code value} field and {@code externalValue} field are mutually exclusive.
 *                      To represent examples of media types that cannot be naturally represented in JSON or YAML, use a string value to contain the example, escaping where necessary.
 * @param externalValue A URI that points to the literal example.
 *                      This provides the capability to reference examples that cannot easily be included in JSON or YAML documents.
 *                      The {@code value} field and {@code externalValue} field are mutually exclusive.
 *                      See the rules for resolving Relative References.
 */
public record Example(String summary, String description, Object value, URI externalValue) {
    public Example {
        if (value != null && externalValue != null) throw new IllegalArgumentException("An 'example' Object can't have both a 'value' and an 'externalValue' field");
    }
}
