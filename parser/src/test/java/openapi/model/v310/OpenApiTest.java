package openapi.model.v310;

import openapi.model.v310.security.*;
import openapi.model.v310.security.oauth.*;

import openapi.parser.Parser;
import openapi.parser.ParsingException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@DisplayName("OpenAPI Object : https://github.com/OAI/OpenAPI-Specification/blob/main/versions/3.1.0.md#oasObject")
public class OpenApiTest {

    static String allFieldsJSON = "/Full/all-fields.json";
    static String allFieldsYAML = "/Full/all-fields.yaml";

    private static SerializationTester serializationTester = new SerializationTester();

//    @Test
//    @Tag("JSON")
//    @DisplayName("All fields [JSON]")
//    public void allFieldsJSON() throws IOException {
//        OpenApi openApi = Parser.parseJSON(getClass().getResource(allFieldsJSON), OpenApi.class);
//        validateAllFields(openApi);
//    }

    @Test
    @Tag("YAML")
    @DisplayName("All fields [YAML]")
    public void allFieldsYAML() throws IOException, ParsingException {
        OpenApi openApi = Parser.parseYAML(getClass().getResource(allFieldsYAML), OpenApi.class);
        validateAllFields(openApi);
        serializationTester.checkYAMLSerialization(openApi, allFieldsYAML);
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
        Map<String, SecurityScheme> securitySchemes = openApi.components().securitySchemes();
        assertThat(securitySchemes.size(), is(4));
        //  API Key
        assertThat(securitySchemes.get("api_key").type(), is(Type.apiKey));
        ApiKey apiKey = ((ApiKey)securitySchemes.get("api_key"));
        assertThat(apiKey.name(), is("api_key"));
        assertThat(apiKey.in(), is(Location.header));
        //  HTTP Basic
        assertThat(securitySchemes.get("http_basic").type(), is(Type.http));
        Http httpBasic = ((Http)securitySchemes.get("http_basic"));
        assertThat(httpBasic.scheme(), is(Scheme.Basic));
        //  HTTP Bearer
        assertThat(securitySchemes.get("http_bearer").type(), is(Type.http));
        Http httpBearer = ((Http)securitySchemes.get("http_bearer"));
        assertThat(httpBearer.scheme(), is(Scheme.Bearer));
        assertThat(httpBearer.bearerFormat(), is("JWT"));
        //  OAuth 2
        assertThat(securitySchemes.get("petstore_auth").type(), is(Type.oauth2));
        OAuth2 oauth2 = ((OAuth2)securitySchemes.get("petstore_auth"));
        assertThat(oauth2.flows().size(), is(4));
        //  Implicit Flow
        Implicit implicit = ((Implicit)oauth2.flows().get(OAuthFlowType.implicit));
        assertThat(implicit.authorizationUrl(), is(new URL("https://example.org/api/oauth/dialog")));
        assertThat(implicit.refreshUrl(), is(new URL("https://example.com/api/oauth/refresh")));
        assertThat(implicit.scopes(), is(Map.of("write:pets", "modify pets in your account", "read:pets", "read your pets")));
        //  Password Flow
        Password password = ((Password)oauth2.flows().get(OAuthFlowType.password));
        assertThat(password.tokenUrl(), is(new URL("https://example.com/api/oauth/token")));
        assertThat(password.refreshUrl(), is(new URL("https://example.com/api/oauth/refresh")));
        assertThat(password.scopes(), is(Map.of("write:pets", "modify pets in your account", "read:pets", "read your pets")));
        //  Password Flow
        ClientCredentials clientCredentials = ((ClientCredentials)oauth2.flows().get(OAuthFlowType.clientCredentials));
        assertThat(clientCredentials.tokenUrl(), is(new URL("https://example.com/api/oauth/token")));
        assertThat(clientCredentials.refreshUrl(), is(new URL("https://example.com/api/oauth/refresh")));
        assertThat(clientCredentials.scopes(), is(Map.of("write:pets", "modify pets in your account", "read:pets", "read your pets")));
        //  Authorization Code Flow
        AuthorizationCode authorizationCode = ((AuthorizationCode)oauth2.flows().get(OAuthFlowType.authorizationCode));
        assertThat(authorizationCode.authorizationUrl(), is(new URL("https://example.com/api/oauth/dialog")));
        assertThat(authorizationCode.tokenUrl(), is(new URL("https://example.com/api/oauth/token")));
        assertThat(authorizationCode.refreshUrl(), is(new URL("https://example.com/api/oauth/refresh")));
        assertThat(authorizationCode.scopes(), is(Map.of("write:pets", "modify pets in your account", "read:pets", "read your pets")));

        assertThat(openApi.security().size(), is(1));
        assertThat(openApi.security().get(0).size(), is(1));
        assertThat(openApi.security().get(0).get("petstore_auth"), is(List.of("write:pets", "read:pets")));
        assertThat(openApi.tags().size(), is(1));
        assertThat(openApi.tags().get(0).name(), is("pet"));
        assertThat(openApi.externalDocs().description(), is("Find more info here"));
    }

}
