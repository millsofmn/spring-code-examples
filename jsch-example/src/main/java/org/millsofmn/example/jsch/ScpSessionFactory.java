package org.millsofmn.example.jsch;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class ScpSessionFactory {

    private final String STRICK_HOST_KEY_CHECKING = "StrictHostKeyChecking";
    private final Logger log = LoggerFactory.getLogger(ScpSessionFactory.class);

    private final ApplicationProperties properties;

    public ScpSessionFactory(ApplicationProperties properties) {
        this.properties = properties;
    }

    public ScpClient getScpClient() throws JSchException {
        return new ScpClient(this.createSessionInstance());
    }

    private Session createSessionInstance() throws JSchException {
        JSch jsch = new JSch();

        String username = properties.getScp().getUsername();
        String host = properties.getScp().getHost();
        Integer port = properties.getScp().getPort();

        log.info("Establishing connection to {}:{} with user {}", host, port, username);
        Session session = jsch.getSession(username, host, port);
        session.setPassword(properties.getScp().getPassword());

        session.setConfig(STRICK_HOST_KEY_CHECKING, "no"); // prevents prompt to confirm key

        session.connect();
        log.info("Connection established");

        return session;
    }
}
