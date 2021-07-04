package openapi.model.v310;

import openapi.parser.InvalidValueException;
import openapi.parser.MissingValueException;
import openapi.parser.Parser;
import openapi.parser.ParsingException;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Tag;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Info Object : https://github.com/OAI/OpenAPI-Specification/blob/main/versions/3.1.0.md#info-object")
public class InfoTest {

    static String allFieldsJSON = "/Info/all-fields.json";
    static String allFieldsYAML = "/Info/all-fields.yaml";
    static String mandatoryFieldsJSON = "/Info/mandatory-fields.json";
    static String mandatoryFieldsYAML = "/Info/mandatory-fields.yaml";
    static String missingFieldsJSON = "/Info/missing-fields.json";
    static String missingFieldsYAML = "/Info/missing-fields.yaml";
    static String invalidTermsOfServiceJSON = "/Info/invalid-termsOfService.json";
    static String invalidTermsOfServiceYAML = "/Info/invalid-termsOfService.yaml";

    private static SerializationTester serializationTester = new SerializationTester();

    @Test
    @Tag("JSON")
    @DisplayName("All fields [JSON]")
    public void allFieldsJSON() throws IOException, ParsingException {
        Info info = Parser.parseJSON(getClass().getResource(allFieldsJSON), Info.class);
        validateAllFields(info);
        serializationTester.checkJSONSerialization(info, allFieldsJSON);
    }

    @Test
    @Tag("YAML")
    @DisplayName("All fields [YAML]")
    public void allFieldsYAML() throws IOException, ParsingException {
        Info info = Parser.parseYAML(getClass().getResource(allFieldsYAML), Info.class);
        validateAllFields(info);
        serializationTester.checkYAMLSerialization(info, allFieldsYAML);
    }

    @Test
    @Tag("JSON")
    @DisplayName("Mandatory fields [JSON]")
    public void mandatoryFieldsJSON() throws IOException, ParsingException {
        Info info = Parser.parseJSON(getClass().getResource(mandatoryFieldsJSON), Info.class);
        validateMandatoryFields(info);
    }

    @Test
    @Tag("YAML")
    @DisplayName("Mandatory fields [YAML]")
    public void mandatoryFieldsYAML() throws IOException, ParsingException {
        Info info = Parser.parseYAML(getClass().getResource(mandatoryFieldsYAML), Info.class);
        validateMandatoryFields(info);
    }

    @Test
    @Tag("JSON")
    @DisplayName("Missing Mandatory fields [JSON]")
    public void missingFieldsJSON() {
        MissingValueException exception = assertThrows(MissingValueException.class, () -> Parser.parseJSON(getClass().getResource(missingFieldsJSON), Info.class));
        validateMissingFields(exception);
    }

    @Test
    @Tag("YAML")
    @DisplayName("Missing Mandatory fields [YAML]")
    public void missingFieldsYAML() {
        MissingValueException exception = assertThrows(MissingValueException.class, () -> Parser.parseYAML(getClass().getResource(missingFieldsYAML), Info.class));
        validateMissingFields(exception);
    }

    @Test
    @Tag("JSON")
    @DisplayName("invalid 'termsOfService' field: wrong type [JSON]")
    public void invalidTermsOfServiceJSON() {
        InvalidValueException exception = assertThrows(InvalidValueException.class, () -> Parser.parseJSON(getClass().getResource(invalidTermsOfServiceJSON), Info.class));
        validateInvalidFields(exception);
    }

    @Test
    @Tag("YAML")
    @DisplayName("invalid 'termsOfService' field: wrong type [YAML]")
    public void invalidTermsOfServiceYAML() {
        InvalidValueException exception = assertThrows(InvalidValueException.class, () -> Parser.parseYAML(getClass().getResource(invalidTermsOfServiceYAML), Info.class));
        validateInvalidFields(exception);
    }

    public void validateAllFields(Info info) throws MalformedURLException {
        assertThat(info.title(), is("Sample Pet Store App"));
        assertThat(info.summary(), is("A pet store manager."));
        assertThat(info.description(), is("This is a sample server for a pet store."));
        assertThat(info.termsOfService(), is(new URL("https://example.com/terms/")));
        assertThat(info.contact(), is(new Contact("API Support", new URL("https://www.example.com/support"), "support@example.com")));
        assertThat(info.license(), is(new License("Apache 2.0", new URL("https://www.apache.org/licenses/LICENSE-2.0.html"))));
        assertThat(info.version(), is("1.0.1"));
    }

    public void validateMandatoryFields(Info info) {
        assertThat(info.title(), is("Sample Pet Store App"));
        assertThat(info.version(), is("1.0.1"));
    }

    public void validateMissingFields(MissingValueException exception) {
        assertThat(exception.getPaths(), containsInAnyOrder(new String[] {"title", "version"}));
        assertThat(exception.getMessage(), is(in(new String[] {"The value(s) at location(s) 'title, version' is/are required", "The value(s) at location(s) 'version, title' is/are required"})));
    }

    public void validateInvalidFields(InvalidValueException exception) {
        assertThat(exception.getInvalidValue(), is("terms"));
        assertThat(exception.getPath(), is("termsOfService"));
        assertThat(exception.getExpectedType(), is("URL"));
    }
}
