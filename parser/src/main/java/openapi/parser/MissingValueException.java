package openapi.parser;

import java.util.List;
import java.util.stream.Collectors;

public final class MissingValueException extends ParsingException {

    private List<String> path;

    private static final String message = "The value(s) at location(s) '%s' is/are required";

    public MissingValueException(String path) {
        this(List.of(path), null);
    }

    public MissingValueException(List<String> paths, Throwable cause) {
        super(String.format(message, paths.stream().collect(Collectors.joining(", "))), cause);
        this.path = paths;
    }

    public List<String> getPaths() {
        return path;
    }

}
