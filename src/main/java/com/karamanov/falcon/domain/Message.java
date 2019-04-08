package com.karamanov.falcon.domain;

import java.util.Date;

/**
 * @author Dimitar Karamanov
 */
public class Message {

	private Integer messageID;

	private String messageText;

	private Date messageDate;

	public Message() {
	}

	public Integer getMessageID() {
		return messageID;
	}

	public void setMessageID(Integer messageID) {
		this.messageID = messageID;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public Date getMessageDate() {
		return messageDate;
	}

	public void setMessageDate(Date messageDate) {
		this.messageDate = messageDate;
	}

	public boolean validate() {
		return messageID != null && messageText != null && !"".contentEquals(messageText.trim());
	}
}
