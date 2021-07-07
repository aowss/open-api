package openapi.model.v310;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class XMLTest {

    @Test
    @DisplayName("Default values")
    public void defaultValues() throws URISyntaxException {
        XML xml = new XML("name", new URI("namespace"), "prefix", null, null);
        assertThat(xml.attribute(), is(false));
        assertThat(xml.wrapped(), is(false));
    }

}
