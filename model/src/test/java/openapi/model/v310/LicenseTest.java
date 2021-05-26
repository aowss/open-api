package openapi.model.v310;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
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

import static java.util.stream.Collectors.joining;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("License Object : https://github.com/OAI/OpenAPI-Specification/blob/main/versions/3.1.0.md#license-object")
public class    LicenseTest {

    static String allFieldsURLJSON = "/License/all-fields-url.json";
    static String allFieldsURLYAML = "/License/all-fields-url.yaml";
    static String allFieldsIdentifierJSON = "/License/all-fields-identifier.json";
    static String allFieldsIdentifierYAML = "/License/all-fields-identifier.yaml";
    static String URLAndIdentifier = "/License/url-identifier.json";
    static String mandatoryFieldsJSON = "/License/mandatory-fields.json";
    static String missingFieldsJSON = "/License/missing-fields.json";
    static String invalidUrlJSON = "/License/invalid-url.json";

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
        License license = jsonMapper.readValue(getClass().getResource(allFieldsURLJSON), License.class);
        validateAllFieldsURL(license);
    }

    @Test
    @Tag("YAML")
    @DisplayName("All fields with URL [YAML]")
    public void allFieldsURLYAML() throws IOException {
        License license = yamlMapper.readValue(getClass().getResource(allFieldsURLYAML), License.class);
        validateAllFieldsURL(license);
    }

    @Test
    @Tag("JSON")
    @DisplayName("All fields with Identifier [JSON]")
    public void allFieldsIdentifierJSON() throws IOException {
        License license = jsonMapper.readValue(getClass().getResource(allFieldsIdentifierJSON), License.class);
        validateAllFieldsIdentifier(license);
    }

    @Test
    @Tag("YAML")
    @DisplayName("All fields with Identifier [YAML]")
    public void allFieldsIdentifierYAML() throws IOException {
        License license = yamlMapper.readValue(getClass().getResource(allFieldsIdentifierYAML), License.class);
        validateAllFieldsIdentifier(license);
    }

    @Test
    @Tag("JSON")
    @DisplayName("Mandatory fields")
    public void mandatoryFields() throws IOException {
        License license = jsonMapper.readValue(getClass().getResource(mandatoryFieldsJSON), License.class);
        validateMandatoryFields(license);
    }

    @Test
    @Tag("JSON")
    @DisplayName("Missing Mandatory fields")
    public void missingFields() throws IOException {
        License license = jsonMapper.readValue(getClass().getResource(missingFieldsJSON), License.class);
        Set<ConstraintViolation<License>> violations = validator.validate(license);
        validateMissingFields(violations);
    }

    @Test
    @Tag("JSON")
    @DisplayName("invalid 'url' field: wrong type")
    public void invalidUrl() {
        InvalidFormatException exception = assertThrows(InvalidFormatException.class, () -> jsonMapper.readValue(getClass().getResource(invalidUrlJSON), License.class));
        assertThat(exception.getValue(), is("license"));
        assertThat(exception.getTargetType(), is(URL.class));
        assertThat(exception.getPath().stream().map(JsonMappingException.Reference::getFieldName).collect(joining(".")), is("url"));
    }

    @Test
    @Tag("JSON")
    @DisplayName("'url' and 'identifier' are mutually exclusive")
    public void UrlAndIdentifier() {
        ValueInstantiationException exception = assertThrows(ValueInstantiationException.class, () -> jsonMapper.readValue(getClass().getResource(URLAndIdentifier), License.class));
        assertThat(exception.getType().getRawClass(), is(License.class));
        assertThat(exception.getCause().getClass(), is(IllegalArgumentException.class));
        assertThat(exception.getCause().getMessage(), is("A license can't have both an identifier and a url"));
    }

    public void validateAllFieldsURL(License license) throws MalformedURLException {
        assertThat(license.name(), is("Apache 2.0"));
        assertThat(license.url(), is(new URL("https://www.example.com/license")));
    }

    public void validateAllFieldsIdentifier(License license) throws MalformedURLException {
        assertThat(license.name(), is("Apache 2.0"));
        assertThat(license.identifier(), is("Apache-2.0"));
    }

    public void validateMandatoryFields(License license) {
        assertThat(license.name(), is("Apache 2.0"));
    }

    public void validateMissingFields(Set<ConstraintViolation<License>> violations) {
        assertThat(violations.size(), is(1));
        var violation = violations.iterator().next();
        assertThat(violation.getConstraintDescriptor().getMessageTemplate(), is("{javax.validation.constraints.NotNull.message}"));
        assertThat(violation.getPropertyPath().toString(), is("name"));
    }
}
