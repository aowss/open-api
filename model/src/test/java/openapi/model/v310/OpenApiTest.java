package openapi.model.v310;

import com.fasterxml.jackson.databind.ObjectMapper;
import openapi.model.v310.security.OAuth2;
import openapi.model.v310.security.Type;
import openapi.model.v310.security.oauth.Implicit;
import openapi.model.v310.security.oauth.OAuthFlowType;
import openapi.model.v310.utils.Parser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@DisplayName("OpenAPI Object : https://github.com/OAI/OpenAPI-Specification/blob/main/versions/3.1.0.md#oasObject")
public class OpenApiTest {

    static String allFieldsJSON = "/Full/all-fields.json";
    static String allFieldsYAML = "/Full/all-fields.yaml";

    static final ObjectMapper jsonMapper = Parser.jsonMapper;
    static final ObjectMapper yamlMapper = Parser.yamlMapper;

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

//    @Test
//    @Tag("JSON")
//    @DisplayName("All fields [JSON]")
//    public void allFieldsJSON() throws IOException {
//        OpenApi openApi = jsonMapper.readValue(getClass().getResource(allFieldsJSON), OpenApi.class);
//        validateAllFields(openApi);
//    }

    @Test
    @Tag("YAML")
    @DisplayName("All fields [YAML]")
    public void allFieldsYAML() throws IOException {
        OpenApi openApi = yamlMapper.readValue(getClass().getResource(allFieldsYAML), OpenApi.class);
        validateAllFields(openApi);
    }

    public void validateAllFields(OpenApi openApi) throws MalformedURLException {
        assertThat(openApi.openapi(), is(new Version(3,1,0)));
        assertThat(openApi.info().title(), is("Sample Pet Store App"));
        assertThat(openApi.servers().size(), is(1));
        assertThat(openApi.servers().get(0).effectiveUrl(), is(new URL("https://demo.gigantic-server.com:8443/v2")));
        assertThat(openApi.paths().size(), is(1));
        assertThat(openApi.paths().get("/pets").get().summary(), is("Find pets by ID"));
        assertThat(openApi.paths().get("/pets").put().tags().get(0), is("pet"));
        assertThat(openApi.paths().get("/pets").post(), nullValue());
        assertThat(openApi.paths().get("/pets").parameters().size(), is(1));
        assertThat(openApi.paths().get("/pets").parameters().get(0).name(), is("id"));
        assertThat(openApi.webhooks().size(), is(1));
        assertThat(openApi.webhooks().get("newPet").post().requestBody().description(), is("Information about a new pet in the system"));
        assertThat(openApi.components().parameters().size(), is(2));
        assertThat(openApi.components().parameters().get("skipParam").in(), is(Location.query));
        assertThat(openApi.components().responses().size(), is(3));
        assertThat(openApi.components().responses().get("GeneralError").content().size(), is(1));
        assertThat(openApi.components().securitySchemes().size(), is(2));
        assertThat(openApi.components().securitySchemes().get("petstore_auth").type(), is(Type.oauth2));
        OAuth2 oauth2 = ((OAuth2)openApi.components().securitySchemes().get("petstore_auth"));
        assertThat(oauth2.flows().size(), is(1));
        assertThat(((Implicit)oauth2.flows().get(OAuthFlowType.implicit)).scopes().size(), is(2));
        assertThat(openApi.security().size(), is(1));
        assertThat(openApi.security().get(0).size(), is(1));
        assertThat(openApi.security().get(0).get("petstore_auth"), is(List.of("write:pets", "read:pets")));
        assertThat(openApi.tags().size(), is(1));
        assertThat(openApi.tags().get(0).name(), is("pet"));
        assertThat(openApi.externalDocs().description(), is("Find more info here"));
    }

}
