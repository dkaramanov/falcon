package com.karamanov.falcon.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.karamanov.falcon.db.model.MessageEntity;
import com.karamanov.falcon.db.repository.MessageRepository;
import com.karamanov.falcon.domain.Message;

/**
 * @author Dimitar Karamanov
 */
@Service
public class MessageService {

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(MessageService.class);

	/**
	 * Hold connected sessions
	 */
	private final List<WebSocketSession> sessions = new ArrayList<>();

	/**
	 * Lock object for concurrent access to sessions
	 */
	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	@Autowired
	private MessageRepository messageRepository;

	public Optional<Message> get(Integer id) {
		Optional<MessageEntity> message = messageRepository.findById(id);
		if (message.isPresent()) {
			Message messageO = new Message();
			messageO.setMessageID(message.get().getMessageID());
			messageO.setMessageText(message.get().getMessageText());
			messageO.setMessageDate(message.get().getMessageDate());
			return Optional.of(messageO);
		}
		return Optional.empty();
	}

	public void add(Message message) {
		MessageEntity messageEntity = new MessageEntity();
		messageEntity.setMessageID(message.getMessageID());
		messageEntity.setMessageText(message.getMessageText());
		messageEntity.setMessageDate(new Date());

		messageRepository.save(messageEntity);
		notifyAllSessions(message);
	}

	public void addSession(WebSocketSession session) {
		lock.writeLock().lock();
		try {
			sessions.add(session);
		} finally {
			lock.writeLock().unlock();
		}
	}

	public void removeSession(WebSocketSession session) {
		lock.writeLock().lock();
		try {
			sessions.remove(session);
		} finally {
			lock.writeLock().unlock();
		}
	}

	private void notifyAllSessions(Message message) {
		notifyAllSessions(message.getMessageText());
	}

	public void notifyAllSessions(String text) {
		lock.readLock().lock();
		try {
			for (WebSocketSession session : sessions) {
				if (session.isOpen()) {
					TextMessage textMessage = new TextMessage(text);
					try {
						session.sendMessage(textMessage);
					} catch (IOException e) {
						logger.error(e.getMessage());
					}
				}
			}
		} finally {
			lock.readLock().unlock();
		}
	}
}
