package io.mglobe.customerdetails.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.mglobe.customerdetails.models.request.HakikishaDetailsRequest;
import io.mglobe.customerdetails.models.response.HakikishaDetailsResponse;
import io.mglobe.customerdetails.utils.HakikishaConfigProps;
import io.mglobe.customerdetails.utils.HakikishaFileReader;
import io.mglobe.customerdetails.utils.XMLTagExtractor;

@Service
public class PesalinkClient {
	HakikishaConfigProps ipslConfig;

	public PesalinkClient(HakikishaConfigProps ipslConfig) {
		this.ipslConfig = ipslConfig;
	}

	HakikishaDetailsResponse hakikisha;
	HakikishaFileReader fileReader;

	public final Logger LOG = LogManager.getLogger(PesalinkClient.class);
	String timelog = new SimpleDateFormat("yyyyMMddHHmmssSS").format(new java.util.Date());

	public Map<String, String> pesalinkValidatingWithPhoneNumber(HakikishaDetailsRequest req) {
		Map<String, String> responseAccountParams = new HashMap<>();

		LOG.info(timelog + " : configs= " + ipslConfig.toString());
		String pesalinkMobileReq = null;
		
		try (InputStream inputStream = getClass().getResourceAsStream(ipslConfig.getIpslMobileReqFileName()+"");
			    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			    pesalinkMobileReq = reader.lines().collect(Collectors.joining(System.lineSeparator()))
			    		.replace("${MOBILENO}", req.getRequestPayload().getPrimaryData().getBusinessKey())
						.replace("${LOGIN}", ipslConfig.getIpslMobileLogin())
						.replace("${PASSWORD}", ipslConfig.getIpslMobilePassword());
						//.replace("${MOBILENO}", req.getRequestPayload().getPrimaryData().getBusinessKey());
			} catch (IOException e1) {
				LOG.error(timelog + " : File Not Found: "+e1.getMessage());
			}

		LOG.info(timelog + " : ------------------------------------------------------------------------------");

		LOG.info(timelog + " : Pesalink Account - Request Pesalink Endpoint : " + ipslConfig.getIpslMobileHostUrl());

		LOG.info(timelog + " : Pesalink Account - Incoming Request : " + req);

		LOG.info(timelog + " : ------------------------------------------------------------------------------");
		responseAccountParams.put("transactionCost", "0");
		responseAccountParams.put("businessKeyType", req.getRequestPayload().getPrimaryData().getBusinessKeyType());
		responseAccountParams.put("businessKey", req.getRequestPayload().getPrimaryData().getBusinessKey());

		responseAccountParams.put("messageID", req.getHeader().getMessageID());
		responseAccountParams.put("routeCode", req.getHeader().getRouteCode());
		responseAccountParams.put("targetSystemID", "NotAvailable");
		responseAccountParams.put("statusCode", "1");
		responseAccountParams.put("statusDescription", "Failure");
		responseAccountParams.put("statusMessage", "Failure");
		responseAccountParams.put("callBackURL", req.getHeader().getCallBackURL());

		return new PesalinkClient(ipslConfig).pesalinkMobileAPICall(pesalinkMobileReq, responseAccountParams);

	}

	public Map<String, String> pesalinkValidatingWithAccount(HakikishaDetailsRequest req) {
		Map<String, String> responseAccountParams = new HashMap<>();
		String pesalinkAccountReq = null;
		LOG.info(timelog + " : configs= " + ipslConfig.toString());
		
		try (InputStream inputStream = getClass().getResourceAsStream(ipslConfig.getIpslAccountReqFileName()+"");
			    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			pesalinkAccountReq = reader.lines().collect(Collectors.joining(System.lineSeparator()))
					.replace("${MESSAGEID}", req.getHeader().getMessageID())
					.replace("${TIMESTAMP}", timelog)
					.replace("${ACCOUNT_NUMBER}", req.getRequestPayload().getPrimaryData().getBusinessKey())
					.replace("${BENEFICIARY_BANKCODE}",req.getRequestPayload().getAdditionalData().getBeneficiaryBankCode())
					.replace("${SENDER_BANKCODE}", req.getRequestPayload().getAdditionalData().getSenderBankCode());
			} catch (IOException e1) {
				LOG.error(timelog + " : File Not Found: "+e1.getMessage());
				
			}
		
		
		LOG.info(timelog + " : ------------------------------------------------------------------------------");

		LOG.info(timelog + " : Pesalink Account - Request Pesalink Endpoint : " + ipslConfig.getIpslMobileHostUrl());

		LOG.info(timelog + " : Pesalink Account - Incoming Request : " + req);

		LOG.info(timelog + " : ------------------------------------------------------------------------------");
		responseAccountParams.put("transactionCost", "0");
		responseAccountParams.put("businessKeyType", req.getRequestPayload().getPrimaryData().getBusinessKeyType());
		responseAccountParams.put("businessKey", req.getRequestPayload().getPrimaryData().getBusinessKey());

		responseAccountParams.put("messageID", req.getHeader().getMessageID());
		responseAccountParams.put("routeCode", req.getHeader().getRouteCode());
		responseAccountParams.put("targetSystemID", "NotAvailable");
		responseAccountParams.put("statusCode", "1");
		responseAccountParams.put("statusDescription", "Failure");
		responseAccountParams.put("statusMessage", "Failure");
		responseAccountParams.put("callBackURL", req.getHeader().getCallBackURL());

		return new PesalinkClient(ipslConfig).pesalinkAccountAPICall(pesalinkAccountReq, responseAccountParams);

	}

