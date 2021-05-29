package openapi.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import openapi.model.v310.*;
import openapi.model.v310.security.*;
import openapi.model.v310.security.oauth.*;
import openapi.parser.deserializer.OAuthFlowDeserializer;
import openapi.parser.deserializer.SecuritySchemeDeserializer;
import openapi.parser.deserializer.ServerVariableDeserializer;
import openapi.parser.deserializer.VersionDeserializer;

import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.net.URL;

public class Parser {

    public static final ObjectMapper jsonMapper = new ObjectMapper();
    public static final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
    public static final Validator factory = Validation.buildDefaultValidatorFactory().getValidator();

    static {
        //  Didn't manage to make the mixin work with records
        SimpleModule module = new SimpleModule();
        module.addDeserializer(ServerVariable.class, new ServerVariableDeserializer());
        module.addDeserializer(Version.class, new VersionDeserializer());
        module.addDeserializer(SecurityScheme.class, new SecuritySchemeDeserializer());
        module.addDeserializer(OAuthFlow.class, new OAuthFlowDeserializer());
        jsonMapper.registerModule(module);
        yamlMapper.registerModule(module);
    }

    public static final <T> T parseJSON(URL src, Class<T> valueType) throws IOException {
        return jsonMapper.readValue(src, valueType);
    }

    public static final <T> T parseYAML(URL src, Class<T> valueType) throws IOException {
        return yamlMapper.readValue(src, valueType);
    }

}
