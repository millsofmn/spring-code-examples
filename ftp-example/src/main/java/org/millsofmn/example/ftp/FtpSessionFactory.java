package org.millsofmn.example.ftp;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;

@Component
@ConfigurationProperties(prefix = "ftp", ignoreUnknownFields = true)
public class FtpSessionFactory {

    private String host;
    private Integer port;
    private String username;
    private String password;

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

    public FtpSession getSession() throws Exception {
        return new FtpSession(this.createClient());
    }

    private FTPClient createClient() throws Exception {
        FTPClient client = new FTPClient();

        client.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));

        client.connect(this.host, this.port);

        if(!FTPReply.isPositiveCompletion(client.getReplyCode())){
            client.disconnect();
            throw new Exception("Connecting to ftp server " + this.host + ":" + this.port + " has failed. Please check the connection.");
        }

        if(!client.login(this.username, this.password)){
            throw new IllegalStateException("Login failed. The response from the ftp server is: " + client.getReplyString());
        }

        client.setFileType(FTP.BINARY_FILE_TYPE);
        client.enterLocalPassiveMode();

        return client;
    }
}
