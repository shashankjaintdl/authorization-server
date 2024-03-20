package com.commerxo.authserver.authserver.config;

import com.commerxo.authserver.authserver.config.property.EmailPropertyConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@Import(EmailPropertyConfig.class)
public class EmailConfig {

    private final EmailPropertyConfig propertyConfig;

    public EmailConfig(EmailPropertyConfig propertyConfig) {
        this.propertyConfig = propertyConfig;
    }

    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setUsername(propertyConfig.getUsername());
        javaMailSender.setPassword(propertyConfig.getPassword());
        javaMailSender.setHost(propertyConfig.getHost());
        javaMailSender.setPort(propertyConfig.getPort());
        Properties properties = javaMailSender.getJavaMailProperties();
        properties.setProperty(EmailPropertyConfig.TRANSPORT_PROTOCOL_PROPERTY, JavaMailSenderImpl.DEFAULT_PROTOCOL);
        properties.setProperty(EmailPropertyConfig.SMTP_AUTH, String.valueOf(propertyConfig.isSmtp()));
        properties.setProperty(EmailPropertyConfig.TLS_ENABLED, String.valueOf(propertyConfig.isTls()));
        properties.setProperty(EmailPropertyConfig.DEBUG, String.valueOf(propertyConfig.isDebug()));
        properties.setProperty(EmailPropertyConfig.TEST_CONNECTION, String.valueOf(propertyConfig.isTestConnection()));
        return javaMailSender;
    }

}
