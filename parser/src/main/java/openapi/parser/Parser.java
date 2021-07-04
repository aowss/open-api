package openapi.parser;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import openapi.model.v310.*;
import openapi.model.v310.security.*;
import openapi.model.v310.security.oauth.*;
import openapi.parser.deserializer.*;
import openapi.parser.serializer.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

public class Parser {

    //  TODO: check if we need 2 mappers
    private static final ObjectMapper jsonMapper = new ObjectMapper();
    private static final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    static {
        //  Didn't manage to make the mixin work with records
        SimpleModule module = new SimpleModule();
        //  ServerVariable
        module.addDeserializer(ServerVariable.class, new ServerVariableDeserializer());
        module.addSerializer(ServerVariable.class, new ServerVariableSerializer());
        //  Version
        module.addDeserializer(Version.class, new VersionDeserializer());
        module.addSerializer(Version.class, new VersionSerializer());
        //  SecurityScheme
        module.addDeserializer(SecurityScheme.class, new SecuritySchemeDeserializer());
//        module.addSerializer(SecurityScheme.class, new SecuritySchemeSerializer());
        module.setSerializerModifier(new SecuritySchemeBeanSerializerModifier());
//        module.addDeserializer(Scheme.class, new SchemeDeserializer());
        module.addSerializer(Scheme.class, new SchemeSerializer());
        //  OAuthFlow
        module.addDeserializer(OAuthFlow.class, new OAuthFlowDeserializer());

        jsonMapper.registerModule(module);
        jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        yamlMapper.registerModule(module);
        yamlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
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

    public static final void writeJSON(Writer writer, Object object) throws ParsingException, IOException {
        write(jsonMapper, writer, object);
    }

    public static final void writeYAML(Writer writer, Object object) throws ParsingException, IOException {
        write(yamlMapper, writer, object);
    }

    private static void write(ObjectMapper mapper, Writer writer, Object object) throws ParsingException, IOException {
        try {
            mapper.writeValue(writer, object);
        } catch (JsonGenerationException | JsonMappingException exc) {
            throw new ParsingException(exc.getMessage(), exc);
        } catch (IOException ioe) {
            throw ioe;
        }
    }

}
