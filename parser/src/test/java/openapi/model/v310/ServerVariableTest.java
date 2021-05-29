package openapi.model.v310;

import openapi.parser.Parser;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Server Variable Object : https://github.com/OAI/OpenAPI-Specification/blob/main/versions/3.1.0.md#server-variable-object")
public class ServerVariableTest {

    static String allFieldsJSON = "/ServerVariable/all-fields.json";
    static String allFieldsYAML = "/ServerVariable/all-fields.yaml";
    static String emptyEnum = "/ServerVariable/empty-enum.json";
    static String invalidEnum = "/ServerVariable/invalid-enum.json";
    static String invalidDefault = "/ServerVariable/invalid-default.json";
    static String mandatoryFields = "/ServerVariable/mandatory-fields.json";
    static String missingFields = "/ServerVariable/missing-fields.json";

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
        ServerVariable serverVariable = Parser.parseJSON(getClass().getResource(allFieldsJSON), ServerVariable.class);
        validateAllFields(serverVariable);
    }

    @Test
    @Tag("YAML")
    @DisplayName("All fields [YAML]")
    public void allFieldsYAML() throws IOException {
        ServerVariable serverVariable = Parser.parseYAML(getClass().getResource(allFieldsYAML), ServerVariable.class);
        validateAllFields(serverVariable);
    }

    @Test
    @Tag("JSON")
    @DisplayName("Mandatory fields")
    public void mandatoryFields() throws IOException {
        ServerVariable serverVariable = Parser.parseJSON(getClass().getResource(mandatoryFields), ServerVariable.class);
        assertThat(serverVariable.defaultValue(), is("8443"));
    }

    @Test
    @Tag("JSON")
    @DisplayName("Missing Mandatory fields")
    public void missingFields() throws IOException {
        ServerVariable serverVariable = Parser.parseJSON(getClass().getResource(missingFields), ServerVariable.class);
        Set<ConstraintViolation<ServerVariable>> violations = validator.validate(serverVariable);
        validateMissingFields(violations);
    }

    @Test
    @Tag("JSON")
    @DisplayName("Empty 'enum' field")
    public void emptyEnum() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Parser.parseJSON(getClass().getResource(emptyEnum), ServerVariable.class));
        assertThat(exception.getMessage(), is("The 'enum' array can't be empty"));
    }

    @Test
    @Tag("JSON")
    @DisplayName("Invalid 'enum' field: not an array")
    public void invalidEnum() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Parser.parseJSON(getClass().getResource(invalidEnum), ServerVariable.class));
        assertThat(exception.getMessage(), is("The 'enum' field must be an array"));
    }

    @Test
    @Tag("JSON")
    @DisplayName("Invalid 'default' field: not part of the 'enum'")
    public void invalidDefault() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> Parser.parseJSON(getClass().getResource(invalidDefault), ServerVariable.class));
        assertThat(exception.getMessage(), is("The 'default' value must be one of the 'enum' values"));
    }

    public void validateAllFields(ServerVariable serverVariable) {
        assertThat(serverVariable.enums(), contains("8443", "443"));
        assertThat(serverVariable.defaultValue(), is("8443"));
        assertThat(serverVariable.description(), is("the port number"));
    }

    public void validateMissingFields(Set<ConstraintViolation<ServerVariable>> violations) {
        assertThat(violations.size(), is(1));
        var violation = violations.iterator().next();
        assertThat(violation.getConstraintDescriptor().getMessageTemplate(), is("{javax.validation.constraints.NotNull.message}"));
        assertThat(violation.getPropertyPath().toString(), is("defaultValue"));
    }

}
