package io.mglobe.customerdetails.bean;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import io.mglobe.customerdetails.client.DarajaClient;
import io.mglobe.customerdetails.client.MpesaAgentClient;
import io.mglobe.customerdetails.client.PesalinkClient;
import io.mglobe.customerdetails.models.request.HakikishaDetailsRequest;
import io.mglobe.customerdetails.models.response.BankDetails;
import io.mglobe.customerdetails.models.response.HakikishaDetailsResponse;
import io.mglobe.customerdetails.models.response.HakikishaInfo;
import io.mglobe.customerdetails.models.response.PrimaryData;
import io.mglobe.customerdetails.models.response.ResponseHeaders;
import io.mglobe.customerdetails.models.response.ResponsePayload;
import io.mglobe.customerdetails.utils.HakikishaConfigProps;

@Service
public class HakikishaDetailsService {
	// @Autowired
	// HakikishaDetailsRepo headers;
	// @Autowired
	// HakikishaDetailsRequest detailsReq;

	@Autowired
	HakikishaConfigProps propConfig;

	HakikishaDetailsResponse hakikishaInfo = new HakikishaDetailsResponse();
	public static final Logger LOG = LogManager.getLogger(HakikishaDetailsService.class);

	String timelog = new SimpleDateFormat("yyyyMMddHHmmssSS").format(new java.util.Date());

	public HakikishaConfigProps getConfigProps() {
		return propConfig;
	}
	
	public HakikishaDetailsResponse requestProcessor(HakikishaDetailsRequest req) {
		LOG.info(timelog + " : ------------------------------------------------------------------------------- ");

		// LOG.info(timelog + " : LOADED CONFIGS : ");

		LOG.info(timelog + " : " + propConfig.toString());

		// LOG.info(timelog + " : Received Request: " + req);

		LOG.info(timelog + " : Received headers: " + req.getHeader());
		LOG.info(timelog + " : Received request Payload: " + req.getRequestPayload());
		LOG.info(timelog + " : ------------------------------------------------------------------------------- ");
		PesalinkClient pesalink = new PesalinkClient(propConfig);
		DarajaClient daraja = new DarajaClient(propConfig);
		if (req != null) {
			String providerSystem = req.getRequestPayload().getAdditionalData().getProviderSystem();
			String businessKeyType = req.getRequestPayload().getPrimaryData().getBusinessKeyType();
			String messageID = req.getHeader().getMessageID();

			if (providerSystem.equalsIgnoreCase("PESALINK") && businessKeyType.equalsIgnoreCase("AccountNumber")) {
				// route to pesalink account validation
				LOG.info(timelog + " : " + messageID + ": Hakikisha Microservice - PESALINK for ACCOUNT Validation");
				Map<String, String> mapper = new HashMap<>();
				mapper = pesalink.pesalinkValidatingWithAccount(req);
				LOG.info(timelog + " : RESPONSE MAP = " + mapper);
				hakikishaInfo = responseMapping(mapper,req);

			} else if (providerSystem.equalsIgnoreCase("T24") && businessKeyType.equalsIgnoreCase("AccountNumber")) {
				LOG.info(timelog + " : " + messageID + ": Hakikisha Microservice - T24 Validation");

				// route to daraja account validation
				// hakikishaInfo = mockService(req);
				Map<String, String> mapper = new HashMap<>();
				mapper = daraja.getDarajaAccountValidation(req);
				LOG.info(timelog + " : RESPONSE MAP = " + mapper);
				hakikishaInfo = responseMapping(mapper,req);
			} else if (providerSystem.equalsIgnoreCase("PESALINK")
					&& businessKeyType.equalsIgnoreCase("MobileNumber")) {
				Map<String, String> mapper = new HashMap<>();
				LOG.info(timelog + " : " + messageID
						+ ": Hakikisha Microservice - PESALINK for MOBILE NUMBER Validation");

				// route to pesalink mobile validation

				mapper = pesalink.pesalinkValidatingWithPhoneNumber(req);
				LOG.info(timelog + " : RESPONSE MAP = " + mapper);
				hakikishaInfo = responseMapping(mapper,req);
				// hakikishaInfo = mockService(req);

			} else if ((providerSystem.equalsIgnoreCase("SOPRA") || providerSystem.equalsIgnoreCase("MPESA"))
					&& businessKeyType.equalsIgnoreCase("MobilNumber")) {
				LOG.info(timelog + " : " + messageID + ": Hakikisha Microservice - SOPRA | MPESA Validation");

				// route to daraja account validation
				hakikishaInfo = mockService(req);
				
			} else if (providerSystem.equalsIgnoreCase("AGENCY") && businessKeyType.equalsIgnoreCase("AgentNumber")) {
				LOG.info(timelog + " : " + messageID + ": Hakikisha Microservice - AGENCY Validation");
				Map<String, String> mapper = new HashMap<>();
				mapper = new MpesaAgentClient(propConfig).ValidatingWithAgentNumber(req);
				// route to mpesa agent validation
				hakikishaInfo = responseMapping(mapper,req);
				//hakikishaInfo = mockService(req);
			} else {
				// not supported.
				LOG.info(timelog + " : " + messageID + ": Hakikisha Microservice - Not supported category ");

				hakikishaInfo = mockService(req);
				hakikishaInfo.getHeader().setStatusCode("1");
				hakikishaInfo.getHeader()
						.setStatusDescription("Provided businessKey, businessKeyType and providerSystem not supported");
				hakikishaInfo.getHeader().setStatusMessage("Failure");
				hakikishaInfo.getHeader().setTargetSystemID("NotAvailable");
				hakikishaInfo.getResponsePayload().setHakikishaInfo(null);
			}
		}

		LOG.info(timelog + " : ------------------------------------------------------------------------------- ");

		LOG.info(timelog + " : Generated Headers Response: " + hakikishaInfo.getHeader());
		LOG.info(timelog + " : Generated Payload Response: " + hakikishaInfo.getResponsePayload());
		LOG.info(timelog + " : Generated Response: " + hakikishaInfo);

		LOG.info(timelog + " : ------------------------------------------------------------------------------- ");

		return hakikishaInfo;

	}

