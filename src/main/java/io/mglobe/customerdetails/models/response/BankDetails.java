package io.mglobe.customerdetails.models.response;

public class BankDetails {
	private String bankCode;
	private String bankName;
	public BankDetails() {
		
	}
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	@Override
	public String toString() {
		return "BankDetails [bankCode=" + bankCode + ", bankName=" + bankName + "]";
	}
	
	
}