	public Map<String, String> pesalinkAccountAPICall(String reqTemplate, Map<String, String> responseAccountParams) {
		String endpoint = ipslConfig.getIpslAccountHostUrl().replace("null", "");
		String username = ipslConfig.getIpslAccountLogin().replace("null", "");
		String password = ipslConfig.getIpslAccountPassword().replace("null", "");
		BufferedReader httpResponseReader = null;
		LOG.info(timelog + " : Payload Request =" + reqTemplate);
		LOG.info(timelog + " : Endpoint =" + endpoint);
		LOG.info(timelog + " : Payload username =" + username);
		LOG.info(timelog + " : Payload password =" + password.replace(password, "XXXXXXXX"));
		try {

			URL serverUrl = new URL(endpoint);

			HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();
			urlConnection.setRequestMethod("POST");

			urlConnection.addRequestProperty("Content-Type", "application/json");
			urlConnection.setRequestProperty("Accept", "application/json");
			String basicAuth = username + ":" + password;
			String basicAuthPayload = "Basic" + " " + Base64.getEncoder().encodeToString(basicAuth.getBytes());
			LOG.info(timelog + " : Authorization Headers : " + basicAuthPayload);
			urlConnection.setRequestProperty("Authorization", basicAuthPayload.trim());
			urlConnection.setDoOutput(true);
			try (OutputStream os = urlConnection.getOutputStream()) {
				byte[] input = reqTemplate.getBytes("utf-8");
				os.write(input, 0, input.length);
			}
			// Read response from web server, which will trigger HTTP Basic Authentication
			// request to be sent.
			try (BufferedReader br = new BufferedReader(
					new InputStreamReader(urlConnection.getInputStream(), "utf-8"))) {
				StringBuilder response = new StringBuilder();
				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());
				}
				LOG.info(timelog
						+ " :=============================== RESPONSE FROM PESALINK ===============================");
				LOG.info(timelog + " : " + response.toString());

				JsonParser parser = new JsonParser();
				JsonElement jsonTree = parser.parse(response.toString());
				JsonElement headerElement = null;
				JsonElement responseBody = null;
				if (jsonTree.isJsonObject()) {
					JsonObject jsonObject = jsonTree.getAsJsonObject();
					try {
						headerElement = parser.parse(jsonObject.get("header").toString().replaceAll("^\"|\"$", ""));
						responseBody = parser
								.parse(jsonObject.get("responsePayload").toString().replaceAll("^\"|\"$", ""));
					} catch (Exception e) {
						// e.printStackTrace();
					} finally {
						if (headerElement != null) {
							jsonObject = headerElement.getAsJsonObject();

							responseAccountParams.put("messageID",
									jsonObject.get("messageID").toString().replaceAll("^\"|\"$", ""));
							responseAccountParams.put("conversationID",
									jsonObject.get("conversationID").toString().replaceAll("^\"|\"$", ""));
							responseAccountParams.put("routeCode",
									jsonObject.get("routeCode").toString().replaceAll("^\"|\"$", ""));
							responseAccountParams.put("targetSystemID",
									jsonObject.get("targetSystemID").toString().replaceAll("^\"|\"$", ""));
							responseAccountParams.put("statusCode",
									jsonObject.get("statusCode").toString().replaceAll("^\"|\"$", ""));
							responseAccountParams.put("statusDescription",
									jsonObject.get("statusDescription").toString().replaceAll("^\"|\"$", ""));
							responseAccountParams.put("statusMessage",
									jsonObject.get("statusMessage").toString().replaceAll("^\"|\"$", ""));
							responseAccountParams.put("messageCode",
									jsonObject.get("messageCode").toString().replaceAll("^\"|\"$", ""));
							responseAccountParams.put("callBackURL",
									jsonObject.get("callBackURL").toString().replaceAll("^\"|\"$", ""));

							jsonObject = responseBody.getAsJsonObject();
							responseAccountParams.put("beneficiaryName",
									jsonObject.get("beneficiaryAccountName").toString().replaceAll("^\"|\"$", ""));
							responseAccountParams.put("beneficiaryBankCode",
									jsonObject.get("beneficiaryBankCode").toString().replaceAll("^\"|\"$", ""));
							responseAccountParams.put("accountStatus",
									jsonObject.get("accountStatus").toString().replaceAll("^\"|\"$", ""));
							responseAccountParams.put("verificationDescription",
									jsonObject.get("verificationDescription").toString().replaceAll("^\"|\"$", ""));
							responseAccountParams.put("endToEndId",
									jsonObject.get("endToEndId").toString().replaceAll("^\"|\"$", ""));
						}
					}

				}

			}

