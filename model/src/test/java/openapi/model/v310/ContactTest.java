package openapi.model.v310;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Tag;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Contact Object : https://github.com/OAI/OpenAPI-Specification/blob/main/versions/3.1.0.md#contact-object")
public class ContactTest {

    static String allFieldsJSON = "/Contact/all-fields.json";
    static String allFieldsYAML = "/Contact/all-fields.yaml";
    static String invalidUrlJSON = "/Contact/invalid-url.json";
    static String invalidEmailJSON = "/Contact/invalid-email.json";

    static final ObjectMapper jsonMapper = new ObjectMapper();
    static final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @Tag("JSON")
    @DisplayName("All fields")
    public void allFieldsJSON() throws URISyntaxException, IOException {
        Contact contact = jsonMapper.readValue(getClass().getResource(allFieldsJSON), Contact.class);
        validateAllFields(contact);
    }

    @Test
    @Tag("YAML")
    @DisplayName("All fields")
    public void allFieldsYAML() throws URISyntaxException, IOException {
        Contact contact = yamlMapper.readValue(getClass().getResource(allFieldsYAML), Contact.class);
        validateAllFields(contact);
    }

    @Test
    @Tag("JSON")
    @DisplayName("Invalid 'email' field: doesn't conform to Email annotation")
    public void invalidEmail() throws IOException {
        Contact contact = jsonMapper.readValue(getClass().getResource(invalidEmailJSON), Contact.class);
        Set<ConstraintViolation<Contact>> violations = validator.validate(contact);
        assertThat(violations.size(), is(1));
        var violation = violations.iterator().next();
        assertThat(violation.getInvalidValue(), is("support"));
        assertThat(violation.getPropertyPath().toString(), is("email"));
        assertThat(violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName(), is("Email"));
    }

    @Test
    @Tag("JSON")
    @DisplayName("invalid 'url' field: wrong type")
    public void invalidUrl() {
        InvalidFormatException exception = assertThrows(InvalidFormatException.class, () -> jsonMapper.readValue(getClass().getResource(invalidUrlJSON), Contact.class));
        assertThat(exception.getValue(), is("support"));
        assertThat(exception.getTargetType(), is(URL.class));
        assertThat(exception.getPath().get(0).getFieldName(), is("url"));
    }

    public void validateAllFields(Contact contact) throws MalformedURLException {
        assertThat(contact.name(), is("API Support"));
        assertThat(contact.url(), is(new URL("https://www.example.com/support")));
        assertThat(contact.email(), is("support@example.com"));
    }

}
