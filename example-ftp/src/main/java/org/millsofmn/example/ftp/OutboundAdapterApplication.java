package org.millsofmn.example.ftp;

import org.apache.commons.net.ftp.FTPFile;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.file.remote.session.CachingSessionFactory;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.ftp.outbound.FtpMessageHandler;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.messaging.MessageHandler;

import java.io.File;


@SpringBootApplication
@IntegrationComponentScan
public class OutboundAdapterApplication {

    public static void main(String[] args){
        ConfigurableApplicationContext context = new SpringApplicationBuilder(OutboundAdapterApplication.class)
                .web(false).run(args);

        File newFile = new File("src\\main\\java\\org\\millsofmn\\example\\ftp\\bar.txt");

        MyGateway gateway = context.getBean(MyGateway.class);
        gateway.sendToFtp(newFile);
    }

    @Bean
    public SessionFactory<FTPFile> ftpSessionFactory(){
        DefaultFtpSessionFactory sf = new DefaultFtpSessionFactory();
        sf.setHost("");
        sf.setPort(21);
        sf.setUsername("");
        sf.setPassword("");
        return new CachingSessionFactory<FTPFile>(sf);
    }

    @Bean
    @ServiceActivator(inputChannel = "toFtpChannel")
    public MessageHandler handler() {
        FtpMessageHandler handler = new FtpMessageHandler(ftpSessionFactory());
        ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();
        handler.setRemoteDirectoryExpression(EXPRESSION_PARSER.parseExpression("headers['path']"));

        return handler;
    }

    @MessagingGateway
    public interface MyGateway {
        @Gateway(requestChannel = "toFtpChannel")
        void sendToFtp(File file);
    }
}
