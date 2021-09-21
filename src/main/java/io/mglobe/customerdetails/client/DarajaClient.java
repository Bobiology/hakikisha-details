package io.mglobe.customerdetails.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import io.mglobe.customerdetails.models.request.HakikishaDetailsRequest;
import io.mglobe.customerdetails.utils.DarajaAuth;
import io.mglobe.customerdetails.utils.HakikishaConfigProps;
import io.mglobe.customerdetails.utils.XMLTagExtractor;

@Service
public class DarajaClient {
	HakikishaConfigProps ipslConfig;

	public DarajaClient(HakikishaConfigProps ipslConfig) {
		this.ipslConfig = ipslConfig;
	}

	public static final Logger LOG = LogManager.getLogger(PesalinkClient.class);
	String timelog = new SimpleDateFormat("yyyyMMddHHmmssSS").format(new java.util.Date());
	String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());

	public Map<String, String> getDarajaAccountValidation(HakikishaDetailsRequest req) {
		Map<String, String> responseParams = new HashMap<>();
		String darajaAccountReq = null;
		LOG.info(timelog + " : configs= " + ipslConfig.toString());

		// pre-populate obvious parameters
		responseParams.put("businessKey", req.getRequestPayload().getPrimaryData().getBusinessKey());
		responseParams.put("businessKey", req.getRequestPayload().getPrimaryData().getBusinessKey());
		responseParams.put("callBackURL", req.getHeader().getCallBackURL());
		responseParams.put("messageID", req.getHeader().getMessageID());
		responseParams.put("transactionCost", "0");
		responseParams.put("businessKeyType", req.getRequestPayload().getPrimaryData().getBusinessKeyType());
		responseParams.put("beneficiaryBankCode", req.getRequestPayload().getAdditionalData().getBeneficiaryBankCode());
		responseParams.put("routeCode", req.getHeader().getRouteCode());
		
		
		try (InputStream inputStream = getClass().getResourceAsStream(ipslConfig.getDarajaReqFileName()+"");
			    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
			darajaAccountReq = reader.lines().collect(Collectors.joining(System.lineSeparator()))
					.replace("${LOGIN}", req.getHeader().getMessageID())
					.replace("${TIMESTAMP}", timeStamp)
					.replace("${ACCOUNT}", req.getRequestPayload().getPrimaryData().getBusinessKey())
					.replace("${SESSION_ID}", req.getHeader().getMessageID())
					.replace("${COMPANY_ID}", ipslConfig.getCompanyId())
					.replace("${CHANNEL_ID}", ipslConfig.getChannelId())
					.replace("${BILLER_ID}", ipslConfig.getBillerId())
					.replace("${SERVICE_ID}", ipslConfig.getServiceId()).replace("${PASSWORD}",
							new DarajaAuth().Auth("", req.getHeader().getMessageID(), ipslConfig.getDarajaPassword()));
			LOG.info(timelog + " : DARAJA REQ TEMPLATE = " + darajaAccountReq);

			LOG.info(timelog + " : ------------------------------------------------------------------------------");
			LOG.info(timelog + " : Daraja Account Validation - Request : " + darajaAccountReq);

			LOG.info(timelog + " : ------------------------------------------------------------------------------");
			return new DarajaClient(ipslConfig).sendToDarajaAPICall(darajaAccountReq, responseParams);
			} catch (IOException e1) {
				LOG.error(timelog + " : File Not Found: "+e1.getMessage());
			return null;	
			}
		
	}

	private Map<String, String> sendToDarajaAPICall(String darajaAccountReq, Map<String, String> responseParams) {

		BufferedReader httpResponseReader = null;
		String statusCode = "1";
		try {
			URL serverUrl = new URL(ipslConfig.getDarajaHostUrl() + "");
			HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();

			urlConnection.setRequestMethod("POST");

			urlConnection.addRequestProperty("Content-Type", "application/xml; utf-8");
			urlConnection.setRequestProperty("Accept", "application/xml");
			urlConnection.setDoOutput(true);

			try (OutputStream os = urlConnection.getOutputStream()) {
				byte[] input = darajaAccountReq.getBytes("utf-8");
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
				LOG.info("Response");
				LOG.info(response);

				if (response != null && !response.toString().isEmpty()) {

					if (XMLTagExtractor.getTagValue(response.toString(), "statusCode").equalsIgnoreCase("00")) {
						statusCode = "0";
					}
					responseParams.put("statusCode", statusCode);
					responseParams.put("statusDescription",
							XMLTagExtractor.getTagValue(response.toString(), "statusDesc"));
					responseParams.put("statusMessage", XMLTagExtractor.getTagValue(response.toString(), "statusDesc"));
					responseParams.put("messageCode", statusCode);
					responseParams.put("conversationID",
							XMLTagExtractor.getTagValue(response.toString(), "originalRefNo"));
					responseParams.put("targetSystemID",
							XMLTagExtractor.getTagValue(response.toString(), "responseID"));
					responseParams.put("beneficiaryName",
							XMLTagExtractor.getTagValue(response.toString(), "AccountName"));
					responseParams.put("accountStatus",
							XMLTagExtractor.getTagValue(response.toString(), "AccountStatus"));
					responseParams.put("verificationDescription",
							XMLTagExtractor.getTagValue(response.toString(), "statusDesc"));

					LOG.info("Response Map: " + responseParams);
				}

			} catch (IOException ioe) {
				LOG.info("Error: " + ioe.toString());
				responseParams.put("statusDescription", "Unknown error");
				responseParams.put("targetSystemID", "NotAvailable");

			} finally {

				if (httpResponseReader != null) {
					try {
						httpResponseReader.close();
					} catch (IOException ioe) {
						LOG.info("Error: " + ioe.toString());

						responseParams.put("statusDescription", "Invalid response");
						responseParams.put("targetSystemID", "NotAvailable");
					}
				}
			}
			responseParams.put("messageCode", urlConnection.getResponseCode() + "");
			responseParams.put("statusDescription", urlConnection.getResponseMessage() + "");
		} catch (MalformedURLException ex) {
			LOG.info("Error: " + ex.getMessage());

			responseParams.put("statusDescription", "Invalid payload");
			responseParams.put("targetSystemID", "NotAvailable");
		} catch (IOException ex) {
			LOG.info("Endpoint Exception: " + ex.getMessage());

			responseParams.put("statusDescription", "Connection error");
			responseParams.put("targetSystemID", "NotAvailable");
		}
		return responseParams;

	}

}
