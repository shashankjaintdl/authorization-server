package com.commerxo.authserver.authserver;

import com.commerxo.authserver.authserver.twillio.SmsRequest;
import com.commerxo.authserver.authserver.twillio.TwilioConfiguration;
import com.commerxo.authserver.authserver.twillio.TwilioSmsSenderService;
import com.commerxo.authserver.authserver.web.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class AuthServerApplication {

	@Autowired
	private TwilioSmsSenderService service;



	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void send(){
//		this.service.send("jainshashank562@gmail.com","Test","test");
		service.sendSms(new SmsRequest("919902642035","Test"));
	}

}