package org.millsofmn.example.jsch;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.apache.commons.io.IOUtils;

public class JschFileTemplate implements AutoCloseable {

    private final Session session;

    private ChannelExec channel;
    private InputStream inputStream;
    private OutputStream outputStream;

    public JschFileTemplate(Session session) {
        this.session = session;
    }

    private void executeCommand(String command) throws JSchException, IOException {
        channel = (ChannelExec)session.openChannel("exec");
        channel.setCommand(command);

        inputStream = channel.getInputStream();
        outputStream = channel.getOutputStream();

        System.out.println("Executing command : " + command);
        channel.connect();
    }

    public void checkFileExists(String path) throws IOException, JSchException {
        String command = "ls " + path;

        executeCommand(command);
//        flushOutputStream();
//        checkAck();

        // just a comment to push
        try {
            String response = readResponse();
        } catch (IOException e) {
            throw new FileNotFoundException(path);
        }

    }
    public File downloadFile(String path) throws IOException, JSchException {
        // scp command
        String command = "scp -f " + path;

        executeCommand(command);
        flushOutputStream();
        checkAck();

        String header = readResponse();
        String[] head = header.split(" ");
        String filePermissions = head[0];
        Long fileSize = Long.valueOf(head[1]);
        flushOutputStream();

        File outFile = getFile("record.bed", fileSize);
        checkAck();
        flushOutputStream();

        return outFile;
    }

    private void checkAck() throws IOException {
        int b = inputStream.read();
        System.out.println("Check Ack : " + b);

        if(b == 1 || b == 2){
            String error = readLine();
            throw new IOException("Failed to execute remote SCP command: " + error);
        }
    }

    private void flushOutputStream() throws IOException {
        outputStream.write(0);
        outputStream.flush();
    }

    private String readResponse() throws IOException {
        String response = readLine();
        System.out.println("Response : " + response);
        return response;
    }

    private String readLine() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while(true) {
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
//        File outFile = File.createTempFile(saveAs, ".bed");
//        outFile.deleteOnExit();

        FileOutputStream fos = new FileOutputStream(outFile);

        System.out.println("Start file transfer");
        IOUtils.copyLarge(inputStream, fos, 0, fileSize);
        System.out.println("End file transfer");

        return outFile;
    }

    @Override
    public void close() throws Exception {
        System.out.println("Disconnecting from client");
        this.session.disconnect();
    }
}
