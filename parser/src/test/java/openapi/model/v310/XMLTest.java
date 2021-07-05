package openapi.model.v310;

import openapi.parser.InvalidValueException;
import openapi.parser.Parser;
import openapi.parser.ParsingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("XML Object : https://github.com/OAI/OpenAPI-Specification/blob/main/versions/3.1.0.md#xml-object")
public class XMLTest {

    static String allFieldsJSON = "/XML/all-fields.json";
    static String allFieldsYAML = "/XML/all-fields.yaml";
    static String invalidUri = "/XML/invalid-uri.json";
    static String defaultValues = "/XML/default-values.json";

    private static SerializationTester serializationTester = new SerializationTester();

    @Test
    @Tag("JSON")
    @DisplayName("All fields [JSON]")
    public void allFieldsJSON() throws IOException, ParsingException, URISyntaxException {
        XML xml = Parser.parseJSON(getClass().getResource(allFieldsJSON), XML.class);
        validateAllFields(xml);
        serializationTester.checkJSONSerialization(xml, allFieldsJSON);
    }

    @Test
    @Tag("YAML")
    @DisplayName("All fields [YAML]")
    public void allFieldsYAML() throws IOException, ParsingException, URISyntaxException {
        XML xml = Parser.parseYAML(getClass().getResource(allFieldsYAML), XML.class);
        validateAllFields(xml);
        serializationTester.checkYAMLSerialization(xml, allFieldsYAML);
    }

    @Test
    @Tag("JSON")
    @DisplayName("Default values")
    public void defaultValues() throws IOException, ParsingException, URISyntaxException {
        XML xml = Parser.parseYAML(getClass().getResource(defaultValues), XML.class);
        assertThat(xml.attribute(), is(false));
        assertThat(xml.wrapped(), is(false));
    }

    @Test
    @Tag("JSON")
    @DisplayName("invalid 'uri' field")
    public void invalidUrl() {
        InvalidValueException exception = assertThrows(InvalidValueException.class, () -> Parser.parseJSON(getClass().getResource(invalidUri), XML.class));
        assertThat(exception.getInvalidValue(), is("xml namespace"));
        assertThat(exception.getPath(), is("namespace"));
        assertThat(exception.getExpectedType(), is("URI"));
    }

    public void validateAllFields(XML xml) throws URISyntaxException {
        assertThat(xml.name(), is("animal"));
        assertThat(xml.namespace(), is(new URI("https://www.example.com/xml")));
        assertThat(xml.prefix(), is("xml"));
        assertThat(xml.attribute(), is(true));
        assertThat(xml.wrapped(), is(false));
    }

}
