package io.mglobe.customerdetails.models.response;

import java.util.List;

public class HakikishaInfo {
	
	private String beneficiaryName;
	private double transactionCost;
	private List<BankDetails> bankDetails;
	public HakikishaInfo() {
		
	}
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	public double getTransactionCost() {
		return transactionCost;
	}
	public void setTransactionCost(double transactionCost) {
		this.transactionCost = transactionCost;
	}
	public List<BankDetails> getBankDetails() {
		return bankDetails;
	}
	public void setBankDetails(List<BankDetails> banks) {
		this.bankDetails = banks;
	}
	@Override
	public String toString() {
		return "HakikishaInfo [beneficiaryName=" + beneficiaryName + ", transactionCost=" + transactionCost
				+ ", bankDetails=" + bankDetails + "]";
	}
	
}
