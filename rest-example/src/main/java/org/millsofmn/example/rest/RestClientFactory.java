package org.millsofmn.example.rest;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@ConfigurationProperties(prefix = "rest", ignoreUnknownFields = true)
public class RestClientFactory {

    private String username;
    private String password;
    private String uri;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public RestClient getClient(){
        return new RestClient(this.createClient());
    }

    private RestTemplate createClient(){
        RestTemplate restTemplate = new RestTemplateBuilder()
                .basicAuthorization(username, password)
                .rootUri(uri)
                .build();

        return restTemplate;
    }
}
