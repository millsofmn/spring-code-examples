package org.millsofmn.example.rest;

import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class RestClient {

    private final RestTemplate restTemplate;

    public RestClient(RestTemplate client) {
        this.restTemplate = client;
    }

    public Container requestContainer(String id) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("id", id);

        Containers containers = restTemplate.getForObject(
                "/containers?name={id}",
                Containers.class,
                parameters
                );

        return containers.getContainer().get(0);
    }
}
