package openapi.model.v310;

import openapi.parser.Parser;
import openapi.parser.ParsingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Link Object : https://github.com/OAI/OpenAPI-Specification/blob/main/versions/3.1.0.md#link-object")
class LinkTest {

    static String allFieldsOperationRefJSON = "/Link/all-fields-operation-ref.json";
    static String allFieldsOperationIdJSON = "/Link/all-fields-operation-id.json";
    static String allFieldsOperationRefYAML = "/Link/all-fields-operation-ref.yaml";
    static String allFieldsOperationIdYAML = "/Link/all-fields-operation-id.yaml";
    static String missingOperationIdentifier = "/Link/missing-operation-identifier.json";

    @Test
    @Tag("JSON")
    @DisplayName("All fields with operation ref [JSON]")
    public void allFieldsOperationRefJSON() throws Exception {
        Link link = Parser.parseJSON(getClass().getResource(allFieldsOperationRefJSON), Link.class);
        assertThat(link.operationRef(), is(new URL("https://na2.gigantic-server.com/#/paths/some-path/get")));
        validateAllFields(link);
    }

    @Test
    @Tag("JSON")
    @DisplayName("All fields with operation id [JSON]")
    public void allFieldsOperationIdJSON() throws Exception {
        Link link = Parser.parseJSON(getClass().getResource(allFieldsOperationIdJSON), Link.class);
        assertThat(link.operationId(), is("getUserInfo"));
        validateAllFields(link);
    }

    @Test
    @Tag("YAML")
    @DisplayName("All fields with operation ref [YAML]")
    public void allFieldsOperationRefYAML() throws Exception {
        Link link = Parser.parseYAML(getClass().getResource(allFieldsOperationRefYAML), Link.class);
        assertThat(link.operationRef(), is(new URL("https://na2.gigantic-server.com/#/paths/some-path/get")));
        validateAllFields(link);
    }

    @Test
    @Tag("YAML")
    @DisplayName("All fields with operation ref [YAML]")
    public void allFieldsOperationIdYAML() throws Exception {
        Link link = Parser.parseYAML(getClass().getResource(allFieldsOperationIdYAML), Link.class);
        assertThat(link.operationId(), is("getUserInfo"));
        validateAllFields(link);
    }

    @Test
    @Tag("JSON")
    @DisplayName("Missing both operationRef and operationId")
    public void missingOperationIdentifier() {
        ParsingException exception = assertThrows(ParsingException.class, () -> Parser.parseJSON(getClass().getResource(missingOperationIdentifier), Link.class));
        assertThat(exception.getMessage(), startsWith("Cannot construct instance of `openapi.model.v310.Link`, problem: An 'link' Object must have either an 'operationRef' or an 'operationId' field"));
        assertThat(exception.getCause().getClass(), is(IllegalArgumentException.class));
        assertThat(exception.getCause().getMessage(), is("An 'link' Object must have either an 'operationRef' or an 'operationId' field"));
    }

    public void validateAllFields(Link link) {
        assertThat(link.parameters(), is(Map.of("key1", "value1", "key2", "value2")));
        assertThat(link.description(), is("the target link operation"));
        assertThat(link.server().url(), is("https://{username}.gigantic-server.com:{port}/{basePath}"));
        assertThat(link.server().description(), is("The production API server"));
        assertThat(link.server().variables().size(), is(3));
        assertThat(link.server().variables().get("username"), is(new ServerVariable(null, "demo", "this value is assigned by the service provider, in this example `gigantic-server.com`")));
        assertThat(link.server().variables().get("port"), is(new ServerVariable(List.of("8443", "443"), "8443", null)));
        assertThat(link.server().variables().get("basePath"), is(new ServerVariable(null, "v2", null)));
    }
}