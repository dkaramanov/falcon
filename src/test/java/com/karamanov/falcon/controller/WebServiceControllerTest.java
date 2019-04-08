package com.karamanov.falcon.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.karamanov.falcon.Application;
import com.karamanov.falcon.db.model.MessageEntity;
import com.karamanov.falcon.db.repository.MessageRepository;
import com.karamanov.falcon.domain.Message;

/**
 * @author Dimitar Karamanov
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource(properties = {
		"spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration, org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration, org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration, org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration" })
public class WebServiceControllerTest {

	@LocalServerPort
	private int port;

	private final static Integer ID = -1;

	@MockBean
	private MessageRepository messageRepository;

	private final TestRestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void shouldGetMessage() throws Exception {
		MessageEntity messageEntity = new MessageEntity();
		messageEntity.setMessageID(ID);
		messageEntity.setMessageText("Test message");
		given(messageRepository.findById(ID)).willReturn(Optional.of(messageEntity));

		Message result = restTemplate.getForObject("http://localhost:" + port + "/message/" + ID, Message.class);
		assertEquals(messageEntity.getMessageID(), result.getMessageID());
		assertEquals(messageEntity.getMessageText(), result.getMessageText());
	}

	@Test
	public void shouldAddMessage() throws Exception {
		MessageEntity messageEntity = new MessageEntity();
		messageEntity.setMessageID(ID);
		messageEntity.setMessageText("Test message");
		given(messageRepository.save(ArgumentMatchers.any(MessageEntity.class))).willReturn(messageEntity);

		Message message = new Message();
		message.setMessageID(ID);
		message.setMessageText("Test message");

		String result = restTemplate.postForObject("http://localhost:" + port + "/message/", message, String.class);
		assertTrue("true".equals(result));
	}

	@Test
	public void shouldNotAddMessage() throws Exception {
		MessageEntity messageEntity = new MessageEntity();
		messageEntity.setMessageID(ID);
		messageEntity.setMessageText("Test message");
		given(messageRepository.save(ArgumentMatchers.any(MessageEntity.class))).willReturn(messageEntity);

		Message message = new Message();
		message.setMessageText("Test message");

		String result = restTemplate.postForObject("http://localhost:" + port + "/message/", message, String.class);
		assertTrue("false".equals(result));
	}

}
