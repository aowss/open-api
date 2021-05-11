package openapi.model.v310;

import java.util.Map;

//  TODO: should operationRef be a URI ?
public record Link(String operationRef, String operationId, Map<String, String> parameters, Object requuestBody, String description, Server server) {
    public Link {
        if (operationRef != null && operationId != null) throw new IllegalArgumentException("An 'link' Object can't have both an 'operationRef' and an 'operationId' field");
    }
}
