package openapi.model.v310;

import openapi.parser.MissingValueException;
import openapi.parser.Parser;
import openapi.parser.ParsingException;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Tag;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Server Object : https://github.com/OAI/OpenAPI-Specification/blob/main/versions/3.1.0.md#server-object")
public class ServerTest {

    static String allFieldsJSON = "/Server/all-fields.json";
    static String allFieldsYAML = "/Server/all-fields.yaml";
    static String mandatoryFields = "/Server/mandatory-fields.json";
    static String missingFields = "/Server/missing-fields.json";
    static String invalidSubstitution = "/Server/invalid-substitution.json";
    static String invalidUrl = "/Server/invalid-url.json";
    static String invalidUrlAfterSubstitution = "/Server/invalid-url-substitution.json";

    @Test
    @Tag("JSON")
    @DisplayName("All fields [JSON]")
    public void allFieldsJSON() throws IOException, ParsingException {
        Server server = Parser.parseJSON(getClass().getResource(allFieldsJSON), Server.class);
        validateAllFields(server);
    }

    @Test
    @Tag("YAML")
    @DisplayName("All fields [YAML]")
    public void allFieldsYAML() throws IOException, ParsingException {
        Server server = Parser.parseYAML(getClass().getResource(allFieldsYAML), Server.class);
        validateAllFields(server);
    }

    @Test
    @Tag("JSON")
    @DisplayName("Mandatory fields")
    public void mandatoryFields() throws IOException, ParsingException {
        Server server = Parser.parseJSON(getClass().getResource(mandatoryFields), Server.class);
        validateMandatoryFields(server);
    }

    @Test
    @Tag("JSON")
    @DisplayName("Missing Mandatory fields")
    public void missingFields() throws IOException {
        MissingValueException exception = assertThrows(MissingValueException.class, () -> Parser.parseJSON(getClass().getResource(missingFields), Server.class));
        assertThat(exception.getPaths(), is(List.of("url")));
    }

    @Test
    @Tag("JSON")
    @DisplayName("invalid 'url' field")
    public void invalidUrl() {
        ParsingException exception = assertThrows(ParsingException.class, () -> Parser.parseJSON(getClass().getResource(invalidUrl), Server.class));
        assertThat(exception.getMessage(), startsWith("Cannot construct instance of `openapi.model.v310.Server`, problem: The 'url' field is not a valid URL"));
        assertThat(exception.getCause().getClass(), is(IllegalArgumentException.class));
        assertThat(exception.getCause().getMessage(), is("The 'url' field is not a valid URL"));
    }

    @Test
    @Tag("JSON")
    @DisplayName("invalid 'url' field after substitution")
    public void invalidUrlAfterSubstitution() {
        ParsingException exception = assertThrows(ParsingException.class, () -> Parser.parseJSON(getClass().getResource(invalidUrlAfterSubstitution), Server.class));
        assertThat(exception.getCause().getClass(), is(IllegalArgumentException.class));
        assertThat(exception.getCause().getMessage(), is("The 'url' field is not a valid URL"));
    }

    @Test
    @Tag("JSON")
    @DisplayName("missing variable in 'url' substitution")
    public void invalidSubstitution() {
        ParsingException exception = assertThrows(ParsingException.class, () -> Parser.parseJSON(getClass().getResource(invalidSubstitution), Server.class));
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

}
