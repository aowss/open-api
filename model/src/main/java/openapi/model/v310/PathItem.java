package openapi.model.v310;

import java.util.List;
import java.util.Map;

enum Method {
    get, put, post, delete, options, head, patch, trace
}

public record PathItem(String summary, String description, Operation get, Operation put, Operation post, Operation delete, Operation options, Operation head, Operation patch, Operation trace, List<Server> servers, List<Parameter> parameters) {
    public PathItem(String summary, String description, Map<Method, Operation> operations, List<Server> servers, List<Parameter> parameters) {
        this(summary, description, operations.get(Method.get), operations.get(Method.put), operations.get(Method.post), operations.get(Method.delete), operations.get(Method.options), operations.get(Method.head), operations.get(Method.patch), operations.get(Method.trace), servers, parameters);
    }
}