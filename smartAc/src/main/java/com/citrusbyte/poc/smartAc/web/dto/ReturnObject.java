package com.citrusbyte.poc.smartAc.web.dto;

import java.util.ArrayList;
import java.util.List;

import com.citrusbyte.poc.smartAc.web.exception.ExceptionConstants;

public class ReturnObject {
	private boolean ok;
	private List<MessageTuple> messages = new ArrayList<MessageTuple>();
	//TODO CRC - add timestamp
	//TODO CRC - return OK with correct values
	
	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public List<MessageTuple> getMessages() {
		return messages;
	}
	
	public void addMessage(ExceptionConstants code, String description) {		
		messages.add(new MessageTuple(code, description));
	}

	private class MessageTuple{
		private ExceptionConstants code;
		private String description;
		
		public MessageTuple(ExceptionConstants code, String description) {
			this.code = code;
			this.description = description;
		}
	}
}
