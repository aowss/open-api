package openapi.model.v310;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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

    static final ObjectMapper jsonMapper = new ObjectMapper();
    static final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @org.junit.jupiter.api.Tag("JSON")
    @DisplayName("All fields with URL [JSON]")
    public void allFieldsURLJSON() throws IOException {
        Tag tag = jsonMapper.readValue(getClass().getResource(allFieldsURLJSON), Tag.class);
        validateAllFields(tag);
    }

    @Test
    @org.junit.jupiter.api.Tag("YAML")
    @DisplayName("All fields with URL [YAML]")
    public void allFieldsURLYAML() throws IOException {
        Tag tag = yamlMapper.readValue(getClass().getResource(allFieldsURLYAML), Tag.class);
        validateAllFields(tag);
    }

    @Test
    @org.junit.jupiter.api.Tag("JSON")
    @DisplayName("Mandatory fields")
    public void mandatoryFields() throws IOException {
        Tag tag = jsonMapper.readValue(getClass().getResource(mandatoryFields), Tag.class);
        validateMandatoryFields(tag);
    }

    @Test
    @org.junit.jupiter.api.Tag("JSON")
    @DisplayName("Missing Mandatory fields")
    public void missingFields() throws IOException {
        Tag tag = jsonMapper.readValue(getClass().getResource(missingFields), Tag.class);
        Set<ConstraintViolation<Tag>> violations = validator.validate(tag);
        validateMissingFields(violations);
    }

    @Test
    @org.junit.jupiter.api.Tag("JSON")
    @DisplayName("invalid 'externalDocs' field")
    public void invalidExternalDocs() {
        InvalidFormatException exception = assertThrows(InvalidFormatException.class, () -> jsonMapper.readValue(getClass().getResource(invalidUrl), Tag.class));
        assertThat(exception.getValue(), is("externalDocumentation"));
        assertThat(exception.getTargetType(), is(URL.class));
        assertThat(exception.getPath().stream().map(JsonMappingException.Reference::getFieldName).collect(joining(".")), is("externalDocs.url"));
    }

    public void validateAllFields(Tag tag) throws MalformedURLException {
        assertThat(tag.name(), is("pet"));
        assertThat(tag.description(), is("Pets operations"));
        assertThat(tag.externalDocs(), is(new ExternalDocumentation("Find more info here", new URL("https://example.com/externalDocumentation"))));
    }

    public void validateMandatoryFields(Tag tag) {
        assertThat(tag.name(), is("pet"));
    }

    public void validateMissingFields(Set<ConstraintViolation<Tag>> violations) {
        assertThat(violations.size(), is(1));
        var violation = violations.iterator().next();
        assertThat(violation.getConstraintDescriptor().getMessageTemplate(), is("{javax.validation.constraints.NotNull.message}"));
        assertThat(violation.getPropertyPath().toString(), is("name"));
    }
}
