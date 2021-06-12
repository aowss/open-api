package openapi.model.v310;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.Map;

/**
 * The Link object represents a possible design-time link for a response. The presence of a link does not guarantee the caller's ability to successfully invoke it, rather it provides a known relationship and traversal mechanism between responses and other operations.
 * Unlike dynamic links (i.e. links provided in the response payload), the OAS linking mechanism does not require link information in the runtime response.
 * For computing links, and providing instructions to execute them, a runtime expression is used for accessing values in an operation and using them as parameters while invoking the linked operation.
 *
 * @param operationRef A relative or absolute URI reference to an OAS operation.
 *                     This field is mutually exclusive of the {@code operationId} field, and MUST point to an {@link Operation} Object. Relative {@code operationRef} values MAY be used to locate an existing {@link Operation} Object in the OpenAPI definition.
 * @param operationId  The name of an existing, resolvable OAS operation, as defined with a unique operationId.
 *                     This field is mutually exclusive of the {@code operationRef} field.
 * @param parameters   A map representing parameters to pass to an operation as specified with {@code operationId} or identified via {@code operationRef}.
 *                     The key is the parameter name to be used, whereas the value can be a constant or an expression to be evaluated and passed to the linked operation.
 *                     The parameter name can be qualified using the parameter location {@code [{in}.]{name}} for operations that use the same parameter name in different locations (e.g. {@code path.id}).
 * @param requestBody  A literal value or {expression} to use as a request body when calling the target operation.
 * @param description  A description of the link. CommonMark syntax MAY be used for rich text representation.
 * @param server       A server object to be used by the target operation.
 */
public record Link(URI operationRef, String operationId, Map<String, String> parameters, Object requestBody, String description, Server server) {

    /**
     * Creates an instance of a {@code Link} record class.
     *
     * @param operationRef A relative or absolute URI reference to an OAS operation.
     *                     This field is mutually exclusive of the {@code operationId} field, and MUST point to an {@link Operation} Object. Relative {@code operationRef} values MAY be used to locate an existing {@link Operation} Object in the OpenAPI definition.
     * @param operationId  The name of an existing, resolvable OAS operation, as defined with a unique operationId.
     *                     This field is mutually exclusive of the {@code operationRef} field.
     * @param parameters   A map representing parameters to pass to an operation as specified with {@code operationId} or identified via {@code operationRef}.
     *                     The key is the parameter name to be used, whereas the value can be a constant or an expression to be evaluated and passed to the linked operation.
     *                     The parameter name can be qualified using the parameter location {@code [{in}.]{name}} for operations that use the same parameter name in different locations (e.g. {@code path.id}).
     * @param requestBody  A literal value or {expression} to use as a request body when calling the target operation.
     * @param description  A description of the link. CommonMark syntax MAY be used for rich text representation.
     * @param server       A server object to be used by the target operation.
     * @throws IllegalArgumentException if both {@code operationRef} and {@code operationId} are specified or
     *                                  if both {@code operationRef} and {@code operationId} are not specified
     */
    public Link {
        if (operationRef != null && operationId != null) throw new IllegalArgumentException("A 'link' Object can't have both an 'operationRef' and an 'operationId' field");
        if (operationRef == null && (operationId == null || operationId.isBlank())) throw new IllegalArgumentException("A 'link' Object must have either an 'operationRef' or an 'operationId' field");
    }

    /**
     * Creates an instance of a {@code Link} record class where the link will be resolved using the {@code operationRef} value to locate an existing {@link Operation} Object.
     *
     * @param operationRef A relative or absolute URI reference to an OAS operation.
     *                     This field is mutually exclusive of the {@code operationId} field, and MUST point to an {@link Operation} Object. Relative {@code operationRef} values MAY be used to locate an existing {@link Operation} Object in the OpenAPI definition.
     * @param parameters   A map representing parameters to pass to an operation as specified with {@code operationId} or identified via {@code operationRef}.
     *                     The key is the parameter name to be used, whereas the value can be a constant or an expression to be evaluated and passed to the linked operation.
     *                     The parameter name can be qualified using the parameter location {@code [{in}.]{name}} for operations that use the same parameter name in different locations (e.g. {@code path.id}).
     * @param requestBody  A literal value or {expression} to use as a request body when calling the target operation.
     * @param description  A description of the link. CommonMark syntax MAY be used for rich text representation.
     * @param server       A server object to be used by the target operation.
     */
    public Link(@NotNull URI operationRef, Map<String, String> parameters, Object requestBody, String description, Server server) {
        this(operationRef, null, parameters, requestBody, description, server);
    }

    /**
     * Creates an instance of a {@code Link} record class where the link will be resolved using the {@code operationId} value to locate an existing {@link Operation} Object.
     *
     * @param operationId  The name of an existing, resolvable OAS operation, as defined with a unique operationId.
     *                     This field is mutually exclusive of the {@code operationRef} field.
     * @param parameters   A map representing parameters to pass to an operation as specified with {@code operationId} or identified via {@code operationRef}.
     *                     The key is the parameter name to be used, whereas the value can be a constant or an expression to be evaluated and passed to the linked operation.
     *                     The parameter name can be qualified using the parameter location {@code [{in}.]{name}} for operations that use the same parameter name in different locations (e.g. {@code path.id}).
     * @param requestBody  A literal value or {expression} to use as a request body when calling the target operation.
     * @param description  A description of the link. CommonMark syntax MAY be used for rich text representation.
     * @param server       A server object to be used by the target operation.
     */
    public Link(@NotNull String operationId, Map<String, String> parameters, Object requestBody, String description, Server server) {
        this(null, operationId, parameters, requestBody, description, server);
    }

}
