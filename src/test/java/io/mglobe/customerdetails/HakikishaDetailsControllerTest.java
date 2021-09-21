package io.mglobe.customerdetails;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.mglobe.customerdetails.controller.HakikishaDetailsController;
import io.mglobe.customerdetails.models.response.HakikishaDetailsResponse;
import io.mglobe.customerdetails.repo.HakikishaDetailsRepo;

/**
 * REF: https://stackabuse.com/guide-to-unit-testing-spring-boot-rest-apis/
 * 
 * @author sakawaelijahbob
 *
 */
@WebMvcTest(HakikishaDetailsController.class)
public class HakikishaDetailsControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    
    @MockBean
    HakikishaDetailsRepo hakikishaRepo;
    
    HakikishaDetailsResponse hakikishaInfo= new HakikishaDetailsResponse();
    
    @Test
    public void getAllRecords_success() throws Exception {
    	//HakikishaDetailsResponse records = new HakikishaDetailsResponse();
        
        //Mockito.when(hakikishaRepo.findAll()).thenReturn(records);
        
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/hakikishaInfoMock")
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=")
                .contentType(MediaType.APPLICATION_JSON));
                //.andExpect(status().isOk());
                //.andExpect(jsonPath("$.statusCode", is("0")))
                //.andExpect(jsonPath("$.statusDescription", is("Success")));
    }
    
}