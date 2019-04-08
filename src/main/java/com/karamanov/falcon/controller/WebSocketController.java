package com.karamanov.falcon.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.karamanov.falcon.service.MessageService;

/**
 * @author Dimitar Karamanov
 */
public class WebSocketController extends TextWebSocketHandler {

	private final MessageService messageService;

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(WebSocketController.class);

	public WebSocketController(MessageService messageService) {
		this.messageService = messageService;
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
		logger.info("New Text Message Received");
		session.sendMessage(new TextMessage("Hello" + message.toString()));
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		messageService.addSession(session);
		logger.info("New connection");
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
		messageService.removeSession(session);
		logger.info("Close connection");
	}
}
