package io.mglobe.customerdetails.models.request;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonRootName;

@XmlRootElement(name="header")
@JsonRootName(value="header")
public class BodyHeaders {

	private String messageID;
	private String featureCode;
	private String featureName;
	private String serviceCode;
	private String serviceName;
	private String serviceSubCategory;
	private String minorServiceVersion;
	private String channelCode;
	private String channelName;
	private String routeCode;
	private String timeStamp;
	private String serviceMode;
	private String subscribeEvents;
	private String callBackURL;
	
	public BodyHeaders() {
		
	}

	public String getMessageID() {
		return messageID;
	}

	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}

	public String getFeatureCode() {
		return featureCode;
	}

	public void setFeatureCode(String featureCode) {
		this.featureCode = featureCode;
	}

	public String getFeatureName() {
		return featureName;
	}

	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceSubCategory() {
		return serviceSubCategory;
	}

	public void setServiceSubCategory(String serviceSubCategory) {
		this.serviceSubCategory = serviceSubCategory;
	}

	public String getMinorServiceVersion() {
		return minorServiceVersion;
	}

	public void setMinorServiceVersion(String minorServiceVersion) {
		this.minorServiceVersion = minorServiceVersion;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getServiceMode() {
		return serviceMode;
	}

	public void setServiceMode(String serviceMode) {
		this.serviceMode = serviceMode;
	}

	public String getSubscribeEvents() {
		return subscribeEvents;
	}

	public void setSubscribeEvents(String subscribeEvents) {
		this.subscribeEvents = subscribeEvents;
	}

	public String getCallBackURL() {
		return callBackURL;
	}

	public void setCallBackURL(String callBackURL) {
		this.callBackURL = callBackURL;
	}

	@Override
	public String toString() {
		return "BodyHeaders [messageID=" + messageID + ", featureCode=" + featureCode + ", featureName=" + featureName
				+ ", serviceCode=" + serviceCode + ", serviceName=" + serviceName + ", serviceSubCategory="
				+ serviceSubCategory + ", minorServiceVersion=" + minorServiceVersion + ", channelCode=" + channelCode
				+ ", channelName=" + channelName + ", routeCode=" + routeCode + ", timeStamp=" + timeStamp
				+ ", serviceMode=" + serviceMode + ", subscribeEvents=" + subscribeEvents + ", callBackURL="
				+ callBackURL + "]";
	}
	
}
