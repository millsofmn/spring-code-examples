package org.millsofmn.example.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestExampleApplication.class)
public class RestClientFunctionalTest {

    String uri = "https://unknown";

    @Autowired
    private RestClientFactory restClientFactory;

    private MockRestServiceServer mockServer;

    private RestClient restClient;

    @Before
    public void setup(){
        restClientFactory.setUri(uri);
        restClientFactory.setUsername("username");
        restClientFactory.setPassword("password");

        restClient = restClientFactory.getClient();

        mockServer = MockRestServiceServer.createServer(restClient.getRestTemplate());
    }

    @Test
    public void testFindContainer(){
        String response =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<con:containers xmlns:con=\"http://test/containers\">\n" +
                "    <container uri=\"https://unknown/containers/27-1\" limsid=\"27-1\">\n" +
                "        <name>27-1</name>\n" +
                "    </container>\n" +
                "</con:containers>";

        mockServer.expect(requestTo(uri + "/containers?name=27-1")).andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(response, MediaType.TEXT_XML));

        Container result = restClient.requestContainer("27-1");

        mockServer.verify();
    }
}
