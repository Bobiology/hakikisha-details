package io.mglobe.customerdetails.models.response;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRootName;

@XmlRootElement(name="header")
@JsonRootName(value="header")
public class ResponseHeaders{
	
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	private String messageID;
	private String conversationID;
	private String routeCode;
	private String targetSystemID;
	private String statusCode;
	private String statusDescription;
	private String statusMessage;
	private String messageCode;
	
	public ResponseHeaders() {
		
	}

	public String getMessageID() {
		return messageID;
	}

	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}

	public String getConversationID() {
		return conversationID;
	}

	public void setConversationID(String conversationID) {
		this.conversationID = conversationID;
	}

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}

	public String getTargetSystemID() {
		return targetSystemID;
	}

	public void setTargetSystemID(String targetSystemID) {
		this.targetSystemID = targetSystemID;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	@Override
	public String toString() {
		return "ResponseHeaders [messageID=" + messageID + ", conversationID=" + conversationID + ", routeCode="
				+ routeCode + ", targetSystemID=" + targetSystemID + ", statusCode=" + statusCode
				+ ", statusDescription=" + statusDescription + ", statusMessage=" + statusMessage + ", messageCode="
				+ messageCode + "]";
	}

	
	

}
