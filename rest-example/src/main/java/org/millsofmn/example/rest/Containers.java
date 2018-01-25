package org.millsofmn.example.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Containers {

    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Container> container;

    public Containers() {
    }

    public Containers(List<Container> container) {
        this.container = container;
    }

    public List<Container> getContainer() {
        return container;
    }

    public void setContainer(List<Container> container) {
        this.container = container;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Containers{");
        sb.append("container=").append(container);
        sb.append('}');
        return sb.toString();
    }
}
