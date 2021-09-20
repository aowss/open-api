package openapi.model.v310.builder;

import openapi.model.v310.XML;

import java.net.URI;

/**
 * Builder for {@link XML}
 */
public class XMLBuilder {

    private String name;
    private URI namespace;
    private String prefix;
    private boolean attribute = false;
    private boolean wrapped = false;

    public XMLBuilder() {
    }

    public XMLBuilder name(String name) {
        this.name = name;
        return this;
    }

    public XMLBuilder namespace(URI namespace) {
        this.namespace = namespace;
        return this;
    }

    public XMLBuilder prefix(String prefix) {
        this.prefix = prefix;
        return this;
    }

    public XMLBuilder attribute(boolean attribute) {
        this.attribute = attribute;
        return this;
    }

    public XMLBuilder wrapped(boolean wrapped) {
        this.wrapped = wrapped;
        return this;
    }

    public XML build() {
        return new XML(this.name, this.namespace, this.prefix, this.attribute, this.wrapped);
    }

}
