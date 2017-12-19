package org.millsofmn.example.ftp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class FtpExampleApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(FtpExampleApplication.class, args);
        FtpSessionFactory ftpSessionFactory = ctx.getBean(FtpSessionFactory.class);


        System.out.println("Hello World from Spring Boot FTP Example Application!");

        try {

            FtpSession client = ftpSessionFactory.getSession();

            File file = createTestFile();

            client.uploadFile(file.getPath(), file.getName(), "");

            client.disconnect();

            System.out.println("Done");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File createTestFile(){
        try {
            File file = new File("test-file.txt");

            if(file.createNewFile()){
                System.out.println("File is created");
            } else {
                System.out.println("File already exists");
            }
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
