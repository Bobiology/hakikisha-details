package io.mglobe.customerdetails.models.request;

public class PrimaryData {
	private String businessKey;
	private String businessKeyType;
	
	public PrimaryData() {
		
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getBusinessKeyType() {
		return businessKeyType;
	}

	public void setBusinessKeyType(String businessKeyType) {
		this.businessKeyType = businessKeyType;
	}

	@Override
	public String toString() {
		return "PrimaryData [businessKey=" + businessKey + ", businessKeyType=" + businessKeyType + "]";
	}
	
}
