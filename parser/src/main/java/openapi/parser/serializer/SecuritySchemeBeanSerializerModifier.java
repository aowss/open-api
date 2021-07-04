package openapi.parser.serializer;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import openapi.model.v310.security.SecurityScheme;

public class SecuritySchemeBeanSerializerModifier extends BeanSerializerModifier {

    @Override
    public JsonSerializer<?> modifySerializer(SerializationConfig config, BeanDescription beanDesc, JsonSerializer<?> serializer) {
        var interfaces = beanDesc.getBeanClass().getInterfaces();
        if (interfaces != null && interfaces.length == 1 && interfaces[0] == SecurityScheme.class) return new SecuritySchemeSerializer((JsonSerializer<Object>) serializer);
        return serializer;
    }

}
