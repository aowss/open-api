package openapi.model.v310;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * When request bodies or response payloads may be one of a number of different schemas, a discriminator object can be used to aid in serialization, deserialization, and validation.
 * The discriminator is a specific object in a schema which is used to inform the consumer of the document of an alternative schema based on the value associated with it.
 * When using the discriminator, inline schemas will not be considered.
 *
 * @param propertyName The name of the property in the payload that will hold the discriminator value.
 * @param mapping      An object to hold mappings between payload values and schema names or references.
 */
public record Discriminator(@NotNull String propertyName, Map<String, String> mapping) {
}
