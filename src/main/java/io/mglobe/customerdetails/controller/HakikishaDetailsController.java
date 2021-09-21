package io.mglobe.customerdetails.controller;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.mglobe.customerdetails.bean.HakikishaDetailsService;
import io.mglobe.customerdetails.client.PesalinkClient;
import io.mglobe.customerdetails.models.request.HakikishaDetailsRequest;
import io.mglobe.customerdetails.models.response.HakikishaDetailsResponse;
import io.mglobe.customerdetails.utils.HakikishaConfigProps;

@RestController
@RequestMapping(value="/api")
public class HakikishaDetailsController {
	@Autowired
	HakikishaDetailsService hakkService;
	//@Autowired
	//HakikishaConfigProps config;
	public static final Logger LOG = LogManager.getLogger(HakikishaDetailsService.class);

	String timelog = new SimpleDateFormat("yyyyMMddHHmmssSS").format(new java.util.Date());
	
    @RequestMapping(value = "/hakikishaInfo", method = RequestMethod.POST, consumes = {"application/json", "application/xml"}, produces = {"application/json", "application/xml"})
    public HakikishaDetailsResponse hakikishaInfo(@RequestBody HakikishaDetailsRequest req, HttpServletRequest request, HttpServletResponse response) {
    	return hakkService.requestProcessor(req);
    	
    }
    @RequestMapping(value = "/hakikishaInfoMock", method = RequestMethod.GET, produces = {"application/json", "application/xml"})
    public HakikishaDetailsResponse hakikishaMock() {
    	
    	return hakkService.mockServiceTest();
    	
    }

}
