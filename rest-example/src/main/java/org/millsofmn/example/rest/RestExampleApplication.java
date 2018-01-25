package org.millsofmn.example.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RestExampleApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(RestExampleApplication.class, args);
        RestClientFactory factory = ctx.getBean(RestClientFactory.class);

        System.out.println("Creating a example rest client factory");

        RestClient client = factory.getClient();

        Container container = client.requestContainer("27-1");

        System.out.println(container);
    }

}
