package io.mglobe.customerdetails.models.response;

public class ResponsePayload {
	PrimaryData primaryData;
	HakikishaInfo hakikishaInfo;
	public ResponsePayload() {
		
	}
	public PrimaryData getPrimaryData() {
		return primaryData;
	}
	public void setPrimaryData(PrimaryData primaryData) {
		this.primaryData = primaryData;
	}
	public HakikishaInfo getHakikishaInfo() {
		return hakikishaInfo;
	}
	public void setHakikishaInfo(HakikishaInfo hakikishaInfo) {
		this.hakikishaInfo = hakikishaInfo;
	}
	@Override
	public String toString() {
		return "ResponsePayload [primaryData=" + primaryData + ", hakikishaInfo=" + hakikishaInfo + "]";
	}
	
	
}
