package openapi.parser;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import openapi.model.v310.*;
import openapi.model.v310.security.*;
import openapi.model.v310.security.oauth.*;
import openapi.parser.deserializer.OAuthFlowDeserializer;
import openapi.parser.deserializer.SecuritySchemeDeserializer;
import openapi.parser.deserializer.ServerVariableDeserializer;
import openapi.parser.deserializer.VersionDeserializer;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

public class Parser {

    private static final ObjectMapper jsonMapper = new ObjectMapper();
    private static final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

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

    public static final <T> T parseJSON(URL src, Class<T> valueType) throws IOException, ParsingException {
        return parseAndValidate(jsonMapper, src, valueType);
    }

    public static final <T> T parseYAML(URL src, Class<T> valueType) throws IOException, ParsingException {
        return parseAndValidate(yamlMapper, src, valueType);
    }

    private static final <T> T parseAndValidate(ObjectMapper mapper, URL src, Class<T> valueType) throws IOException, ParsingException {
        try {
            T value = mapper.readValue(src, valueType);
            Set<ConstraintViolation<T>> violations = validator.validate(value);
            if (violations.size() == 0) {
                return value;
            } else {
                var firstViolation = violations.iterator().next();
                var firstAnnotation = firstViolation.getConstraintDescriptor().getAnnotation();
                if (firstAnnotation instanceof NotNull) {
                    List<String> paths = violations.stream()
                            .filter(violation -> violation.getConstraintDescriptor().getAnnotation() instanceof NotNull)
                            .map(violation -> violation.getPropertyPath().toString())
                            .collect(Collectors.toList());
                    throw new MissingValueException(paths, null);
                } else {    //  In this case, we just report the first violation
                    String invalidValue = firstViolation.getInvalidValue().toString();
                    String path = firstViolation.getPropertyPath().toString();
                    String type = firstViolation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();
                    throw new InvalidValueException(invalidValue, path, type);
                }
            }
        } catch (InvalidFormatException ife) {
            String invalidValue = ife.getValue().toString();
            String path = ife.getPath().stream().map(JsonMappingException.Reference::getFieldName).collect(joining("."));
            String type = ife.getTargetType().getSimpleName();
            throw new InvalidValueException(invalidValue, path, type);
        } catch (ValueInstantiationException vie) {
            throw new ParsingException(vie.getMessage(), vie.getCause());
        } catch (IOException ioe) {
            throw ioe;
        } catch (Exception e) {
            throw e;
        }
    }

}
