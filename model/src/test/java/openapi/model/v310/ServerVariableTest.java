package openapi.model.v310;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import openapi.model.v310.utils.Parser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Server Variable Object : https://github.com/OAI/OpenAPI-Specification/blob/main/versions/3.1.0.md#server-variable-object")
public class ServerVariableTest {

    static String allFieldsJSON = "/ServerVariable/all-fields.json";
    static String allFieldsYAML = "/ServerVariable/all-fields.yaml";
    static String emptyEnumJSON = "/ServerVariable/empty-enum.json";
    static String invalidEnumJSON = "/ServerVariable/invalid-enum.json";
    static String invalidDefaultJSON = "/ServerVariable/invalid-default.json";
    static String mandatoryFieldsJSON = "/ServerVariable/mandatory-fields.json";
    static String missingFieldsJSON = "/ServerVariable/missing-fields.json";

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
        ServerVariable serverVariable = jsonMapper.readValue(getClass().getResource(allFieldsJSON), ServerVariable.class);
        validateAllFields(serverVariable);
    }

    @Test
    @Tag("YAML")
    @DisplayName("All fields [YAML]")
    public void allFieldsYAML() throws IOException {
        ServerVariable serverVariable = yamlMapper.readValue(getClass().getResource(allFieldsYAML), ServerVariable.class);
        validateAllFields(serverVariable);
    }

    @Test
    @Tag("JSON")
    @DisplayName("Mandatory fields")
    public void mandatoryFieldsJSON() throws IOException {
        ServerVariable serverVariable = jsonMapper.readValue(getClass().getResource(mandatoryFieldsJSON), ServerVariable.class);
        assertThat(serverVariable.defaultValue(), is("8443"));
    }

    @Test
    @Tag("JSON")
    @DisplayName("Missing Mandatory fields")
    public void missingFieldsJSON() throws IOException {
        ServerVariable serverVariable = jsonMapper.readValue(getClass().getResource(missingFieldsJSON), ServerVariable.class);
        Set<ConstraintViolation<ServerVariable>> violations = validator.validate(serverVariable);
        validateMissingFields(violations);
    }

    @Test
    @Tag("JSON")
    @DisplayName("Empty 'enum' field")
    public void emptyEnum() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> jsonMapper.readValue(getClass().getResource(emptyEnumJSON), ServerVariable.class));
        assertThat(exception.getMessage(), is("The 'enum' array can't be empty"));
    }

    @Test
    @Tag("JSON")
    @DisplayName("Invalid 'enum' field: not an array")
    public void invalidEnum() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> jsonMapper.readValue(getClass().getResource(invalidEnumJSON), ServerVariable.class));
        assertThat(exception.getMessage(), is("The 'enum' field must be an array"));
    }

    @Test
    @Tag("JSON")
    @DisplayName("Invalid 'default' field: not part of the 'enum'")
    public void invalidDefault() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> jsonMapper.readValue(getClass().getResource(invalidDefaultJSON), ServerVariable.class));
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
