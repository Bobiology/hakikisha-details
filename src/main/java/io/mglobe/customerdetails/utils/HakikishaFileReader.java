package io.mglobe.customerdetails.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.inject.Inject;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class HakikishaFileReader {
    ResourceLoader resourceLoader;
	 @Inject
	    public HakikishaFileReader(ResourceLoader resourceLoader) {
	        this.resourceLoader = resourceLoader;
	    }
	    public static String fileContent="";

	public String readFile(String fileName) {
		
		fileName="pesalink_mobile_req.xml";
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
            fileContent = textBuilder.toString();
        } catch (IOException e) {
            System.out.println("SOMETHING WRONG");
        }
		return fileContent;
	}
}
