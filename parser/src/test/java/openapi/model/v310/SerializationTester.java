package openapi.model.v310;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.fge.jsonpatch.diff.JsonDiff;
import openapi.model.v310.security.Scheme;
import openapi.parser.Parser;
import openapi.parser.ParsingException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

import static java.util.function.Predicate.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SerializationTester {

    private static ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    public void checkJSONSerialization(Object model, String filePath) throws IOException, ParsingException {
        File output = getFile(filePath);
        Parser.writeJSON(new FileWriter(output), model);
        checkDifferences(filePath, output);
    }

    public void checkYAMLSerialization(Object model, String filePath) throws IOException, ParsingException {
        File output = getFile(filePath);
        Parser.writeYAML(new FileWriter(output), model);
        checkDifferences(filePath, output);
    }

    private void checkDifferences(String filePath, File output) throws IOException {
        /*
        //  This comparison isn't good enough because it doesn't catch differences like <code>"scheme": "Bearer"</code> and <code>"scheme": "bearer"</code>
        var readModel = Parser.parseYAML(output.toURL(), model.getClass());
        assertThat(model, is(readModel));
        //  This comparison doesn't work because of default values and null schemas
        assertEquals(mapper.readTree(getClass().getResource(filePath)), mapper.readTree(output));
        */

        //  Differences between the 2 files, as a JSONPatch document
        ArrayNode patch = (ArrayNode)JsonDiff.asJson(mapper.readTree(getClass().getResource(filePath)), mapper.readTree(output));
        Iterable<JsonNode> iterable = () -> patch.elements();
        var nodes = StreamSupport.stream(iterable.spliterator(), false)
                .filter(not(isSchema))
                .filter(not(isDefaultValue))
                .filter(not(isAlsoDefaultValue))
                .filter(not(isCaseInsensitiveScheme))
                .toList();
        assertThat(nodes.size(), is(0));

    }

    private File getFile(String filePath) {
        int index = filePath.lastIndexOf("/");
        //  This creates the file in the target/test-classes directory
        Path directory = Paths.get(getClass().getResource(filePath.substring(0, index)).getPath());
        File output = Paths.get(directory.toAbsolutePath() + "/out-" + filePath.substring(index + 1)).toFile();
        return output;
    }

    //  Schemas are not implemented yet
    Predicate<JsonNode> isSchema = operation -> operation.get("op").asText().equals("remove") && operation.get("path").asText().endsWith("/schema");

    //  Default values
    Predicate<JsonNode> isDefaultValue = operation -> operation.get("op").asText().equals("add") &&
        (
            //  Default style for query parameters is form
            ( operation.get("path").asText().equals("/components/parameters/limitParam/style") && operation.get("value").asText().equals("form") ) ||
            ( operation.get("path").asText().equals("/components/parameters/skipParam/style") && operation.get("value").asText().equals("form") ) ||
            //  Default explode for simple style is false
            ( operation.get("path").asText().equals("/paths/~1pets/parameters/0/explode") && operation.get("value").asText().equals("false") ) ||
            ( operation.get("path").asText().equals("/paths/~1pets/put/parameters/0/explode") && operation.get("value").asText().equals("false") )
        );

    //  Default values that are handled strangely by the library
    Predicate<JsonNode> isAlsoDefaultValue = operation -> operation.get("op").asText().equals("copy") &&
        (
            //  Default explode for form style is true
            ( operation.get("path").asText().equals("/components/parameters/limitParam/explode") && operation.get("from").asText().equals("/paths/~1pets/put/parameters/0/required") ) ||
            ( operation.get("path").asText().equals("/components/parameters/skipParam/explode") && operation.get("from").asText().equals("/paths/~1pets/put/parameters/0/required") ) ||
            //  Default style for path parameters is simple
            ( operation.get("path").asText().equals("/paths/~1pets/put/parameters/0/style") && operation.get("from").asText().equals("/paths/~1pets/parameters/0/style") )
        );

    //  Security Schemes are case insensitive
    Predicate<JsonNode> isCaseInsensitiveScheme = operation -> operation.get("op").asText().equals("replace") &&
        operation.get("path").asText().startsWith("/components/securitySchemes") &&
        operation.get("path").asText().endsWith("/scheme") &&
        Scheme.from(operation.get("value").asText()) != null;

}
