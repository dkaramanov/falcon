package com.karamanov.falcon;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.karamanov.falcon.service.MessageService;

/**
 * @author Dimitar Karamanov
 */
@SpringBootApplication
public class Application {

	@Autowired
	private MessageService messageService;

	@PreDestroy
	public void stop() {
		messageService.notifyAllSessions("Disconected");
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
