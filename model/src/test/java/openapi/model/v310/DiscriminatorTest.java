package openapi.model.v310;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@DisplayName("Discriminator Object : https://github.com/OAI/OpenAPI-Specification/blob/main/versions/3.1.0.md#discriminator-object")
public class DiscriminatorTest {

    static String allFieldsJSON = "/Discriminator/all-fields.json";
    static String allFieldsYAML = "/Discriminator/all-fields.yaml";
    static String mandatoryFieldsJSON = "/Discriminator/mandatory-fields.json";
    static String missingFieldsJSON = "/Discriminator/missing-fields.json";

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
        Discriminator discriminator = jsonMapper.readValue(getClass().getResource(allFieldsJSON), Discriminator.class);
        validateAllFieldsURL(discriminator);
    }

    @Test
    @Tag("YAML")
    @DisplayName("All fields with URL [YAML]")
    public void allFieldsURLYAML() throws IOException {
        Discriminator discriminator = yamlMapper.readValue(getClass().getResource(allFieldsYAML), Discriminator.class);
        validateAllFieldsURL(discriminator);
    }

    @Test
    @Tag("JSON")
    @DisplayName("Mandatory fields")
    public void mandatoryFields() throws IOException {
        Discriminator discriminator = jsonMapper.readValue(getClass().getResource(mandatoryFieldsJSON), Discriminator.class);
        validateMandatoryFields(discriminator);
    }

    @Test
    @Tag("JSON")
    @DisplayName("Missing Mandatory fields")
    public void missingFields() throws IOException {
        Discriminator discriminator = jsonMapper.readValue(getClass().getResource(missingFieldsJSON), Discriminator.class);
        Set<ConstraintViolation<Discriminator>> violations = validator.validate(discriminator);
        validateMissingFields(violations);
    }

    public void validateAllFieldsURL(Discriminator discriminator) throws MalformedURLException {
        assertThat(discriminator.propertyName(), is("petType"));
        assertThat(discriminator.mapping(), is(Map.of("dog", "#/components/schemas/Dog", "monster", "https://gigantic-server.com/schemas/Monster/schema.json")));
    }

    public void validateMandatoryFields(Discriminator discriminator) {
        assertThat(discriminator.propertyName(), is("petType"));
    }

    public void validateMissingFields(Set<ConstraintViolation<Discriminator>> violations) {
        assertThat(violations.size(), is(1));
        var violation = violations.iterator().next();
        assertThat(violation.getConstraintDescriptor().getMessageTemplate(), is("{javax.validation.constraints.NotNull.message}"));
        assertThat(violation.getPropertyPath().toString(), is("propertyName"));
    }
}
