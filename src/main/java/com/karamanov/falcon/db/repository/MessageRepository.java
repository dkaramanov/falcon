package com.karamanov.falcon.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.karamanov.falcon.db.model.MessageEntity;

/**
 * @author Dimitar Karamanov
 */
public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {

}
