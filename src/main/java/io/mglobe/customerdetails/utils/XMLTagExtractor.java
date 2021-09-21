package io.mglobe.customerdetails.utils;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import io.mglobe.customerdetails.client.PesalinkClient;

public class XMLTagExtractor {
	public static final Logger LOG = LogManager.getLogger(PesalinkClient.class);

	 public static String getTagValue(String xml, String tagName) {

	        Document doc = null;
	        try {
	            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	            InputSource src = new InputSource();
	            src.setCharacterStream(new StringReader(xml));
	            doc = builder.parse(src);
	        } catch (Exception e) {

	        	LOG.info("Repo: Tag extraction error: " + e.toString());
	        }
	        return doc != null ? (doc.getElementsByTagName(tagName).item(0) != null ? doc.getElementsByTagName(tagName).item(0).getTextContent() : "") : "";
	    }
}
