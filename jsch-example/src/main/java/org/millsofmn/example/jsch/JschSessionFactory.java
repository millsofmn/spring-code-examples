package org.millsofmn.example.jsch;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jsch", ignoreInvalidFields = true)
public class JschSessionFactory {

    private final String STRICK_HOST_KEY_CHECKING = "StrictHostKeyChecking";
    private final Logger log = LoggerFactory.getLogger(JschSessionFactory.class);

    private String username;
    private String password;
    private String host;
    private int port;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public JschSession getSession() throws Exception {
        return new JschSession(this.createSessionInstance());
    }

    public JschFileTemplate getFileTemplate() throws Exception {
        return new JschFileTemplate(this.createSessionInstance());
    }

    private Session createSessionInstance() throws JSchException {
            JSch jsch = new JSch();

            log.info("Establishing connection to {}:{} with user {}", host, port, username);
            Session session = jsch.getSession(username, host, port);
            session.setPassword(password);

            session.setConfig(STRICK_HOST_KEY_CHECKING, "no"); // prevents prompt to confirm key

            session.connect();
            log.info("Connection established");

            return session;
    }
}
