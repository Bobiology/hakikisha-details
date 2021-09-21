package io.mglobe.customerdetails.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Component
//@ConfigurationProperties("pesalink.mobile")
@Configuration
@Service
@PropertySource("classpath:application.yml")
public class HakikishaConfigProps {
	//pesalink mobile
	@Value("${pesalink.mobile.login}")	
	private String ipslMobileLogin;
	@Value("${pesalink.mobile.password}")
	private String ipslMobilePassword;
	@Value("${pesalink.mobile.hostUrl}")
	private String ipslMobileHostUrl;
	@Value("${pesalink.mobile.requestPayloadFilePath}")
	private String ipslMobileReqFileName;
	//pesalink account
	@Value("${pesalink.account.login}")	
	private String ipslAccountLogin;
	@Value("${pesalink.account.password}")
	private String ipslAccountPassword;
	@Value("${pesalink.account.hostUrl}")
	private String ipslAccountHostUrl;
	@Value("${pesalink.account.requestPayloadFilePath}")
	private String ipslAccountReqFileName;
	//daraja api
	@Value("${daraja.login}")	
	private String darajaLogin;
	@Value("${daraja.password}")
	private String darajaPassword;
	@Value("${daraja.hostUrl}")
	private String darajaHostUrl;
	@Value("${daraja.requestPayloadFilePath}")
	private String darajaReqFileName;
	@Value("${daraja.ServiceId}")
	private String ServiceId;
	@Value("${daraja.BillerId}")
	private String BillerId;
	@Value("${daraja.MessageTypeIndicator}")
	private String MessageTypeIndicator;
	@Value("${daraja.CompanyId}")
	private String CompanyId;
	@Value("${daraja.ChannelId}")
	private String ChannelId;
	//security
	@Value("${spring.security.user.name}")
	private String basicAuthUsername;
	@Value("${spring.security.user.password}")
	private String basicAuthPass;
	@Value("${spring.security.user.roles}")
	private String basicAuthRole;
	//mpesa agent
	@Value("${mpesa.agent.requestPayloadFilePath}")
	private String agentReqFileName;
	@Value("${mpesa.agent.systemCode}")	
	private String agentSystemCode;
	@Value("${mpesa.agent.password}")
	private String agentPassword;
	@Value("${mpesa.agent.hostUrl}")
	private String agentHostUrl;
	
	public String getIpslMobileLogin() {
		return ipslMobileLogin;
	}
	public void setIpslMobileLogin(String ipslMobileLogin) {
		this.ipslMobileLogin = ipslMobileLogin;
	}
	public String getIpslMobilePassword() {
		return ipslMobilePassword;
	}
	public void setIpslMobilePassword(String ipslMobilePassword) {
		this.ipslMobilePassword = ipslMobilePassword;
	}
	public String getIpslMobileHostUrl() {
		return ipslMobileHostUrl;
	}
	public void setIpslMobileHostUrl(String ipslMobileHostUrl) {
		this.ipslMobileHostUrl = ipslMobileHostUrl;
	}
	public String getIpslMobileReqFileName() {
		return ipslMobileReqFileName;
	}
	public void setIpslMobileReqFileName(String ipslMobileReqFileName) {
		this.ipslMobileReqFileName = ipslMobileReqFileName;
	}
	public String getIpslAccountLogin() {
		return ipslAccountLogin;
	}
	public void setIpslAccountLogin(String ipslAccountLogin) {
		this.ipslAccountLogin = ipslAccountLogin;
	}
	public String getIpslAccountPassword() {
		return ipslAccountPassword;
	}
	public void setIpslAccountPassword(String ipslAccountPassword) {
		this.ipslAccountPassword = ipslAccountPassword;
	}
	public String getIpslAccountHostUrl() {
		return ipslAccountHostUrl;
	}
	public void setIpslAccountHostUrl(String ipslAccountHostUrl) {
		this.ipslAccountHostUrl = ipslAccountHostUrl;
	}
	public String getIpslAccountReqFileName() {
		return ipslAccountReqFileName;
	}
	public void setIpslAccountReqFileName(String ipslAccountReqFileName) {
		this.ipslAccountReqFileName = ipslAccountReqFileName;
	}
	public String getDarajaLogin() {
		return darajaLogin;
	}
	public void setDarajaLogin(String darajaLogin) {
		this.darajaLogin = darajaLogin;
	}
	public String getDarajaPassword() {
		return darajaPassword;
	}
	public void setDarajaPassword(String darajaPassword) {
		this.darajaPassword = darajaPassword;
	}
	public String getDarajaHostUrl() {
		return darajaHostUrl;
	}
	public void setDarajaHostUrl(String darajaHostUrl) {
		this.darajaHostUrl = darajaHostUrl;
	}
	public String getDarajaReqFileName() {
		return darajaReqFileName;
	}
	public void setDarajaReqFileName(String darajaReqFileName) {
		this.darajaReqFileName = darajaReqFileName;
	}
	public String getServiceId() {
		return ServiceId;
	}
	public void setServiceId(String serviceId) {
		ServiceId = serviceId;
	}
	public String getBillerId() {
		return BillerId;
	}
	public void setBillerId(String billerId) {
		BillerId = billerId;
	}
	public String getMessageTypeIndicator() {
		return MessageTypeIndicator;
	}
	public void setMessageTypeIndicator(String messageTypeIndicator) {
		MessageTypeIndicator = messageTypeIndicator;
	}
	public String getCompanyId() {
		return CompanyId;
	}
	public void setCompanyId(String companyId) {
		CompanyId = companyId;
	}
	public String getChannelId() {
		return ChannelId;
	}
	public void setChannelId(String channelId) {
		ChannelId = channelId;
	}
	
