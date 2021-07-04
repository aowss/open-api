package openapi.model.v310;

import openapi.parser.InvalidValueException;
import openapi.parser.Parser;
import openapi.parser.ParsingException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.exc.ValueInstantiationException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Example Object : https://github.com/OAI/OpenAPI-Specification/blob/main/versions/3.1.0.md#example-object")
public class ExampleTest {

    static String allFieldsURLJSON = "/Example/all-fields-value.json";
    static String allFieldsURLYAML = "/Example/all-fields-value.yaml";
    static String allFieldsIdentifierJSON = "/Example/all-fields-externalValue.json";
    static String allFieldsIdentifierYAML = "/Example/all-fields-externalValue.yaml";
    static String valueAndExternalValue = "/Example/value-externalValue.json";
    static String invalidUri = "/Example/invalid-uri.json";

    private static SerializationTester serializationTester = new SerializationTester();

    @Test
    @Tag("JSON")
    @DisplayName("All fields with 'value' [JSON]")
    public void allFieldsURLJSON() throws IOException, ParsingException {
        Example example = Parser.parseJSON(getClass().getResource(allFieldsURLJSON), Example.class);
        validateAllFieldsValue(example);
        serializationTester.checkJSONSerialization(example, allFieldsURLJSON);
    }

    @Test
    @Tag("YAML")
    @DisplayName("All fields with 'value' [YAML]")
    public void allFieldsURLYAML() throws IOException, ParsingException {
        Example example = Parser.parseYAML(getClass().getResource(allFieldsURLYAML), Example.class);
        validateAllFieldsValue(example);
        serializationTester.checkYAMLSerialization(example, allFieldsURLYAML);
    }

    @Test
    @Tag("JSON")
    @DisplayName("All fields with 'externalValue' [JSON]")
    public void allFieldsIdentifierJSON() throws IOException, URISyntaxException, ParsingException {
        Example example = Parser.parseJSON(getClass().getResource(allFieldsIdentifierJSON), Example.class);
        validateAllFieldsExternalValue(example);
        serializationTester.checkJSONSerialization(example, allFieldsIdentifierJSON);
    }

    @Test
    @Tag("YAML")
    @DisplayName("All fields with 'externalValue' [YAML]")
    public void allFieldsIdentifierYAML() throws IOException, URISyntaxException, ParsingException {
        Example example = Parser.parseYAML(getClass().getResource(allFieldsIdentifierYAML), Example.class);
        validateAllFieldsExternalValue(example);
        serializationTester.checkYAMLSerialization(example, allFieldsIdentifierYAML);
    }

    @Test
    @Tag("JSON")
    @DisplayName("'value' and 'externalValue' are mutually exclusive")
    public void UrlAndIdentifier() {
        ParsingException exception = assertThrows(ParsingException.class, () -> Parser.parseJSON(getClass().getResource(valueAndExternalValue), Example.class));
        assertThat(exception.getMessage(), startsWith("Cannot construct instance of `openapi.model.v310.Example`, problem: An 'example' Object can't have both a 'value' and an 'externalValue' field"));
        assertThat(exception.getCause().getClass(), is(IllegalArgumentException.class));
        assertThat(exception.getCause().getMessage(), is("An 'example' Object can't have both a 'value' and an 'externalValue' field"));
    }

    @Test
    @Tag("JSON")
    @DisplayName("invalid 'uri' field")
    public void invalidUri() {
        InvalidValueException exception = assertThrows(InvalidValueException.class, () -> Parser.parseJSON(getClass().getResource(invalidUri), Example.class));
        assertThat(exception.getInvalidValue(), is("external value"));
        assertThat(exception.getPath(), is("externalValue"));
        assertThat(exception.getExpectedType(), is("URI"));
    }

    public void validateAllFieldsValue(Example example) {
        assertThat(example.summary(), is("An example"));
        assertThat(example.description(), is("The description of the example"));
        //  TODO: check how to validate value
    }

    public void validateAllFieldsExternalValue(Example example) throws URISyntaxException {
        assertThat(example.summary(), is("An example"));
        assertThat(example.description(), is("The description of the example"));
        assertThat(example.externalValue(), is(new URI("https://example.org/examples/address-example.xml")));
    }

}
