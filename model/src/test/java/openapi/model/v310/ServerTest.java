package openapi.model.v310;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import openapi.model.v310.utils.Parser;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Tag;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Server Object : https://github.com/OAI/OpenAPI-Specification/blob/main/versions/3.1.0.md#server-object")
public class ServerTest {

    static String allFieldsJSON = "/Server/all-fields.json";
    static String allFieldsYAML = "/Server/all-fields.yaml";
    static String mandatoryFieldsJSON = "/Server/mandatory-fields.json";
    static String missingFieldsJSON = "/Server/missing-fields.json";
    static String invalidSubstitutionJSON = "/Server/invalid-substitution.json";
    static String invalidUrlJSON = "/Server/invalid-url.json";
    static String invalidUrlAfterSubstitutionJSON = "/Server/invalid-url-substitution.json";

    static final ObjectMapper jsonMapper = Parser.jsonMapper;
    static final ObjectMapper yamlMapper = Parser.yamlMapper;

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @Tag("JSON")
    @DisplayName("All fields [JSON]")
    public void allFieldsJSON() throws IOException {
        Server server = jsonMapper.readValue(getClass().getResource(allFieldsJSON), Server.class);
        validateAllFields(server);
    }

    @Test
    @Tag("YAML")
    @DisplayName("All fields [YAML]")
    public void allFieldsYAML() throws IOException {
        Server server = yamlMapper.readValue(getClass().getResource(allFieldsYAML), Server.class);
        validateAllFields(server);
    }

    @Test
    @Tag("JSON")
    @DisplayName("Mandatory fields")
    public void mandatoryFieldsJSON() throws IOException {
        Server server = jsonMapper.readValue(getClass().getResource(mandatoryFieldsJSON), Server.class);
        validateMandatoryFields(server);
    }

    @Test
    @Tag("JSON")
    @DisplayName("Missing Mandatory fields")
    public void missingFieldsJSON() throws IOException {
        Server server = jsonMapper.readValue(getClass().getResource(missingFieldsJSON), Server.class);
        Set<ConstraintViolation<Server>> violations = validator.validate(server);
        validateMissingFields(violations);
    }

    @Test
    @Tag("JSON")
    @DisplayName("invalid 'url' field")
    public void invalidUrlJSON() {
        ValueInstantiationException exception = assertThrows(ValueInstantiationException.class, () -> jsonMapper.readValue(getClass().getResource(invalidUrlJSON), Server.class));
        assertThat(exception.getCause().getClass(), is(IllegalArgumentException.class));
        assertThat(exception.getCause().getMessage(), is("The 'url' field is not a valid URL"));
    }

    @Test
    @Tag("JSON")
    @DisplayName("invalid 'url' field after substitution")
    public void invalidUrlAfterSubstitutionJSON() {
        ValueInstantiationException exception = assertThrows(ValueInstantiationException.class, () -> jsonMapper.readValue(getClass().getResource(invalidUrlAfterSubstitutionJSON), Server.class));
        assertThat(exception.getCause().getClass(), is(IllegalArgumentException.class));
        assertThat(exception.getCause().getMessage(), is("The 'url' field is not a valid URL"));
    }

    @Test
    @Tag("JSON")
    @DisplayName("missing variable in 'url' substitution")
    public void invalidSubstitutionJSON() {
        ValueInstantiationException exception = assertThrows(ValueInstantiationException.class, () -> jsonMapper.readValue(getClass().getResource(invalidSubstitutionJSON), Server.class));
        assertThat(exception.getCause().getClass(), is(IllegalArgumentException.class));
        assertThat(exception.getCause().getMessage(), is("The 'url' field uses substitution variables that are not defined in the 'variables' field"));
    }

    public void validateAllFields(Server server) throws MalformedURLException {
        assertThat(server.url(), is("https://{username}.gigantic-server.com:{port}/{basePath}"));
        assertThat(server.description(), is("The production API server"));
        assertThat(server.variables().size(), is(3));
        assertThat(server.effectiveUrl(), is(new URL("https://demo.gigantic-server.com:8443/v2")));
    }

    public void validateMandatoryFields(Server server) throws MalformedURLException {
        assertThat(server.url(), is("https://development.gigantic-server.com/v1"));
    }

    public void validateMissingFields(Set<ConstraintViolation<Server>> violations) {
        assertThat(violations.size(), is(1));
        var violation = violations.iterator().next();
        assertThat(violation.getConstraintDescriptor().getMessageTemplate(), is("{javax.validation.constraints.NotNull.message}"));
        assertThat(violation.getPropertyPath().toString(), is("url"));
    }

}