	public HakikishaDetailsResponse responseMapping(Map<String, String> responseMap, HakikishaDetailsRequest req) {

		ResponseHeaders header = new ResponseHeaders();
		HakikishaInfo hakiInfo = new HakikishaInfo();
		PrimaryData data = new PrimaryData();
		ResponsePayload payload = new ResponsePayload();
		try {
			header.setConversationID(responseMap.get("conversationID"));
			header.setMessageCode(responseMap.get("messageCode"));
			header.setMessageID(responseMap.get("messageID"));
			header.setRouteCode(responseMap.get("routeCode"));

			header.setTargetSystemID(responseMap.get("targetSystemID"));

			hakiInfo.setBeneficiaryName(responseMap.get("beneficiaryName"));
			hakiInfo.setTransactionCost(Double.parseDouble(responseMap.get("transactionCost")));
		} catch (Exception e) {

		}

		hakikishaInfo.setHeader(header);

		try {
			if (responseMap.get("beneficiaryName").replace("null", "").isEmpty()
					|| responseMap.get("beneficiaryName").replace("null", "") == null
					|| responseMap.get("beneficiaryName") == "null") {
				responseMap.put("statusDescription", "No Record found");
				responseMap.put("statusCode", "1");
				// responseMap.put("statusMessage", "Failure");
			}

		} catch (Exception e) {

		}
		try {
			header.setStatusCode(responseMap.get("statusCode"));
		} catch (Exception e) {
			header.setStatusCode("1");
		}
		try {
			header.setStatusMessage(responseMap.get("statusMessage"));
		} catch (Exception e) {
			header.setStatusMessage("Failed");
		}
		try {
		header.setStatusDescription(responseMap.get("statusDescription"));
	} catch (Exception e) {
		header.setStatusMessage("Request Failed");
	}
		try {
		data.setBusinessKey(responseMap.get("businessKey"));
		data.setBusinessKeyType(responseMap.get("businessKeyType"));
		} catch (Exception e) {
			data.setBusinessKey(req.getRequestPayload().getPrimaryData().getBusinessKey());
			data.setBusinessKeyType(req.getRequestPayload().getPrimaryData().getBusinessKeyType());
		}
		// Arrays.asList(responseMap.get("bankDetails"));
		try{
			hakiInfo.setBankDetails(XPath(responseMap.get("response")));
		}catch(Exception e) {
			
		}

		payload.setHakikishaInfo(hakiInfo);
		payload.setPrimaryData(data);
		hakikishaInfo.setResponsePayload(payload);

		return hakikishaInfo;
	}

