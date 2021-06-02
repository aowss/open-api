package openapi.model.v310.builder;

import openapi.model.v310.Contact;
import openapi.model.v310.Info;
import openapi.model.v310.License;

import java.net.URL;

public class InfoBuilder {

    private final String title;
    private String summary;
    private String description;
    private URL termsOfService;
    private Contact contact;
    private License license;
    private final String version;

    public InfoBuilder(String title, String version) {
        this.title = title;
        this.version = version;
    }

    public InfoBuilder summary(String summary) {
        this.summary = summary;
        return this;
    }

    public InfoBuilder description(String description) {
        this.description = description;
        return this;
    }

    public InfoBuilder termsOfService(URL termsOfService) {
        this.termsOfService = termsOfService;
        return this;
    }

    public InfoBuilder contact(Contact contact) {
        this.contact = contact;
        return this;
    }

    public InfoBuilder license(License license) {
        this.license = license;
        return this;
    }

    public Info build() {
        return new Info(this.title, this.summary, this.description, this.termsOfService, this.contact, this.license, this.version);
    }

}
