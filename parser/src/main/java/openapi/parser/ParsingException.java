package openapi.parser;

public sealed class ParsingException extends Exception permits InvalidValueException, MissingValueException {
    public ParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