	public String getBasicAuthUsername() {
		return basicAuthUsername;
	}
	public void setBasicAuthUsername(String basicAuthUsername) {
		this.basicAuthUsername = basicAuthUsername;
	}
	public String getBasicAuthPass() {
		return basicAuthPass;
	}
	public void setBasicAuthPass(String basicAuthPass) {
		this.basicAuthPass = basicAuthPass;
	}
	public String getBasicAuthRole() {
		return basicAuthRole;
	}
	public void setBasicAuthRole(String basicAuthRole) {
		this.basicAuthRole = basicAuthRole;
	}
	
	public String getAgentReqFileName() {
		return agentReqFileName;
	}
	public void setAgentReqFileName(String agentReqFileName) {
		this.agentReqFileName = agentReqFileName;
	}
	public String getAgentSystemCode() {
		return agentSystemCode;
	}
	public void setAgentSystemCode(String agentSystemCode) {
		this.agentSystemCode = agentSystemCode;
	}
	public String getAgentPassword() {
		return agentPassword;
	}
	public void setAgentPassword(String agentPassword) {
		this.agentPassword = agentPassword;
	}
	public String getAgentHostUrl() {
		return agentHostUrl;
	}
	public void setAgentHostUrl(String agentHostUrl) {
		this.agentHostUrl = agentHostUrl;
	}
	@Override
	public String toString() {
		return "HakikishaConfigProps [ipslMobileLogin=" + ipslMobileLogin + ", ipslMobilePassword=" + "XXXXXXXXX"
				+ ", ipslMobileHostUrl=" + ipslMobileHostUrl + ", ipslMobileReqFileName=" + ipslMobileReqFileName
				+ ", ipslAccountLogin=" + ipslAccountLogin + ", ipslAccountPassword=" + "XXXXXXXXX"
				+ ", ipslAccountHostUrl=" + ipslAccountHostUrl + ", ipslAccountReqFileName=" + ipslAccountReqFileName
				+ ", darajaLogin=" + darajaLogin + ", darajaPassword=" + "XXXXXXXXX" + ", darajaHostUrl="
				+ darajaHostUrl + ", darajaReqFileName=" + darajaReqFileName + ", ServiceId=" + ServiceId
				+ ", BillerId=" + BillerId + ", MessageTypeIndicator=" + MessageTypeIndicator + ", CompanyId="
				+ CompanyId + ", ChannelId=" + ChannelId + ", basicAuthUsername=" + basicAuthUsername
				+ ", basicAuthPass=" + "XXXXXXXXX" + ", basicAuthRole=" + basicAuthRole + ", agentReqFileName="
				+ agentReqFileName + ", agentSystemCode=" + agentSystemCode + ", agentPassword=" + "XXXXXXXXX"
				+ ", agentHostUrl=" + agentHostUrl + "]";
	}	

}
