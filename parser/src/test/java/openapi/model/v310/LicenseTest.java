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
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("License Object : https://github.com/OAI/OpenAPI-Specification/blob/main/versions/3.1.0.md#license-object")
public class    LicenseTest {

    static String allFieldsURLJSON = "/License/all-fields-url.json";
    static String allFieldsURLYAML = "/License/all-fields-url.yaml";
    static String allFieldsIdentifierJSON = "/License/all-fields-identifier.json";
    static String allFieldsIdentifierYAML = "/License/all-fields-identifier.yaml";
    static String URLAndIdentifier = "/License/url-identifier.json";
    static String mandatoryFields = "/License/mandatory-fields.json";
    static String missingFields = "/License/missing-fields.json";
    static String invalidUrl = "/License/invalid-url.json";

    @Test
    @Tag("JSON")
    @DisplayName("All fields with URL [JSON]")
    public void allFieldsURLJSON() throws IOException, ParsingException {
        License license = Parser.parseJSON(getClass().getResource(allFieldsURLJSON), License.class);
        validateAllFieldsURL(license);
    }

    @Test
    @Tag("YAML")
    @DisplayName("All fields with URL [YAML]")
    public void allFieldsURLYAML() throws IOException, ParsingException {
        License license = Parser.parseYAML(getClass().getResource(allFieldsURLYAML), License.class);
        validateAllFieldsURL(license);
    }

    @Test
    @Tag("JSON")
    @DisplayName("All fields with Identifier [JSON]")
    public void allFieldsIdentifierJSON() throws IOException, ParsingException {
        License license = Parser.parseJSON(getClass().getResource(allFieldsIdentifierJSON), License.class);
        validateAllFieldsIdentifier(license);
    }

    @Test
    @Tag("YAML")
    @DisplayName("All fields with Identifier [YAML]")
    public void allFieldsIdentifierYAML() throws IOException, ParsingException {
        License license = Parser.parseYAML(getClass().getResource(allFieldsIdentifierYAML), License.class);
        validateAllFieldsIdentifier(license);
    }

    @Test
    @Tag("JSON")
    @DisplayName("Mandatory fields")
    public void mandatoryFields() throws IOException, ParsingException {
        License license = Parser.parseJSON(getClass().getResource(mandatoryFields), License.class);
        validateMandatoryFields(license);
    }

    @Test
    @Tag("JSON")
    @DisplayName("Missing Mandatory fields")
    public void missingFields() {
        MissingValueException exception = assertThrows(MissingValueException.class, () -> Parser.parseJSON(getClass().getResource(missingFields), License.class));
        assertThat(exception.getPaths(), is(List.of("name")));
    }

    @Test
    @Tag("JSON")
    @DisplayName("invalid 'url' field: wrong type")
    public void invalidUrl() {
        InvalidValueException exception = assertThrows(InvalidValueException.class, () -> Parser.parseJSON(getClass().getResource(invalidUrl), Contact.class));
        assertThat(exception.getInvalidValue(), is("license"));
        assertThat(exception.getPath(), is("url"));
        assertThat(exception.getExpectedType(), is("URL"));
    }

    @Test
    @Tag("JSON")
    @DisplayName("'url' and 'identifier' are mutually exclusive")
    public void UrlAndIdentifier() {
        ParsingException exception = assertThrows(ParsingException.class, () -> Parser.parseJSON(getClass().getResource(URLAndIdentifier), License.class));
        assertThat(exception.getMessage(), startsWith("Cannot construct instance of `openapi.model.v310.License`, problem: A license can't have both an identifier and a url"));
        assertThat(exception.getCause().getClass(), is(IllegalArgumentException.class));
        assertThat(exception.getCause().getMessage(), is("A license can't have both an identifier and a url"));
    }

    public void validateAllFieldsURL(License license) throws MalformedURLException {
        assertThat(license.name(), is("Apache 2.0"));
        assertThat(license.url(), is(new URL("https://www.example.com/license")));
    }

    public void validateAllFieldsIdentifier(License license) {
        assertThat(license.name(), is("Apache 2.0"));
        assertThat(license.identifier(), is("Apache-2.0"));
    }

    public void validateMandatoryFields(License license) {
        assertThat(license.name(), is("Apache 2.0"));
    }

}
