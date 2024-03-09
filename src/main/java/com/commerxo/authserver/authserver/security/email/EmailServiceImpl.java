package com.commerxo.authserver.authserver.security.email;

import com.commerxo.authserver.authserver.security.email.context.AbstractEmailContext;
import freemarker.template.Template;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class EmailServiceImpl implements EmailService{

    private final JavaMailSender javaMailSender;
    private final FreeMarkerConfigurer freeMarkerConfigurer;

    public EmailServiceImpl(JavaMailSender javaMailSender, FreeMarkerConfigurer freeMarkerConfigurer) {
        this.javaMailSender = javaMailSender;
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }

    @Override
    public void sendMimeEmail(AbstractEmailContext context) throws MessagingException {
        Map<String, Object> model = new HashMap<>();
        model.put("context",context.getContext());
        CompletableFuture.runAsync(() -> {
            try {
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
                        MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                        StandardCharsets.UTF_8.name());
                Template template = freeMarkerConfigurer
                        .getConfiguration()
                        .getTemplate(context.getTemplateLocation());
                String html = FreeMarkerTemplateUtils.processTemplateIntoString(template,context);
                mimeMessageHelper.setTo(context.getTo());
                mimeMessageHelper.setSubject(context.getSubject());
                mimeMessageHelper.setFrom(context.getFrom());
                mimeMessageHelper.setText(html, true);
                javaMailSender.send(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void sendSimpleEmail(AbstractEmailContext context) throws MessagingException {

    }


}
