package com.commerxo.authserver.authserver;

import com.commerxo.authserver.authserver.web.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class AuthServerApplication {

	@Autowired
	private MailService service;

	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void send(){
		this.service.send("jainshashank562@gmail.com","Test","test");
	}

}