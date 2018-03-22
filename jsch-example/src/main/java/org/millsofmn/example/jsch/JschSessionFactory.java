package org.millsofmn.example.jsch;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jsch", ignoreInvalidFields = true)
public class JschSessionFactory {
    private String username;
    private String password;
    private String host;
    private String port;

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

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public JschSession getSession() throws Exception {
        return new JschSession(this.createClient());
    }

    private Session createClient() throws Exception {
        JSch jsch = new JSch();

        Session session = jsch.getSession(username, host, 22);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");

        System.out.println("Try establishing connection to " + host);
        session.connect();
        System.out.println("Connection established");
        return session;
    }
}
