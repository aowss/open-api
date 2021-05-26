package openapi.model.v310;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("External Documentation Object : https://github.com/OAI/OpenAPI-Specification/blob/main/versions/3.1.0.md#external-documentation-object")
public class ExternalDocumentationTest {

    static String allFieldsJSON = "/ExternalDocumentation/all-fields.json";
    static String allFieldsYAML = "/ExternalDocumentation/all-fields.yaml";
    static String mandatoryFieldsJSON = "/ExternalDocumentation/mandatory-fields.json";
    static String missingFieldsJSON = "/ExternalDocumentation/missing-fields.json";
    static String invalidUrlJSON = "/ExternalDocumentation/invalid-url.json";

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
    @DisplayName("All fields with URL [JSON]")
    public void allFieldsURLJSON() throws IOException {
        ExternalDocumentation externalDocumentation = jsonMapper.readValue(getClass().getResource(allFieldsJSON), ExternalDocumentation.class);
        validateAllFields(externalDocumentation);
    }

    @Test
    @Tag("YAML")
    @DisplayName("All fields with URL [YAML]")
    public void allFieldsURLYAML() throws IOException {
        ExternalDocumentation externalDocumentation = yamlMapper.readValue(getClass().getResource(allFieldsYAML), ExternalDocumentation.class);
        validateAllFields(externalDocumentation);
    }

    @Test
    @Tag("JSON")
    @DisplayName("Mandatory fields")
    public void mandatoryFieldsJSON() throws IOException {
        ExternalDocumentation externalDocumentation = jsonMapper.readValue(getClass().getResource(mandatoryFieldsJSON), ExternalDocumentation.class);
        validateMandatoryFields(externalDocumentation);
    }

    @Test
    @Tag("JSON")
    @DisplayName("Missing Mandatory fields")
    public void missingFieldsJSON() throws IOException {
        ExternalDocumentation externalDocumentation = jsonMapper.readValue(getClass().getResource(missingFieldsJSON), ExternalDocumentation.class);
        Set<ConstraintViolation<ExternalDocumentation>> violations = validator.validate(externalDocumentation);
        validateMissingFields(violations);
    }

    @Test
    @Tag("JSON")
    @DisplayName("invalid 'url' field: wrong type")
    public void invalidUrl() {
        InvalidFormatException exception = assertThrows(InvalidFormatException.class, () -> jsonMapper.readValue(getClass().getResource(invalidUrlJSON), ExternalDocumentation.class));
        assertThat(exception.getValue(), is("externalDocumentation"));
        assertThat(exception.getTargetType(), is(URL.class));
        assertThat(exception.getPath().get(0).getFieldName(), is("url"));
    }

    public void validateAllFields(ExternalDocumentation externalDocumentation) throws MalformedURLException {
        assertThat(externalDocumentation.description(), is("Find more info here"));
        assertThat(externalDocumentation.url(), is(new URL("https://example.com/externalDocumentation")));
    }

    public void validateMandatoryFields(ExternalDocumentation externalDocumentation) throws MalformedURLException {
        assertThat(externalDocumentation.url(), is(new URL("https://example.com/externalDocumentation")));
    }

    public void validateMissingFields(Set<ConstraintViolation<ExternalDocumentation>> violations) {
        assertThat(violations.size(), is(1));
        var violation = violations.iterator().next();
        assertThat(violation.getConstraintDescriptor().getMessageTemplate(), is("{javax.validation.constraints.NotNull.message}"));
        assertThat(violation.getPropertyPath().toString(), is("url"));
    }
}
