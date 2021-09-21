package io.mglobe.customerdetails.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

 

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

 
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

 

/**
 * 
 *
 */
@Service("fileUtilService")
public class FileUtil {

 

    ResourceLoader resourceLoader;

 

    //@Value("${reversal.message.file.name}")
    //String fileName;

 

    @Inject
    public FileUtil(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
    
    public static String reversalReq = "";

    /**
     * 
     * @param path
     * @return
     */
    @PostConstruct
    public void init() {
    	
    	
    	
    	
    	String fileName="pesalink_mobile_req.xml";
        try {    
            Resource resource = resourceLoader.getResource("classpath:hakikisha/" + fileName);
            InputStream stream = resource.getInputStream(); 
            StringBuilder textBuilder = new StringBuilder();
            try (Reader reader = new BufferedReader(new InputStreamReader
              (stream, Charset.forName(StandardCharsets.UTF_8.name())))) {
                int c = 0;
                while ((c = reader.read()) != -1) {
                    textBuilder.append((char) c);
                }
            }
            reversalReq = textBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @PreDestroy
    public void preDestroy() {
      
    }

}
