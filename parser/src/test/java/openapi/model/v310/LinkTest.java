package openapi.model.v310;

import openapi.parser.Parser;
import openapi.parser.ParsingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Link Object : https://github.com/OAI/OpenAPI-Specification/blob/main/versions/3.1.0.md#link-object")
class LinkTest {

    static String allFieldsOperationRefJSON = "/Link/all-fields-operationRef.json";
    static String allFieldsOperationIdJSON = "/Link/all-fields-operationId.json";
    static String allFieldsOperationRefYAML = "/Link/all-fields-operationRef.yaml";
    static String allFieldsOperationIdYAML = "/Link/all-fields-operationId.yaml";
    static String missingOperationIdentifier = "/Link/missing-fields.json";
    static String operationIdOperationRefJSON = "/Link/operationId-operationRef.json";

    @Test
    @Tag("JSON")
    @DisplayName("All fields with operation ref [JSON]")
    public void allFieldsOperationRefJSON() throws Exception {
        Link link = Parser.parseJSON(getClass().getResource(allFieldsOperationRefJSON), Link.class);
        assertThat(link.operationRef(), is(new URI("https://na2.gigantic-server.com/#/paths/some-path/get")));
        validateAllFields(link);
    }

    @Test
    @Tag("YAML")
    @DisplayName("All fields with operation ref [YAML]")
    public void allFieldsOperationRefYAML() throws Exception {
        Link link = Parser.parseYAML(getClass().getResource(allFieldsOperationRefYAML), Link.class);
        assertThat(link.operationRef(), is(new URI("https://na2.gigantic-server.com/#/paths/some-path/get")));
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
    @DisplayName("All fields with operation id [YAML]")
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
        assertThat(exception.getMessage(), startsWith("Cannot construct instance of `openapi.model.v310.Link`, problem: A 'link' Object must have either an 'operationRef' or an 'operationId' field"));
        assertThat(exception.getCause().getClass(), is(IllegalArgumentException.class));
        assertThat(exception.getCause().getMessage(), is("A 'link' Object must have either an 'operationRef' or an 'operationId' field"));
    }

    @Test
    @Tag("JSON")
    @DisplayName("Both operationRef and operationId provided")
    public void bothOperationIdentifiersProvided() {
        ParsingException exception = assertThrows(ParsingException.class, () -> Parser.parseJSON(getClass().getResource(operationIdOperationRefJSON), Link.class));
        assertThat(exception.getMessage(), startsWith("Cannot construct instance of `openapi.model.v310.Link`, problem: A 'link' Object can't have both an 'operationRef' and an 'operationId' field"));
        assertThat(exception.getCause().getClass(), is(IllegalArgumentException.class));
        assertThat(exception.getCause().getMessage(), is("A 'link' Object can't have both an 'operationRef' and an 'operationId' field"));
    }

    public void validateAllFields(Link link) {
        assertThat(link.parameters(), is(Map.of("key1", "value1", "key2", "value2")));
        assertThat(link.description(), is("the target link operation"));
        assertThat(link.server().url(), is("https://{username}.gigantic-server.com:{port}/{basePath}"));
    }

}