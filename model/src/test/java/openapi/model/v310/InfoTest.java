package openapi.model.v310;

import openapi.model.v310.builder.InfoBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class InfoTest {

    @Test
    @Tag("Builder")
    @DisplayName("Hand-written external Builder")
    public void external() {
        Info info = new InfoBuilder("title", "version")
                .description("decription")
                .build();
    }

}
