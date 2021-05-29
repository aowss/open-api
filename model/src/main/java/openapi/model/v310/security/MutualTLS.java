package openapi.model.v310.security;

public record MutualTLS(String description) implements SecurityScheme {
    @Override
    public Type type() {
        return Type.mutualTLS;
    }
}

