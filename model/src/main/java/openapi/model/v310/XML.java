package openapi.model.v310;

import java.net.URI;

public record XML(String name, URI namespace, String prefix, Boolean attribute, Boolean wrapped) {
    public XML {
        if (attribute == null) attribute = false;
        if (wrapped == null) wrapped = false;
    }
}
