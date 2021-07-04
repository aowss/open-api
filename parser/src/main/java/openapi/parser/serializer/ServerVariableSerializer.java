package openapi.parser.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import openapi.model.v310.ServerVariable;

import java.io.IOException;

public class ServerVariableSerializer extends StdSerializer<ServerVariable> {

    public ServerVariableSerializer() {
        this(null);
    }

    public ServerVariableSerializer(Class<ServerVariable> vc) {
        super(vc);
    }

    @Override
    public void serialize(ServerVariable serverVariable, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        if (serverVariable.enums() != null && serverVariable.enums().size() != 0) {
            jsonGenerator.writeFieldName("enum");
            jsonGenerator.writeStartArray();
            for (String value : serverVariable.enums()) {
                jsonGenerator.writeString(value);
            }
            jsonGenerator.writeEndArray();
        }
        jsonGenerator.writeStringField("default", serverVariable.defaultValue());
        if (serverVariable.description() != null && !serverVariable.description().isEmpty()) jsonGenerator.writeStringField("description", serverVariable.description());
        jsonGenerator.writeEndObject();
    }
}
