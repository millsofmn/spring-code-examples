package org.millsofmn.example.jsch;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;
import org.apache.commons.io.IOUtils;

import java.io.*;

public class JschSession implements AutoCloseable {

    public static void main(String[] args) throws Exception {
        JschSessionFactory factory = new JschSessionFactory();
        JschSession session = factory.getSession();

        session.getFile("/reporting.bed", "MyFile.bed");
        System.out.println("yes");
        System.exit(0);
    }
    /**
     * Jsch Session
     */
    private Session session;

    public JschSession(Session session) {
        this.session = session;
    }

    /**
     * Grab the file specified from the SSH box
     * <p>
     * The remote file is expected to the the whole path to the file.
     * It will return a temporary file which when closed will be removed.
     *
     * @param remoteFile temporary file
     * @param saveAs
     * @return
     * @throws Exception
     */
    public File getFile(String remoteFile, String saveAs) throws Exception {
        Channel channel = session.openChannel("exec");

        // scp command
        String command = "scp -f " + remoteFile;

        // set command
        ((ChannelExec) channel).setCommand(command);

        // setup input and output streams
        OutputStream out = channel.getOutputStream();
        InputStream in = channel.getInputStream();

        // run the command
        channel.connect();

        flushOutputStream(out);


        File outFile = null;

        // loop through the input stream
        while (true) {
            int c = checkAck(in);

            if (c != 'C') {
                break;
            }

            Integer size = Integer.valueOf(readFileHeader(in));

//            readFilePermissions(in);
//
//            Long fileSize = readFileSize(in);
//
//            String fileName = readFileName(in);
//
//            System.out.println("fileSize = " + fileSize + ", fileName = " + fileName);
//
            flushOutputStream(out);
//
            outFile = readFileInFromStream(in, saveAs, size);
//
            System.out.println("out");
            if (checkAck(in) != 0) {
                System.exit(0);
            }
//
            flushOutputStream(out);
        }

        return outFile;
    }

    private String readFileHeader(InputStream inputStream) throws IOException {
        String head = readLine(inputStream);
        System.out.println("head=" + head);

        String[] header = head.split(" ");

        return header[1];
    }

    private String readLine(InputStream inputStream) throws IOException {
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
    private File readFileInFromStream(InputStream in, String saveAs, Integer fileSize) throws IOException {
//        byte[] buffer = new byte[fileSize];
//        in.read(buffer, 0, buffer.length);


//        System.out.println("Buf=" + new String(buffer, "UTF-8"));



//        byte[] buf = new byte[1024];
//
        File outFile = new File(saveAs);
//        File outFile = File.createTempFile(saveAs, ".tmp");
//        outFile.deleteOnExit();
//
        FileOutputStream fos = new FileOutputStream(outFile);


        System.out.println("Start");
        IOUtils.copyLarge(in, fos, 0, fileSize);
        System.out.println("end");
        //
//        int foo;
//
//        while (true) {
//            if (buf.length < fileSize) {
//                foo = buf.length;
//            } else {
//                foo = fileSize.intValue();
//            }
//            foo = in.read(buf, 0, foo);
//
//            if (foo < 0) {
//                // error
//                break;
//            }
//
//            fos.write(buf, 0, foo);
//
//            fileSize -= foo;
//
//            if (fileSize == 0L) break;
//        }

        return outFile;
    }

    private void flushOutputStream(OutputStream out) throws IOException {
        out.write(0);
        out.flush();
    }

    private void readFilePermissions(InputStream in) throws IOException {
        byte[] buf = new byte[5];
        // read '0644 '
        in.read(buf, 0, 5);
        System.out.println("Buf=" + new String(buf, "UTF-8"));
    }

    private Long readFileSize(InputStream in) throws IOException {
        byte[] buf = new byte[1024];
        long fileSize = 0L;

        while (true) {
            if (in.read(buf, 0, 1) < 0) {
                // error
                break;
            }

            System.out.println("Buf2=" + new String(buf, "UTF-8"));
            if (buf[0] == ' ') break;

            fileSize = fileSize * 10L + (long) (buf[0] - '0');
        }
        return fileSize;
    }

    private String readFileName(InputStream in) throws IOException {
        byte[] buf = new byte[1024];
        String fileName = null;
        for (int i = 0; ; i++) {
            in.read(buf, i, 1);

            System.out.println("Buf3=" + new String(buf, "UTF-8"));
            if (buf[i] == (byte) 0x0a) {
                fileName = new String(buf, 0, i);
                break;
            }
        }

        return fileName;
    }

    private int checkAck(InputStream in) throws IOException {
        int b = in.read();

        // b may be
        //  0 for success
        //  1 for error
        //  2 for fatal error
        //  -1

        if (b == 0) return b;

        if (b == -1) return b;

        if (b == 1 || b == 2) {
            StringBuffer sb = new StringBuffer();

            int c;
            do {
                c = in.read();
                sb.append((char) c);
            } while (c != '\n');

            // error
            if (b == 1) {
                System.out.println(sb.toString());
            }

            // fatal error
            if (b == 2) {
                System.out.println(sb.toString());
            }
        }
        return b;
    }

    @Override
    public void close() throws Exception {
        System.out.println("Disconnecting from client");
        this.session.disconnect();
    }
}
