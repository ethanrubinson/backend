package com.ebay.queens.demo;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.io.IOException;

/**
 * Represents a class to access and hit 
 * all of the Paypal api's 
 */
@CrossOrigin
@Component
@Path("/Paypal")
public class Paypal {
	
	Http httpClass = new Http();
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Paypal.class);
	
	public Paypal() throws IOException {
		logger.info("Paypal Constructor");
	}
	
	 /**
	   * Gets list of charities based off charity cause
	   * @param missionArea is passed in to filter the charities returned
	   * @return string of list of charities
	   */
	@GET
	@Path("/getcharity")
	@Produces(MediaType.APPLICATION_JSON)
	public String charitySearch(@QueryParam("missionArea") String missionArea) throws IOException {
		logger.info("Get Charity");
		String charitySearchResponse = this.advancedCharitySearch(missionArea);
		String queryId = charitySearchResponse.substring(13,49);
		String url = "https://api.paypal.com/v1/customer/charities?query_id=" + queryId;
		String response = Http.genericSendGET(url, "Paypal");
		logger.info(response);
		return response;
	}
		
	@GET
	@Path("/searchcharitytype")
	@Produces(MediaType.APPLICATION_JSON)
	public String advancedCharitySearch(@QueryParam("missionArea") String missionArea) throws IOException {
		logger.info("Advanced Charity Search");
		String url = "https://api.paypal.com/v1/customer/charity-search-queries";
		String requestBody = "{\r\n" + 
				"    \"charity\": {\r\n" + 
				"        \"mission_area\": \""+missionArea+"\"\r\n" + 
				"    }\r\n" + 
				"}";
		logger.info(requestBody);
		String response = Http.genericSendPOST(url, requestBody, "Paypal");
		return response;
	}
	
    @GET
    @Path("/getcharitytype")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCharityType() throws IOException {
    	logger.info("Get Charity Type");
    	String url = "https://api.sandbox.paypal.com/v1/customer/charities";
    	String response = Http.genericSendGET(url, "Paypal");
    	return response;
    }  
    
}