			responseAccountParams.put("messageCode", urlConnection.getResponseCode() + "");
			responseAccountParams.put("statusDescription", urlConnection.getResponseMessage() + "");

		} catch (IOException ioe) {
			ioe.printStackTrace();

			responseAccountParams.put("statusDescription", "Unknown error");
			responseAccountParams.put("targetSystemID", "NotAvailable");
		} finally {

			if (httpResponseReader != null) {
				try {
					httpResponseReader.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();

					responseAccountParams.put("statusDescription", "Connection error");
					responseAccountParams.put("targetSystemID", "NotAvailable");
				}
			}
		}

		return responseAccountParams;

	}

	public Map<String, String> pesalinkMobileAPICall(String reqTemplate, Map<String, String> responseMobileParams) {
		String endpoint = ipslConfig.getIpslMobileHostUrl().replace("null", "");
		BufferedReader httpResponseReader = null;
		String statusCode = "1";
		LOG.info(timelog + " : Payload Request =" + reqTemplate);
		LOG.info(timelog + " : Endpoint =" + endpoint);
		try {
			URL serverUrl = new URL(endpoint);
			HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();

			urlConnection.setRequestMethod("POST");

			urlConnection.addRequestProperty("Content-Type", "text/xml");
			urlConnection.setRequestProperty("Accept", "text/xml");
			urlConnection.setRequestProperty("Context", "text/xml");
			urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
			urlConnection.setDoOutput(true);

			try {
				OutputStream os = urlConnection.getOutputStream();
				byte[] input = reqTemplate.getBytes("utf-8");
				os.write(input, 0, input.length);
			}catch(Exception e) {
				
			}
			try (BufferedReader br = new BufferedReader(
					new InputStreamReader(urlConnection.getInputStream(), "utf-8"))) {
				StringBuilder response = new StringBuilder();
				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					response.append(responseLine.trim());

				}
				
				if (response != null && !response.toString().isEmpty()) {

					responseMobileParams.put("statusCode", statusCode);
					responseMobileParams.put("statusDescription",
							XMLTagExtractor.getTagValue(response.toString(), "statusDesc"));
					responseMobileParams.put("statusMessage",
							XMLTagExtractor.getTagValue(response.toString(), "statusDesc"));
					responseMobileParams.put("messageCode", statusCode);
					responseMobileParams.put("conversationID",
							XMLTagExtractor.getTagValue(response.toString(), "originalRefNo"));
					responseMobileParams.put("targetSystemID",
							XMLTagExtractor.getTagValue(response.toString(), "responseID"));
					responseMobileParams.put("beneficiaryName",
							XMLTagExtractor.getTagValue(response.toString(), "destName"));
					responseMobileParams.put("accountStatus",
							XMLTagExtractor.getTagValue(response.toString(), "AccountStatus"));
					responseMobileParams.put("verificationDescription",
							XMLTagExtractor.getTagValue(response.toString(), "statusDesc"));
					responseMobileParams.put("response", response.toString());
					LOG.info(timelog+" : Response Map: " + responseMobileParams);
				}

			} catch (IOException ioe) {
				LOG.info(timelog+" : Error: " + ioe.toString());
				responseMobileParams.put("statusDescription", "Internal Server error");
				responseMobileParams.put("targetSystemID", "NotAvailable");
				LOG.info(timelog+" : Response Code = " + urlConnection.getResponseCode());

			} finally {

				if (httpResponseReader != null) {
					try {
						httpResponseReader.close();
					} catch (IOException ioe) {
						LOG.info(timelog+" : Error: " + ioe.toString());

						responseMobileParams.put("statusDescription", "Invalid response");
						responseMobileParams.put("targetSystemID", "NotAvailable");
					}
				}
			}
			responseMobileParams.put("messageCode", urlConnection.getResponseCode() + "");
			responseMobileParams.put("statusDescription", urlConnection.getResponseMessage() + "");
		} catch (MalformedURLException ex) {
			LOG.info(timelog+" : Error: " + ex.getMessage());

			responseMobileParams.put("statusDescription", "Invalid payload");
			responseMobileParams.put("targetSystemID", "NotAvailable");
		} catch (IOException ex) {
			LOG.info(timelog+" : Endpoint Exception: " + ex.getMessage());

			responseMobileParams.put("statusDescription", "Connection error");
			responseMobileParams.put("targetSystemID", "NotAvailable");
		}
		return responseMobileParams;
	}


}
