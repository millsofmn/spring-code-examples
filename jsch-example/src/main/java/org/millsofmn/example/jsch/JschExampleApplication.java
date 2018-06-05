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
        JschSessionFactory factory = ctx.getBean(JschSessionFactory.class);

        System.out.println("Starting JSCH Example Application");

        try (JschFileTemplate session = factory.getFileTemplate()) {
            String path = "";
            session.checkFileExists(path);
//            session.downloadFile(path);

        } catch (Exception e) {
            System.out.println("Error: ++++++++++++++++++");
            e.printStackTrace();
        }

        System.exit(0);
    }
}
