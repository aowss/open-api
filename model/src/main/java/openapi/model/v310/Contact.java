package openapi.model.v310;

import javax.validation.constraints.Email;
import java.net.URL;

public record Contact(String name, URL url, @Email String email) {
}
