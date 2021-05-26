package openapi.model.v310;

import javax.validation.constraints.NotNull;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An object representing a Server.
 *
 * @param url A URL to the target host. This URL supports Server Variables and MAY be relative, to indicate that the host location is relative to the location where the OpenAPI document is being served. Variable substitutions will be made when a variable is named in {brackets}.
 * @param description An optional string describing the host designated by the URL. CommonMark syntax MAY be used for rich text representation.
 * @param variables A map between a variable name and its value. The value is used for substitution in the server's URL template.
 */
public record Server(@NotNull String url, String description, Map<String, ServerVariable> variables) {

    private static Pattern pattern = Pattern.compile("\\{(.+?)\\}");

    /**
     * Creates an instance of a {@code Server} record class.
     *
     * @param url A URL to the target host. This URL supports Server Variables and MAY be relative, to indicate that the host location is relative to the location where the OpenAPI document is being served. Variable substitutions will be made when a variable is named in {brackets}.
     * @param description An optional string describing the host designated by the URL. CommonMark syntax MAY be used for rich text representation.
     * @param variables A map between a variable name and its value. The value is used for substitution in the server's URL template.
     * @throws IllegalArgumentException if the {@code url} uses variable substitutions and the {@code variables} parameter is {@code null} or empty; <br/>
     *                                  if the {@code url} uses variable substitutions and the {@code variables} doesn't define some of the variables; <br/>
     *                                  if the {@code url}, after variable substitutions, is not a valid URL.
     */
    public Server(@NotNull String url, String description, Map<String, ServerVariable> variables) {
        if (url != null && url.contains("{")) {
            if (variables == null || variables.isEmpty()) throw new IllegalArgumentException("The 'url' field can't use variable substitutions if the 'variables' field is not present");
            Matcher matcher = pattern.matcher(url);
            List<String> matches = new ArrayList<>();
            while (matcher.find()) {
                matches.add(matcher.group(1));
            }
            if (!variables.keySet().containsAll(matches)) throw new IllegalArgumentException("The 'url' field uses substitution variables that are not defined in the 'variables' field");

            matcher.reset();
            StringBuffer buffer = new StringBuffer();
            while (matcher.find()) {
                matcher.appendReplacement(buffer, variables.get(matcher.group(1)).defaultValue());
            }
            matcher.appendTail(buffer);
        }
        this.url = url;
        this.description = description;
        this.variables = variables;
        //  Check that the resulting URL is a valid URL
        effectiveUrl();
    }

    /**
     * Returns the URL after variable substitutions, if any, are made.
     * @return the URL
     */
    public final URL effectiveUrl() {
        if (url == null) return null;
        String effectiveUrl = url;
        if (url.contains("{")) {
            Matcher matcher = pattern.matcher(url);
            StringBuffer buffer = new StringBuffer();
            while (matcher.find()) {
                matcher.appendReplacement(buffer, variables.get(matcher.group(1)).defaultValue());
            }
            matcher.appendTail(buffer);
            effectiveUrl = buffer.toString();
        }
        try {
            return new URL(effectiveUrl);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("The 'url' field is not a valid URL");
        }
    }

}
