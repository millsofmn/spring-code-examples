package org.millsofmn.example.jsch;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.*;

public class ScpClient implements AutoCloseable {

    private final Logger log = LoggerFactory.getLogger(ScpClient.class);
    private final Session session;

    private ChannelExec channel;
    private InputStream inputStream;
    private OutputStream outputStream;

    public ScpClient(Session session) {
        this.session = session;
        log.debug("Opening Client");
    }

    private void executeCommand(String command) throws JSchException, IOException {
        channel = (ChannelExec) session.openChannel("exec");
        channel.setCommand(command);

        inputStream = channel.getInputStream();
        outputStream = channel.getOutputStream();

        log.info("Executing command : {}", command);
        channel.connect();
    }

    public boolean checkFileExists(String path) throws IOException, JSchException {
        String command = "ls " + path;

        executeCommand(command);

        String response = null;
        try {
            response = readResponse();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public File getFile(String remoteFile, String localFile) throws FileNotFoundException, JSchException {
        Assert.notNull("Path to remote file must be specified.", remoteFile);
        Assert.notNull("Path to local file must be specified.", localFile);

        try {
            // scp command
            String command = "scp -f " + remoteFile;

            executeCommand(command);
            flushOutputStream();
            checkAck();

            String header = readResponse();
            String[] head = header.split(" ");
            String filePermissions = head[0];
            Long fileSize = Long.valueOf(head[1]);
            flushOutputStream();

            File outFile = getFile(localFile, fileSize);
            checkAck();
            flushOutputStream();

            return outFile;
        } catch (IOException e) {
            throw new FileNotFoundException(remoteFile);
        }
    }

    public void putFile(String localFile, String remoteFile) throws FileNotFoundException, JSchException {
        Assert.notNull("Path to local file must be specified.", localFile);
        Assert.notNull("Path to remote file must be specified.", remoteFile);

        File transferFile = new File(localFile);
        if (!transferFile.exists() || transferFile.isDirectory()) {
            throw new FileNotFoundException("File to transfer does not exist. " + localFile);
        }

        try {
            String command = "scp -p -t " + remoteFile;

            executeCommand(command);
            checkAck();


            log.info(transferFile.getAbsolutePath());

            long fileSize = transferFile.length();

            command = "C0644 " + fileSize + " "; // permissions
            if (localFile.lastIndexOf('/') > 0) {
                command += localFile.substring(localFile.lastIndexOf('/') + 1);
            } else {
                command += localFile;
            }
            command += "\n";

            writeOutputStream(command);
            flushOutputStream();

            checkAck();

            putFile(transferFile);

            log.info("Transfer Complete");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkAck() throws IOException {
        int b = inputStream.read();
        log.info("Check Ack : {}", b);

        if (b == 1 || b == 2) {
            String error = readLine();
            throw new IOException("Failed to execute remote SCP command: " + error);
        }
    }

    private void writeOutputStream(String command) throws IOException {
        log.info(command);
        outputStream.write(command.getBytes());
    }

    private void flushOutputStream() throws IOException {
        outputStream.write(0);
        outputStream.flush();
    }

    private String readResponse() throws IOException {
        String response = readLine();
        log.debug("Response : {}", response);
        return response;
    }

    private String readLine() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while (true) {
            int c = inputStream.read();
            if (c == '\n') {
                return baos.toString();
            } else if (c == -1) {
                throw new IOException("End of stream");
            } else {
                baos.write(c);
            }
        }
    }

    private File getFile(String saveAs, Long fileSize) throws IOException {
        File outFile = new File(saveAs);
//        File outFile = File.createTempFile(saveAs, ".tmp");
        outFile.deleteOnExit();

        FileOutputStream fos = new FileOutputStream(outFile);

        log.debug("Start file transfer");
        IOUtils.copyLarge(inputStream, fos, 0, fileSize);
        log.debug("End file transfer");

        return outFile;
    }

    private void putFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);

        IOUtils.copyLarge(fis, outputStream, 0, file.length());

        flushOutputStream();
    }

    @Override
    public void close() {
        log.info("Disconnecting from client");
        this.channel.disconnect();
        this.session.disconnect();
    }

    public boolean isUp() throws IOException, JSchException {
        String command = "ls ";

        executeCommand(command);

        String response = null;
        try {
            response = readResponse();
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
