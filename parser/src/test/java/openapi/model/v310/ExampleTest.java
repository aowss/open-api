package openapi.model.v310;

import openapi.parser.Parser;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static java.util.stream.Collectors.joining;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Example Object : https://github.com/OAI/OpenAPI-Specification/blob/main/versions/3.1.0.md#example-object")
public class ExampleTest {

    static String allFieldsURLJSON = "/Example/all-fields-value.json";
    static String allFieldsURLYAML = "/Example/all-fields-value.yaml";
    static String allFieldsIdentifierJSON = "/Example/all-fields-externalValue.json";
    static String allFieldsIdentifierYAML = "/Example/all-fields-externalValue.yaml";
    static String valueAndExternalValue = "/Example/value-externalValue.json";
    static String invalidUri = "/Example/invalid-uri.json";

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @Tag("JSON")
    @DisplayName("All fields with 'value' [JSON]")
    public void allFieldsURLJSON() throws IOException {
        Example example = Parser.parseJSON(getClass().getResource(allFieldsURLJSON), Example.class);
        validateAllFieldsValue(example);
    }

    @Test
    @Tag("YAML")
    @DisplayName("All fields with 'value' [YAML]")
    public void allFieldsURLYAML() throws IOException {
        Example example = Parser.parseYAML(getClass().getResource(allFieldsURLYAML), Example.class);
        validateAllFieldsValue(example);
    }

    @Test
    @Tag("JSON")
    @DisplayName("All fields with 'externalValue' [JSON]")
    public void allFieldsIdentifierJSON() throws IOException, URISyntaxException {
        Example example = Parser.parseJSON(getClass().getResource(allFieldsIdentifierJSON), Example.class);
        validateAllFieldsExternalValue(example);
    }

    @Test
    @Tag("YAML")
    @DisplayName("All fields with 'externalValue' [YAML]")
    public void allFieldsIdentifierYAML() throws IOException, URISyntaxException {
        Example example = Parser.parseYAML(getClass().getResource(allFieldsIdentifierYAML), Example.class);
        validateAllFieldsExternalValue(example);
    }

    @Test
    @Tag("JSON")
    @DisplayName("'value' and 'externalValue' are mutually exclusive")
    public void UrlAndIdentifier() {
        ValueInstantiationException exception = assertThrows(ValueInstantiationException.class, () -> Parser.parseJSON(getClass().getResource(valueAndExternalValue), Example.class));
        assertThat(exception.getType().getRawClass(), is(Example.class));
        assertThat(exception.getCause().getClass(), is(IllegalArgumentException.class));
        assertThat(exception.getCause().getMessage(), is("An 'example' Object can't have both a 'value' and an 'externalValue' field"));
    }

    @Test
    @Tag("JSON")
    @DisplayName("invalid 'uri' field")
    public void invalidUri() {
        InvalidFormatException exception = assertThrows(InvalidFormatException.class, () -> Parser.parseJSON(getClass().getResource(invalidUri), Example.class));
        assertThat(exception.getValue(), is("external value"));
        assertThat(exception.getTargetType(), is(URI.class));
        assertThat(exception.getPath().stream().map(JsonMappingException.Reference::getFieldName).collect(joining(".")), is("externalValue"));
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
