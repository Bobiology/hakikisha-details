package io.mglobe.customerdetails.models.response;

public class HakikishaDetailsResponse {
	ResponseHeaders header;
	ResponsePayload responsePayload;
	public HakikishaDetailsResponse() {
	 
 }
	public ResponseHeaders getHeader() {
		return header;
	}
	public void setHeader(ResponseHeaders header) {
		this.header = header;
	}
	public ResponsePayload getResponsePayload() {
		return responsePayload;
	}
	public void setResponsePayload(ResponsePayload responsePayload) {
		this.responsePayload = responsePayload;
	}
	@Override
	public String toString() {
		return "HakikishaDetailsResponse [header=" + header + ", responsePayload=" + responsePayload + "]";
	}
	
	
 
}
