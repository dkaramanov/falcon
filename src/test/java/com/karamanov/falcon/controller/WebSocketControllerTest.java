package com.karamanov.falcon.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import com.karamanov.falcon.Application;
import com.karamanov.falcon.db.repository.MessageRepository;

/**
 * @author Dimitar Karamanov
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource(properties = {
		"spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration, org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration, org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration, org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration" })
public class WebSocketControllerTest {

	@LocalServerPort
	private int port;

	@MockBean
	private MessageRepository messageRepository;

	private boolean connected = false;
	
	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(WebSocketControllerTest.class);


	@Test
	public void testWebSocket() {
		StandardWebSocketClient client = new StandardWebSocketClient();

		WebSocketHandler wsh = new WebSocketHandler() {

			@Override
			public void afterConnectionEstablished(WebSocketSession session) throws Exception {
				logger.info("Connected");
				connected = true;
			}

			@Override
			public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
				logger.info("Handle message");
			}

			@Override
			public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
				logger.info("Handle error");
			}

			@Override
			public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
				logger.info("Close");
			}

			@Override
			public boolean supportsPartialMessages() {
				return false;
			}
		};

		client.doHandshake(wsh, "ws://localhost:" + port + "/socket");

		// Wait a second to handle connection
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertTrue(connected);
	}
}
