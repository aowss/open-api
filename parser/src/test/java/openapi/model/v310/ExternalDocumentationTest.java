package openapi.model.v310;

import openapi.parser.InvalidValueException;
import openapi.parser.MissingValueException;
import openapi.parser.Parser;
import openapi.parser.ParsingException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("External Documentation Object : https://github.com/OAI/OpenAPI-Specification/blob/main/versions/3.1.0.md#external-documentation-object")
public class ExternalDocumentationTest {

    static String allFieldsJSON = "/ExternalDocumentation/all-fields.json";
    static String allFieldsYAML = "/ExternalDocumentation/all-fields.yaml";
    static String mandatoryFields = "/ExternalDocumentation/mandatory-fields.json";
    static String missingFields = "/ExternalDocumentation/missing-fields.json";
    static String invalidUrl = "/ExternalDocumentation/invalid-url.json";

    private static SerializationTester serializationTester = new SerializationTester();

    @Test
    @Tag("JSON")
    @DisplayName("All fields [JSON]")
    public void allFieldsJSON() throws IOException, ParsingException {
        ExternalDocumentation externalDocumentation = Parser.parseJSON(getClass().getResource(allFieldsJSON), ExternalDocumentation.class);
        validateAllFields(externalDocumentation);
        serializationTester.checkJSONSerialization(externalDocumentation, allFieldsJSON);
    }

    @Test
    @Tag("YAML")
    @DisplayName("All fields [YAML]")
    public void allFieldsYAML() throws IOException, ParsingException {
        ExternalDocumentation externalDocumentation = Parser.parseYAML(getClass().getResource(allFieldsYAML), ExternalDocumentation.class);
        validateAllFields(externalDocumentation);
        serializationTester.checkYAMLSerialization(externalDocumentation, allFieldsYAML);
    }

    @Test
    @Tag("JSON")
    @DisplayName("Mandatory fields")
    public void mandatoryFields() throws IOException, ParsingException {
        ExternalDocumentation externalDocumentation = Parser.parseJSON(getClass().getResource(mandatoryFields), ExternalDocumentation.class);
        validateMandatoryFields(externalDocumentation);
    }

    @Test
    @Tag("JSON")
    @DisplayName("Missing Mandatory fields")
    public void missingFields() {
        MissingValueException exception = assertThrows(MissingValueException.class, () -> Parser.parseJSON(getClass().getResource(missingFields), ExternalDocumentation.class));
        assertThat(exception.getPaths(), is(List.of("url")));
    }

    @Test
    @Tag("JSON")
    @DisplayName("invalid 'url' field: wrong type")
    public void invalidUrl() {
        InvalidValueException exception = assertThrows(InvalidValueException.class, () -> Parser.parseJSON(getClass().getResource(invalidUrl), ExternalDocumentation.class));
        assertThat(exception.getInvalidValue(), is("externalDocumentation"));
        assertThat(exception.getPath(), is("url"));
        assertThat(exception.getExpectedType(), is("URL"));
    }

    public void validateAllFields(ExternalDocumentation externalDocumentation) throws MalformedURLException {
        assertThat(externalDocumentation.description(), is("Find more info here"));
        assertThat(externalDocumentation.url(), is(new URL("https://example.com/externalDocumentation")));
    }

    public void validateMandatoryFields(ExternalDocumentation externalDocumentation) throws MalformedURLException {
        assertThat(externalDocumentation.url(), is(new URL("https://example.com/externalDocumentation")));
    }

}
