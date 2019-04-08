package com.karamanov.falcon.db.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Dimitar Karamanov
 */
@Entity
@Table(name = "message")
public class MessageEntity implements Serializable {

	private static final long serialVersionUID = 8941103279985736454L;

	@Id
    @Column(name = "message_id")
    private Integer messageID;
    
    @Column(name = "message_text")
    private String messageText;
    
    @Column(name = "message_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date messageDate;
    
    public MessageEntity() {
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
}
