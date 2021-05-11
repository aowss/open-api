package openapi.model.v310;

import java.util.List;
import java.util.Map;

import static openapi.model.v310.Method.*;

enum Method {
    GET, PUT, POST, DELETE, OPTIONS, HEAD, PATCH, TRACE
}

//  TODO: what should we do with $ref
public record PathItem(String summary, String description, Map<Method, Operation> operations, List<Server> servers, List<Parameter> parameters) {
    Operation get() {
        return operations.get(GET);
    }
    Operation put() {
        return operations.get(PUT);
    }
    Operation post() {
        return operations.get(POST);
    }
    Operation delete() {
        return operations.get(DELETE);
    }
    Operation options() {
        return operations.get(OPTIONS);
    }
    Operation head() {
        return operations.get(HEAD);
    }
    Operation patch() {
        return operations.get(PATCH);
    }
    Operation trace() {
        return operations.get(TRACE);
    }
}
