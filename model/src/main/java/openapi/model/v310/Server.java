package openapi.model.v310;

import javax.validation.constraints.NotNull;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Server(@NotNull String url, String description, Map<String, ServerVariable> variables) {

    private static Pattern pattern = Pattern.compile("\\{(.+?)\\}");

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
