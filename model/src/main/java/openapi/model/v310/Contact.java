package openapi.model.v310;

import javax.validation.constraints.Email;
import java.net.URL;

/**
 * Contact information for the exposed API.
 *
 * @param name  The identifying name of the contact person/organization.
 * @param url   The URL pointing to the contact information.
 * @param email The email address of the contact person/organization.
 */
public record Contact(String name, URL url, @Email String email) {
}
