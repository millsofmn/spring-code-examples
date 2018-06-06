package org.millsofmn.example.jsch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class JschExampleApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(JschExampleApplication.class);
	}

    public static void main(String[] args){
        ConfigurableApplicationContext ctx = SpringApplication.run(JschExampleApplication.class, args);
        ScpSessionFactory factory = ctx.getBean(ScpSessionFactory.class);

        System.out.println("Starting JSCH Example Application");

        try (ScpClient session = factory.getScpClient()) {
            String remoteFile = "/dlmp/dev/scripts/sources/teamwork/lyle/output.txt";
            String localFile = "C:\\Users\\m108491\\IdeaProjects\\spring-code-examples\\rest-example\\output.bed";

            session.checkFileExists(remoteFile);
            session.getFile(remoteFile, localFile);

            session.putFile(localFile, "/dlmp/dev/scripts/sources/teamwork/lyle/output2.txt");

        } catch (Exception e) {
            System.out.println("Error: ++++++++++++++++++");
            e.printStackTrace();
        }

        System.exit(0);
    }
}
