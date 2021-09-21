package io.mglobe.customerdetails.models.request;

public class AdditionalData {
	private String companyCode;
	private String providerSystem;
	private String beneficiaryBankCode;
	private String senderBankCode;
	
	
	public AdditionalData() {
		
	}


	public String getCompanyCode() {
		return companyCode;
	}


	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}


	public String getProviderSystem() {
		return providerSystem;
	}


	public void setProviderSystem(String providerSystem) {
		this.providerSystem = providerSystem;
	}


	public String getBeneficiaryBankCode() {
		return beneficiaryBankCode;
	}


	public void setBeneficiaryBankCode(String beneficiaryBankCode) {
		this.beneficiaryBankCode = beneficiaryBankCode;
	}


	public String getSenderBankCode() {
		return senderBankCode;
	}


	public void setSenderBankCode(String senderBankCode) {
		this.senderBankCode = senderBankCode;
	}


	@Override
	public String toString() {
		return "AdditionalData [companyCode=" + companyCode + ", providerSystem=" + providerSystem
				+ ", beneficiaryBankCode=" + beneficiaryBankCode + ", senderBankCode=" + senderBankCode + "]";
	}

	
}
