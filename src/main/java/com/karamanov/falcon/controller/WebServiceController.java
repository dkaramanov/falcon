package com.karamanov.falcon.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.karamanov.falcon.domain.Message;
import com.karamanov.falcon.service.MessageService;

/**
 * @author Dimitar Karamanov
 */
@RestController
@RequestMapping("/")
public class WebServiceController {

	@Autowired
	private MessageService messageService;

	@GetMapping(value = "/message/{id}")
	@ResponseBody
	public ResponseEntity<Message> getMessage(@PathVariable(name = "id", required = true) Integer id) {
		Optional<Message> message = messageService.get(id);
		if (message.isPresent()) {
			return new ResponseEntity<Message>(message.get(), HttpStatus.OK);
		}
		return new ResponseEntity<Message>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/message")
	public ResponseEntity<Boolean> addMessage(@RequestBody Message messageO) {
		if (messageO != null && messageO.validate()) {
			messageService.add(messageO);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}
		return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
	}
}
