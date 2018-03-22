package org.home.service;

import lombok.extern.slf4j.Slf4j;
import org.home.model.entity.SMTPSettings;
import org.home.model.entity.TestResponse;
import org.home.model.entity.TestStatus;
import org.springframework.stereotype.Component;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import java.net.Socket;
import java.util.Properties;

@Component
@Slf4j
public class EmailService {

    public TestResponse testSMTPServer(SMTPSettings settings) {

        final String server = settings.getServer();
        final Integer port = settings.getPort();
        final String userName = settings.getUserName();
        final String password = settings.getPassword();
        final Boolean auth = settings.getAuth();
        final Boolean ssl = settings.getSslSecurity();


        Properties properties = new Properties();
        properties.put("mail.smtp.host", server);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", auth);
        if (ssl) {
            properties.put("mail.smtp.socketFactory.port", port);
            properties.put("mail.smtp.socketFactory.class", "com.sun.mail.util.MailSSLSocketFactory");
        }

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });

        try {
//            Transport transport = session.getTransport("smtp");
//            transport.connect(server, port, userName, password);
//            transport.close();

            Socket s = new Socket(server, port);

            return TestResponse.builder().result(TestStatus.OK).build();
        } catch (Exception e) {
            log.error("Error during SMTP check: " + e.getMessage(), e);
            return TestResponse.builder()
                    .result(TestStatus.ERROR)
                    .message(e.getLocalizedMessage()).build();
        }
    }
}