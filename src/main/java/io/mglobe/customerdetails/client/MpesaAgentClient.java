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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.mglobe.customerdetails.models.request.HakikishaDetailsRequest;
import io.mglobe.customerdetails.models.response.HakikishaDetailsResponse;
import io.mglobe.customerdetails.utils.HakikishaConfigProps;
import io.mglobe.customerdetails.utils.HakikishaFileReader;
import io.mglobe.customerdetails.utils.XMLTagExtractor;

public class MpesaAgentClient {

	HakikishaConfigProps ipslConfig;

	public MpesaAgentClient(HakikishaConfigProps ipslConfig) {
		this.ipslConfig = ipslConfig;
	}

	HakikishaDetailsResponse hakikisha;
	HakikishaFileReader fileReader;

	public final Logger LOG = LogManager.getLogger(PesalinkClient.class);
	String timelog = new SimpleDateFormat("yyyyMMddHHmmssSS").format(new java.util.Date());
	
	public Map<String, String> ValidatingWithAgentNumber(HakikishaDetailsRequest req) {
		Map<String, String> agentResponseParams = new HashMap<>();

		LOG.info(timelog + " : configs= " + ipslConfig.toString());
		String mpesaAgentReq = null;
		
		try (InputStream inputStream = getClass().getResourceAsStream(ipslConfig.getAgentReqFileName()+"");
			    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			mpesaAgentReq = reader.lines().collect(Collectors.joining(System.lineSeparator()))
			    		.replace("${MESSAGE_ID}", req.getHeader().getMessageID())
						.replace("${AGENT_NUMBER}", req.getRequestPayload().getPrimaryData().getBusinessKey());
			} catch (IOException e1) {
				LOG.error(timelog + " : File Not Found: "+e1.getMessage());
			}

		LOG.info(timelog + " : ------------------------------------------------------------------------------");

		LOG.info(timelog + " : Mpesa Agent - Request Endpoint : " + ipslConfig.getAgentHostUrl());

		LOG.info(timelog + " : Mpesa Agent - Incoming Request : " + req);

		LOG.info(timelog + " : ------------------------------------------------------------------------------");
		agentResponseParams.put("transactionCost", "0");
		agentResponseParams.put("businessKeyType", req.getRequestPayload().getPrimaryData().getBusinessKeyType());
		agentResponseParams.put("businessKey", req.getRequestPayload().getPrimaryData().getBusinessKey());

		agentResponseParams.put("messageID", req.getHeader().getMessageID());
		agentResponseParams.put("routeCode", req.getHeader().getRouteCode());
		agentResponseParams.put("targetSystemID", "NotAvailable");
		agentResponseParams.put("statusCode", "1");
		agentResponseParams.put("statusDescription", "Failure");
		agentResponseParams.put("statusMessage", "Failure");
		agentResponseParams.put("callBackURL", req.getHeader().getCallBackURL());

		return new MpesaAgentClient(ipslConfig).agentAPICall(mpesaAgentReq, agentResponseParams);

	}

	private Map<String, String> agentAPICall(String reqTemplate, Map<String, String> agentResponseParams) {
		String endpoint = ipslConfig.getAgentHostUrl().replace("null", "");
		String systemCode = ipslConfig.getAgentSystemCode().replace("null", "");
		String password = ipslConfig.getAgentPassword().replace("null", "");
		BufferedReader httpResponseReader = null;
		LOG.info(timelog + " : Payload Request =" + reqTemplate);
		LOG.info(timelog + " : Endpoint =" + endpoint);
		LOG.info(timelog + " : Payload systemCode =" + systemCode);
		LOG.info(timelog + " : Payload password =" + password.replace(password, "XXXXXXXX"));
		try {

			URL serverUrl = new URL(endpoint);

			HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();
			urlConnection.setRequestMethod("POST");

			urlConnection.addRequestProperty("Content-Type", "application/json");
			urlConnection.setRequestProperty("Accept", "application/json");
			urlConnection.setRequestProperty("username", systemCode);
			urlConnection.setRequestProperty("password", password);
			//String basicAuth = username + ":" + password;
			//String basicAuthPayload = "Basic" + " " + Base64.getEncoder().encodeToString(basicAuth.getBytes());
			//LOG.info(timelog + " : Authorization Headers : " + basicAuthPayload);
			//urlConnection.setRequestProperty("Authorization", basicAuthPayload.trim());
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
						+ " :=============================== RESPONSE - MPESA AGENT ===============================");
				LOG.info(timelog + " : " + response.toString());

				JsonParser parser = new JsonParser();
				JsonElement jsonTree = parser.parse(response.toString());
				JsonElement headerElement = null;
				JsonElement responseBody = null;
				
				if (jsonTree.isJsonObject()) {
					JsonObject jsonObject = jsonTree.getAsJsonObject();
					agentResponseParams.put("statusCode",
							jsonObject.get("ResponseCode").toString().replaceAll("^\"|\"$", ""));
					agentResponseParams.put("statusDescription",
							jsonObject.get("ResponseDesc").toString().replaceAll("^\"|\"$", ""));
					agentResponseParams.put("statusMessage",
							jsonObject.get("ResponseDesc").toString().replaceAll("^\"|\"$", ""));
					agentResponseParams.put("messageCode",
							jsonObject.get("ResponseCode").toString().replaceAll("^\"|\"$", ""));
					agentResponseParams.put("beneficiaryName",
							jsonObject.get("AgentName").toString().replaceAll("^\"|\"$", ""));

				}

			}

			agentResponseParams.put("messageCode", urlConnection.getResponseCode() + "");
			agentResponseParams.put("statusDescription", urlConnection.getResponseMessage() + "");

		} catch (IOException ioe) {
			ioe.printStackTrace();

			agentResponseParams.put("statusDescription", "Unknown error");
			agentResponseParams.put("targetSystemID", "NotAvailable");
		} finally {

			if (httpResponseReader != null) {
				try {
					httpResponseReader.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();

					agentResponseParams.put("statusDescription", "Connection error");
					agentResponseParams.put("targetSystemID", "NotAvailable");
				}
			}
		}

		return agentResponseParams;
	}


}
