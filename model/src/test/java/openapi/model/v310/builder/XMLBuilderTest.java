package openapi.model.v310.builder;

import openapi.model.v310.XML;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class XMLBuilderTest {

    @Test
    @DisplayName("All values")
    public void allValues() throws URISyntaxException {
        XML xml = new XML("name", new URI("namespace"), "prefix", true, false);
        XML builder = new XMLBuilder()
            .name("name")
            .namespace(new URI("namespace"))
            .prefix("prefix")
            .attribute(true)
            .wrapped(false)
            .build();
        assertThat(builder, is(xml));
    }

}
