package openapi.model.v310;

import openapi.parser.MissingValueException;
import openapi.parser.Parser;
import openapi.parser.ParsingException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Discriminator Object : https://github.com/OAI/OpenAPI-Specification/blob/main/versions/3.1.0.md#discriminator-object")
public class DiscriminatorTest {

    static String allFieldsJSON = "/Discriminator/all-fields.json";
    static String allFieldsYAML = "/Discriminator/all-fields.yaml";
    static String mandatoryFields = "/Discriminator/mandatory-fields.json";
    static String missingFields = "/Discriminator/missing-fields.json";

    @Test
    @Tag("JSON")
    @DisplayName("All fields with URL [JSON]")
    public void allFieldsURLJSON() throws IOException, ParsingException {
        Discriminator discriminator = Parser.parseJSON(getClass().getResource(allFieldsJSON), Discriminator.class);
        validateAllFieldsURL(discriminator);
    }

    @Test
    @Tag("YAML")
    @DisplayName("All fields with URL [YAML]")
    public void allFieldsURLYAML() throws IOException, ParsingException {
        Discriminator discriminator = Parser.parseYAML(getClass().getResource(allFieldsYAML), Discriminator.class);
        validateAllFieldsURL(discriminator);
    }

    @Test
    @Tag("JSON")
    @DisplayName("Mandatory fields")
    public void mandatoryFields() throws IOException, ParsingException {
        Discriminator discriminator = Parser.parseJSON(getClass().getResource(mandatoryFields), Discriminator.class);
        validateMandatoryFields(discriminator);
    }

    @Test
    @Tag("JSON")
    @DisplayName("Missing Mandatory fields")
    public void missingFields() {
        MissingValueException exception = assertThrows(MissingValueException.class, () -> Parser.parseJSON(getClass().getResource(missingFields), Discriminator.class));
        assertThat(exception.getPaths(), is(List.of("propertyName")));
        assertThat(exception.getMessage(), is("The value(s) at location(s) 'propertyName' is/are required"));
    }

    public void validateAllFieldsURL(Discriminator discriminator) {
        assertThat(discriminator.propertyName(), is("petType"));
        assertThat(discriminator.mapping(), is(Map.of("dog", "#/components/schemas/Dog", "monster", "https://gigantic-server.com/schemas/Monster/schema.json")));
    }

    public void validateMandatoryFields(Discriminator discriminator) {
        assertThat(discriminator.propertyName(), is("petType"));
    }

}
