package io.mglobe.customerdetails.models.request;

public class RequestPayload {
	private PrimaryData primaryData;
	private AdditionalData additionalData;
	public RequestPayload() {
		
	}
	public PrimaryData getPrimaryData() {
		return primaryData;
	}
	public void setPrimaryData(PrimaryData primaryData) {
		this.primaryData = primaryData;
	}
	public AdditionalData getAdditionalData() {
		return additionalData;
	}
	public void setAdditionalData(AdditionalData additionalData) {
		this.additionalData = additionalData;
	}
	@Override
	public String toString() {
		return "RequestPayload [primaryData=" + primaryData + ", additionalData=" + additionalData + "]";
	}
	
}
