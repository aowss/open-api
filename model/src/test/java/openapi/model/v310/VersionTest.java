package openapi.model.v310;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class VersionTest {

    @Test
    @DisplayName("pre-release patch")
    public void preReleasePath() {
        Version version = new Version("3.1.0-rc0");
        assertThat(version.major(), is(3));
        assertThat(version.minor(), is(1));
        assertThat(version.patch(), is("0-rc0"));
    }

}