	public HakikishaDetailsResponse mockService(HakikishaDetailsRequest req) {

		ResponseHeaders header = new ResponseHeaders();
		header.setConversationID("37e1965f-10f8-4f25-bcff-daa8d877c06b");
		header.setMessageCode("OSP-1002");
		try {
			header.setMessageID(req.getHeader().getMessageID());
		} catch (Exception e) {

		}
		header.setRouteCode("101");
		header.setStatusDescription("Success");
		header.setStatusMessage("Processed Successfully");
		header.setTargetSystemID("Available");
		header.setStatusCode("0");

		hakikishaInfo.setHeader(header);

		HakikishaInfo hakiInfo = new HakikishaInfo();
		hakiInfo.setBeneficiaryName("JOHN DOE");
		hakiInfo.setTransactionCost(33);

		List<BankDetails> banks = new LinkedList<BankDetails>();

		BankDetails bankDetails = new BankDetails();
		BankDetails bd = new BankDetails();
		bankDetails.setBankCode("1004");
		bankDetails.setBankName("Equity");
		bd.setBankCode("1000");
		bd.setBankName("KCB");
		banks.add(bankDetails);
		banks.add(bd);

		hakiInfo.setBankDetails(banks);

		PrimaryData data = new PrimaryData();
		try {
			data.setBusinessKey(req.getRequestPayload().getPrimaryData().getBusinessKey());
			data.setBusinessKeyType(req.getRequestPayload().getPrimaryData().getBusinessKeyType());
		} catch (Exception e) {

		}
		ResponsePayload payload = new ResponsePayload();
		payload.setHakikishaInfo(hakiInfo);
		payload.setPrimaryData(data);
		hakikishaInfo.setResponsePayload(payload);

		return hakikishaInfo;
	}

	public HakikishaDetailsResponse mockServiceTest() {

		ResponseHeaders header = new ResponseHeaders();
		header.setConversationID("37e1965f-10f8-4f25-bcff-daa8d877c06b");
		header.setMessageCode("OSP-1002");

		header.setRouteCode("101");
		header.setStatusDescription("Success");
		header.setStatusMessage("Processed Successfully");
		header.setTargetSystemID("Available");
		header.setStatusCode("0");

		hakikishaInfo.setHeader(header);

		HakikishaInfo hakiInfo = new HakikishaInfo();
		hakiInfo.setBeneficiaryName("JOHN DOE");
		hakiInfo.setTransactionCost(33);

		List<BankDetails> banks = new LinkedList<BankDetails>();

		BankDetails bankDetails = new BankDetails();
		BankDetails bd = new BankDetails();
		bankDetails.setBankCode("1004");
		bankDetails.setBankName("Equity");
		bd.setBankCode("1000");
		bd.setBankName("KCB");
		banks.add(bankDetails);
		banks.add(bd);

		hakiInfo.setBankDetails(banks);

		PrimaryData data = new PrimaryData();

		ResponsePayload payload = new ResponsePayload();
		payload.setHakikishaInfo(hakiInfo);
		payload.setPrimaryData(data);
		hakikishaInfo.setResponsePayload(payload);

		return hakikishaInfo;
	}

	private List<BankDetails> XPath(String response) {
		List<BankDetails> banks = new ArrayList<BankDetails>();
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			try {
				InputSource is = new InputSource(new StringReader(response));
				Document xmlDocument = builder.parse(is);

				NodeList nodeList = xmlDocument.getElementsByTagName("bank");
				for (int i = 0; i < nodeList.getLength(); i++) {
					BankDetails bd = new BankDetails();
					Node node = nodeList.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) node;
						bd.setBankName(eElement.getElementsByTagName("bankName").item(0).getTextContent());
						bd.setBankCode(eElement.getElementsByTagName("sortCode").item(0).getTextContent());
						banks.add(bd);
					}
				}
				LOG.info(timelog + " : BankDetails = " + banks);
			} catch (Exception e) {

			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		return banks;

	}

}
