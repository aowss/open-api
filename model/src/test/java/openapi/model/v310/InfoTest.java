package openapi.model.v310;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.hibernate.validator.HibernateValidator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Tag;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Info Object : https://github.com/OAI/OpenAPI-Specification/blob/main/versions/3.1.0.md#info-object")
public class InfoTest {

    static String allFieldsJSON = "/Info/all-fields.json";
    static String allFieldsYAML = "/Info/all-fields.yaml";
    static String mandatoryFieldsJSON = "/Info/mandatory-fields.json";
    static String mandatoryFieldsYAML = "/Info/mandatory-fields.yaml";
    static String missingFieldsJSON = "/Info/missing-fields.json";
    static String missingFieldsYAML = "/Info/missing-fields.yaml";
    static String invalidTermsOfServiceJSON = "/Info/invalid-termsOfService.json";
    static String invalidTermsOfServiceYAML = "/Info/invalid-termsOfService.yaml";

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
    @DisplayName("All fields [JSON]")
    public void allFieldsJSON() throws IOException {
        Info info = jsonMapper.readValue(getClass().getResource(allFieldsJSON), Info.class);
        validateAllFields(info);
    }

    @Test
    @Tag("YAML")
    @DisplayName("All fields [YAML]")
    public void allFieldsYAML() throws IOException {
        Info info = yamlMapper.readValue(getClass().getResource(allFieldsYAML), Info.class);
        validateAllFields(info);
    }

    @Test
    @Tag("JSON")
    @DisplayName("Mandatory fields [JSON]")
    public void mandatoryFieldsJSON() throws IOException {
        Info info = jsonMapper.readValue(getClass().getResource(mandatoryFieldsJSON), Info.class);
        validateMandatoryFields(info);
    }

    @Test
    @Tag("YAML")
    @DisplayName("Mandatory fields [YAML]")
    public void mandatoryFieldsYAML() throws IOException {
        Info info = yamlMapper.readValue(getClass().getResource(mandatoryFieldsYAML), Info.class);
        validateMandatoryFields(info);
    }

    @Test
    @Tag("JSON")
    @DisplayName("Missing Mandatory fields [JSON]")
    public void missingFieldsJSON() throws IOException {
        Info info = jsonMapper.readValue(getClass().getResource(missingFieldsJSON), Info.class);
        Set<ConstraintViolation<Info>> violations = validator.validate(info);
        validateMissingFields(violations);
    }

    @Test
    @Tag("YAML")
    @DisplayName("Missing Mandatory fields [YAML]")
    public void missingFieldsYAML() throws IOException {
        Info info = yamlMapper.readValue(getClass().getResource(missingFieldsYAML), Info.class);
        Set<ConstraintViolation<Info>> violations = validator.validate(info);
        validateMissingFields(violations);
    }

    @Test
    @Tag("JSON")
    @DisplayName("invalid 'termsOfService' field: wrong type [JSON]")
    public void invalidTermsOfServiceJSON() {
        InvalidFormatException exception = assertThrows(InvalidFormatException.class, () -> jsonMapper.readValue(getClass().getResource(invalidTermsOfServiceJSON), Info.class));
        assertThat(exception.getValue(), is("terms"));
        assertThat(exception.getTargetType(), is(URL.class));
        assertThat(exception.getPath().get(0).getFieldName(), is("termsOfService"));
    }

    @Test
    @Tag("YAML")
    @DisplayName("invalid 'termsOfService' field: wrong type [YAML]")
    public void invalidTermsOfServiceYAML() {
        InvalidFormatException exception = assertThrows(InvalidFormatException.class, () -> yamlMapper.readValue(getClass().getResource(invalidTermsOfServiceYAML), Info.class));
        assertThat(exception.getValue(), is("terms"));
        assertThat(exception.getTargetType(), is(URL.class));
        assertThat(exception.getPath().get(0).getFieldName(), is("termsOfService"));
    }

    public void validateAllFields(Info info) throws MalformedURLException {
        assertThat(info.title(), is("Sample Pet Store App"));
        assertThat(info.summary(), is("A pet store manager."));
        assertThat(info.description(), is("This is a sample server for a pet store."));
        assertThat(info.termsOfService(), is(new URL("https://example.com/terms/")));
        assertThat(info.contact(), is(new Contact("API Support", new URL("https://www.example.com/support"), "support@example.com")));
        assertThat(info.license(), is(new License("Apache 2.0", new URL("https://www.apache.org/licenses/LICENSE-2.0.html"))));
        assertThat(info.version(), is("1.0.1"));
    }

    public void validateMandatoryFields(Info info) {
        assertThat(info.title(), is("Sample Pet Store App"));
        assertThat(info.version(), is("1.0.1"));
    }

    public void validateMissingFields(Set<ConstraintViolation<Info>> violations) {
        assertThat(violations.size(), is(2));

        var constraints = violations.stream()
                .map(violation -> violation.getConstraintDescriptor().getMessageTemplate())
                .distinct()
                .collect(Collectors.toList());
        assertThat(constraints, is(List.of("{javax.validation.constraints.NotNull.message}")));

        var fields = violations.stream()
                .map(violation -> violation.getPropertyPath().toString())
                .collect(Collectors.toList());
        System.out.println(fields);
        assertThat(fields, containsInAnyOrder(new String[] { "title", "version" }));
    }

}
