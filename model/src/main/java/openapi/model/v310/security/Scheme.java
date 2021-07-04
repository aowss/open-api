package openapi.model.v310.security;

public enum Scheme {
    Basic("Basic"),
    Bearer("Bearer"),
    Digest("Digest"),
    HOBA("HOBA"),
    Mutual("Mutual"),
    Negotiate("Negotiate"),
    OAuth("OAuth"),
    SCRAM_SHA_1("SCRAM-SHA-1"),
    SCRAM_SHA_256("SCRAM-SHA-256"),
    vapid("vapid");

    private final String name;

    Scheme(String name) {
        this.name = name;
    }

    public static Scheme from(String name) {
        for (Scheme scheme : Scheme.values()) {
            if (scheme.name.equalsIgnoreCase(name)) return scheme;
        }
        return null;
    }

}
