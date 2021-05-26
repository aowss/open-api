package openapi.model.v310;

//  TODO: Validate 'The discriminator object is legal only when using one of the composite keywords oneOf, anyOf, allOf.'
public record Schema(Discriminator discriminator, XML xml, ExternalDocumentation externalDocs, Object example) {
}
