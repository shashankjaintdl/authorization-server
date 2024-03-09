package com.commerxo.authserver.authserver.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.mail")
public class EmailPropertyConfig {

    public static final String TRANSPORT_PROTOCOL_PROPERTY = "mail.transport.protocol";
    public static final String SMTP_AUTH = "mail.smtp.auth";
    public static final String TLS_ENABLED = "mail.smtp.starttls.enable";
    public static final String  DEBUG = "mail.debug";
    public static final String TEST_CONNECTION = "spring.mail.test-connection";

    private String username;
    private String password;
    private String host;
    private int port;
    private boolean smtp;
    private boolean tls;
    private boolean debug;
    private boolean testConnection;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isSmtp() {
        return smtp;
    }

    public void setSmtp(boolean smtp) {
        this.smtp = smtp;
    }

    public boolean isTls() {
        return tls;
    }

    public void setTls(boolean tls) {
        this.tls = tls;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean isTestConnection() {
        return testConnection;
    }

    public void setTestConnection(boolean testConnection) {
        this.testConnection = testConnection;
    }

}