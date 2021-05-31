package openapi.parser;

public final class InvalidValueException extends ParsingException {

    private String invalidValue;
    private String path;
    //  TODO: should this be changed to a class ?
    private String expectedType;

    private static final String message = "The value '%s' at location '%s' is invalid: it should be a '%s'";

    public InvalidValueException(String invalidValue, String path, String expectedType) {
        this(invalidValue, path, expectedType, null);
    }

    public InvalidValueException(String invalidValue, String path, String expectedType, Throwable cause) {
        super(String.format(message, invalidValue, path, expectedType), cause);
        this.invalidValue = invalidValue;
        this.path = path;
        this.expectedType = expectedType;
    }

    public String getInvalidValue() {
        return invalidValue;
    }

    public String getPath() {
        return path;
    }

    public String getExpectedType() {
        return expectedType;
    }

}
