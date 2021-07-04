package openapi.parser.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import openapi.model.v310.security.Scheme;

import java.io.IOException;

public class SchemeSerializer extends StdSerializer<Scheme> {

    public SchemeSerializer() {
        this(null);
    }

    public SchemeSerializer(Class<Scheme> t) {
        super(t);
    }

    @Override
    public void serialize(Scheme scheme, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(scheme.name().toLowerCase());
    }

}
