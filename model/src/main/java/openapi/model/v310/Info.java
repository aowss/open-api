package openapi.model.v310;

import javax.validation.constraints.NotNull;
import java.net.URL;

public record Info(@NotNull String title, String summary, String description, URL termsOfService, Contact contact, License license, @NotNull String version) {
}
