package com.karamanov.falcon.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.karamanov.falcon.controller.WebSocketController;
import com.karamanov.falcon.service.MessageService;

/**
 * @author Dimitar Karamanov
 */
@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

	@Autowired
	private MessageService messageService;

	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(new WebSocketController(messageService), "/socket").setAllowedOrigins("*");
	}
}
