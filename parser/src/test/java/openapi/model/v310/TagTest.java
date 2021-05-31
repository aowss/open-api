package openapi.model.v310;

import openapi.parser.InvalidValueException;
import openapi.parser.MissingValueException;
import openapi.parser.Parser;

import openapi.parser.ParsingException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.joining;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Tag Object : https://github.com/OAI/OpenAPI-Specification/blob/main/versions/3.1.0.md#tag-object")
public class TagTest {

    static String allFieldsURLJSON = "/Tag/all-fields.json";
    static String allFieldsURLYAML = "/Tag/all-fields.yaml";
    static String mandatoryFields = "/Tag/mandatory-fields.json";
    static String missingFields = "/Tag/missing-fields.json";
    static String invalidUrl = "/Tag/invalid-docs.json";

    @Test
    @org.junit.jupiter.api.Tag("JSON")
    @DisplayName("All fields with URL [JSON]")
    public void allFieldsURLJSON() throws IOException, ParsingException {
        Tag tag = Parser.parseJSON(getClass().getResource(allFieldsURLJSON), Tag.class);
        validateAllFields(tag);
    }

    @Test
    @org.junit.jupiter.api.Tag("YAML")
    @DisplayName("All fields with URL [YAML]")
    public void allFieldsURLYAML() throws IOException, ParsingException {
        Tag tag = Parser.parseYAML(getClass().getResource(allFieldsURLYAML), Tag.class);
        validateAllFields(tag);
    }

    @Test
    @org.junit.jupiter.api.Tag("JSON")
    @DisplayName("Mandatory fields")
    public void mandatoryFields() throws IOException, ParsingException {
        Tag tag = Parser.parseJSON(getClass().getResource(mandatoryFields), Tag.class);
        validateMandatoryFields(tag);
    }

    @Test
    @org.junit.jupiter.api.Tag("JSON")
    @DisplayName("Missing Mandatory fields")
    public void missingFields() throws IOException {
        MissingValueException exception = assertThrows(MissingValueException.class, () -> Parser.parseJSON(getClass().getResource(missingFields), Tag.class));
        assertThat(exception.getPaths(), is(List.of("name")));
    }

    @Test
    @org.junit.jupiter.api.Tag("JSON")
    @DisplayName("invalid 'externalDocs' field")
    public void invalidExternalDocs() {
        InvalidValueException exception = assertThrows(InvalidValueException.class, () -> Parser.parseJSON(getClass().getResource(invalidUrl), Tag.class));
        assertThat(exception.getInvalidValue(), is("externalDocumentation"));
        assertThat(exception.getPath(), is("externalDocs.url"));
        assertThat(exception.getExpectedType(), is("URL"));
    }

    public void validateAllFields(Tag tag) throws MalformedURLException {
        assertThat(tag.name(), is("pet"));
        assertThat(tag.description(), is("Pets operations"));
        assertThat(tag.externalDocs(), is(new ExternalDocumentation("Find more info here", new URL("https://example.com/externalDocumentation"))));
    }

    public void validateMandatoryFields(Tag tag) {
        assertThat(tag.name(), is("pet"));
    }

}
