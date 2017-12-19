package org.millsofmn.example.ftp;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FtpSession {

    private final FTPClient client;

    public FtpSession(FTPClient client) {
        this.client = client;
    }

    public void uploadFile(String localFileFullName, String fileName, String hostDir) throws IOException {
        try (InputStream input = new FileInputStream(new File(localFileFullName))) {
            this.client.storeFile(hostDir + fileName, input);
        }
    }

    public void disconnect(){
        if(this.client.isConnected()){
            try {
                this.client.logout();
                this.client.disconnect();
            } catch (IOException e) {
                // do nothing
            }
        }
    }
}
