package openapi.model.v310;

import openapi.parser.InvalidValueException;
import openapi.parser.Parser;
import openapi.parser.ParsingException;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Tag;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Contact Object : https://github.com/OAI/OpenAPI-Specification/blob/main/versions/3.1.0.md#contact-object")
public class ContactTest {

    static String allFieldsJSON = "/Contact/all-fields.json";
    static String allFieldsYAML = "/Contact/all-fields.yaml";
    static String invalidUrl = "/Contact/invalid-url.json";
    static String invalidEmail = "/Contact/invalid-email.json";

    private static SerializationTester serializationTester = new SerializationTester();

    @Test
    @Tag("JSON")
    @DisplayName("All fields [JSON]")
    public void allFieldsJSON() throws IOException, ParsingException, URISyntaxException {
        Contact contact = Parser.parseJSON(getClass().getResource(allFieldsJSON), Contact.class);
        validateAllFields(contact);
        serializationTester.checkJSONSerialization(contact, allFieldsJSON);
    }

    @Test
    @Tag("YAML")
    @DisplayName("All fields [YAML]")
    public void allFieldsYAML() throws IOException, ParsingException {
        Contact contact = Parser.parseYAML(getClass().getResource(allFieldsYAML), Contact.class);
        validateAllFields(contact);
        serializationTester.checkYAMLSerialization(contact, allFieldsYAML);
    }

    @Test
    @Tag("JSON")
    @DisplayName("Invalid 'email' field: doesn't conform to Email annotation")
    public void invalidEmail() {
        InvalidValueException exception = assertThrows(InvalidValueException.class, () -> Parser.parseJSON(getClass().getResource(invalidEmail), Contact.class));
        assertThat(exception.getInvalidValue(), is("support"));
        assertThat(exception.getPath(), is("email"));
        assertThat(exception.getExpectedType(), is("Email"));
        assertThat(exception.getMessage(), is("The value 'support' at location 'email' is invalid: it should be a 'Email'"));
    }

    @Test
    @Tag("JSON")
    @DisplayName("invalid 'url' field: wrong type")
    public void invalidUrl() {
        InvalidValueException exception = assertThrows(InvalidValueException.class, () -> Parser.parseJSON(getClass().getResource(invalidUrl), Contact.class));
        assertThat(exception.getInvalidValue(), is("support"));
        assertThat(exception.getPath(), is("url"));
        assertThat(exception.getExpectedType(), is("URL"));
    }

    public void validateAllFields(Contact contact) throws MalformedURLException {
        assertThat(contact.name(), is("API Support"));
        assertThat(contact.url(), is(new URL("https://www.example.com/support")));
        assertThat(contact.email(), is("support@example.com"));
    }

}
