package openapi.parser.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import openapi.model.v310.Version;

import java.io.IOException;

public class VersionSerializer extends StdSerializer<Version> {

    public VersionSerializer() {
        this(null);
    }

    public VersionSerializer(Class<Version> t) {
        super(t);
    }

    @Override
    public void serialize(Version version, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(version.major() + "." + version.minor() + "." + version.patch());
    }

}
