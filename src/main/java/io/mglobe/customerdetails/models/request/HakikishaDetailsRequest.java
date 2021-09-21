package io.mglobe.customerdetails.models.request;


public class HakikishaDetailsRequest {
	BodyHeaders header;
	RequestPayload requestPayload;
	public HakikishaDetailsRequest() {
		
	}
	public BodyHeaders getHeader() {
		return header;
	}
	public void setHeader(BodyHeaders header) {
		this.header = header;
	}
	public RequestPayload getRequestPayload() {
		return requestPayload;
	}
	public void setRequestPayload(RequestPayload requestPayload) {
		this.requestPayload = requestPayload;
	}
	@Override
	public String toString() {
		return "HakikishaDetails [header=" + header + ", requestPayload=" + requestPayload + "]";
	}
	
}
