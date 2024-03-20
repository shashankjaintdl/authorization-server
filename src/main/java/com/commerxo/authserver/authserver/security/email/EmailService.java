package com.commerxo.authserver.authserver.security.email;

import com.commerxo.authserver.authserver.security.email.context.AbstractEmailContext;
import jakarta.mail.MessagingException;

public interface EmailService {

     void sendMimeEmail(AbstractEmailContext context) throws MessagingException;

     void sendSimpleEmail(AbstractEmailContext context) throws MessagingException;

}
