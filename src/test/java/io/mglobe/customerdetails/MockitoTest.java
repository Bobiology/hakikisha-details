package io.mglobe.customerdetails;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Service;

import io.mglobe.customerdetails.bean.HakikishaDetailsService;
import io.mglobe.customerdetails.models.request.HakikishaDetailsRequest;
import io.mglobe.customerdetails.models.response.BankDetails;
import io.mglobe.customerdetails.models.response.HakikishaDetailsResponse;
import io.mglobe.customerdetails.models.response.HakikishaInfo;
import io.mglobe.customerdetails.models.response.PrimaryData;
import io.mglobe.customerdetails.models.response.ResponseHeaders;
import io.mglobe.customerdetails.models.response.ResponsePayload;
import io.mglobe.customerdetails.repo.HakikishaDetailsRepo;

@SpringBootTest
public class MockitoTest {

	//@Autowired
	 //@Qualifier("HakikishaDetailsService")
	private HakikishaDetailsService service = new HakikishaDetailsService();
	
	HakikishaDetailsResponse resp = new HakikishaDetailsResponse();
	BankDetails bank = new BankDetails();
	ResponseHeaders header = new ResponseHeaders();
	HakikishaInfo hakiInfo = new HakikishaInfo();
	PrimaryData data = new PrimaryData();
	ResponsePayload payload = new ResponsePayload();
	
	HakikishaDetailsRequest req = new HakikishaDetailsRequest();
	
	@MockBean
	private HakikishaDetailsRepo repo;
	
	@Test
	public void checkStatusCodeTest() {
		/*
		List<BankDetails> myBanks = new ArrayList<>();
		bank.setBankCode("1001");
		bank.setBankName("KCB Bank");
		myBanks.add(bank);
		bank.setBankCode("2002");
		bank.setBankName("Equity Bank");
		myBanks.add(bank);
		hakiInfo.setBankDetails(myBanks);
		hakiInfo.setBeneficiaryName("Sakawa Elijah Bob");
		hakiInfo.setTransactionCost(30);
		
		header.setConversationID("QWERTY122");
		header.setMessageID("123456789");
		header.setMessageCode("0");
		header.setRouteCode("1001");
		header.setStatusCode("0");
		header.setStatusDescription("Successfully processed");
		header.setStatusMessage("Success");
		header.setTargetSystemID("Available");
		
		data.setBusinessKey("1238765432");
		data.setBusinessKeyType("AccountNumber");
		
		payload.setHakikishaInfo(hakiInfo);
		payload.setPrimaryData(data);
		
		resp.setHeader(header);
		resp.setResponsePayload(payload);
		*/
		when(repo.getResponse()).thenReturn(resp);
		assertEquals("0", service.mockServiceTest().getHeader().getStatusCode());
	}
	@Test
	public void checkBusinessTypeTest() {
		when(repo.getResponse()).thenReturn(resp);
		assertEquals("AccountNumber", service.mockServiceTest().getResponsePayload().getPrimaryData().getBusinessKeyType());
	}
}
