package com.ebay.queens.demo;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.ebay.queens.requests.GetItem;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.io.IOException;
/**
 * Represents a class to access and hit 
 * all of the eBays api's 
 */
@Component
@Path("/v1")
public class Version1Api {
	
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Version1Api.class);
	Http httpClass = new Http();
	public static void main(String [] args) throws IOException {
		GetItem test = new GetItem();
		//advancedFindCharityItems("1726");
		//getItem("333460893922");
		logger.info("Testing");
	}
	
	 /**
     * Represents an api to search for the nonprofit id which is then used to bring back the charity items 
     * @param charityId - charityId is used to get information on the charity to obtain the external id to return a list of products
     */
	@GET
	@Path("/advancedfindcharityItems")
	@Produces(MediaType.APPLICATION_JSON)
	public String advancedFindCharityItems(@QueryParam("charityId") String charityId) throws IOException {
		String response = findNonProfit(charityId);
		String nonProfitId = response.substring(response.indexOf("nonprofitId")+12, response.indexOf("nonprofitId")+16);
		logger.info("Non Profit Id: " + nonProfitId);
		String response2 = findCharityItems(nonProfitId);
		logger.info("Response: " + response2);
		return response2;
	}
	
	 /**
     * Represents an api to return a list of products matching the search term 
     * @param searchTerm - uses the search term to return a list of products matching the search term
     */
	@GET
	@Path("/searchItem")
	@Produces(MediaType.APPLICATION_JSON)
	public String searchItem(@QueryParam("searchTerm") String searchTerm) throws IOException {
		logger.info("Search Item: " + searchTerm);
    	String url = "https://api.ebay.com/buy/browse/v1/item_summary/search?q=" + searchTerm;
    	String response = httpClass.genericSendGET(url, "searchItem");
    	return response;
	}
	
	 /**
     * Represents an api to return products from a specific charity with a specific listing type
     * @param charityItemId - uses the charity item id to return list of products/inventory for that specific charity
     */
    @GET
    @Path("/advancedcharityItems")
    @Produces(MediaType.APPLICATION_JSON)
    public String advancedCharitySearch(@QueryParam("charityItemId") String charityItemId, @QueryParam("listingType") String listingType) throws IOException {
    	logger.info("Advanced Charity Search");
    	logger.info("Charity Item Id: " + charityItemId + " Listing Type: " + listingType );
    	String requestBody = "{\r\n" + 
    			"    \"searchRequest\": {\r\n" + 
    			"        \"sortOrder\": \"BestMatch\",\r\n" + 
    			"        \"paginationInput\": {\r\n" + 
    			"            \"pageNumber\": 1,\r\n" + 
    			"            \"entriesPerPage\": 5\r\n" + 
    			"        },\r\n" + 
    			"        \"keyword\": \"phone\",\r\n" + 
    			"        \"constraints\": {\r\n" + 
    			"            \"globalAspect\": [\r\n" + 
    			"                {\r\n" + 
    			"                    \"constraintType\": \"CharityIds\",\r\n" + 
    			"                    \"value\": ["+charityItemId+"]\r\n" + 
    			"                },\r\n" + 
    			"                {\r\n" + 
    			"                    \"constraintType\": \"CharityOnly\",\r\n" + 
    			"                    \"value\": [\"true\"]\r\n" + 
    			"                },\r\n" + 
    			"                {\r\n" + 
    			"                    \"constraintType\": \"ListingType\",\r\n" + 
    			"                    \"value\": [\""+listingType+"\"],\r\n" + 
    			"                    \"paramNameValue\": [\r\n" + 
    			"                        {\r\n" + 
    			"                            \"name\": \"operator\",\r\n" + 
    			"                            \"value\": \"exclusive\"\r\n" + 
    			"                        }\r\n" + 
    			"                    ]\r\n" + 
    			"                }\r\n" + 
    			"            ]\r\n" + 
    			"        }\r\n" + 
    			"    }\r\n" + 
    			"}";
    	logger.info(requestBody);
    	String url = "https://api.ebay.com/buying/search/v2";
    	String response = httpClass.genericSendPOST(url, requestBody, "charityItem");
    	return response;
    }
    
    /**
     * Represents an api to return products from a specific charity
     * @param charityItemId - uses the charity item id to return list of products/inventory for that specific charity
     */
    @GET
    @Path("/findcharityItems")
    @Produces(MediaType.APPLICATION_JSON)
    public String findCharityItems(@QueryParam("charityItemId") String charityItemId) throws IOException {
    	logger.info("Find Charity Items");
    	String requestBody = "{\r\n" + 
    			"    \"searchRequest\": {\r\n" + 
    			"        \"sortOrder\": \"StartTimeNewest\",\r\n" + 
    			"        \"paginationInput\": {\r\n" + 
    			"            \"pageNumber\": 1,\r\n" + 
    			"            \"entriesPerPage\": 25\r\n" + 
    			"        },\r\n" + 
    			"        \"constraints\": {\r\n" + 
    			"            \"globalAspect\": [\r\n" + 
    			"                {\r\n" + 
    			"                    \"constraintType\": \"CharityIds\",\r\n" + 
    			"                    \"value\": ["+charityItemId+"]\r\n" + 
    			"                    \r\n" + 
    			"                },\r\n" + 
    			"                {\r\n" + 
    			"                    \"constraintType\": \"CharityOnly\",\r\n" + 
    			"                    \"value\": [\"true\"]\r\n" + 
    			"                }\r\n" + 
    			"            ]\r\n" + 
    			"        }\r\n" + 
    			"    }\r\n" + 
    			"}";
    	String url = "https://api.ebay.com/buying/search/v2";
    	logger.info("Request body: " + requestBody);
    	String response = httpClass.genericSendPOST(url, requestBody, "charityItem");
    	return response.toString();
    }
    
    /**
     * Represents an api to return specific product information 
     * @param input - uses the input to search for a specific product in the itemId field
     */
    @GET
    @Path("/getItem")
    @Produces(MediaType.APPLICATION_XML)
    public String getItem(@QueryParam("input")String input) throws IOException {
    	logger.info("Get Item Method");
    	String requestBody = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n" + 
    			"<GetItemRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\r\n" + 
    			"  <RequesterCredentials>\r\n" + 
    			"    <eBayAuthToken>AgAAAA**AQAAAA**aAAAAA**9enaXQ**nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6AGmICpDZmDpwWdj6x9nY+seQ**OSoGAA**AAMAAA**mc4q0e94Onut08iyCeBQDC6u1QQokVs4kukRMxe2PSdQ4CsKUIxSGhgFHFraUV0VJ1fLgYmKg6kXDQh0idHTgKjcHFPcuPDmtwT5TSH+SAKHw6TaZkAgVLgOMBprmwgLAJNiTESHarSfuuJxUdILw3lB/rrGC3tQ3sFz8l3SxpCx6NXvuOQ0++3aZ2gB/EPeiT70bR/z87bVkZYoK/OAG/vRCBVch5xo2PiJWJl5xLYpG3iz6aauTiJur3TKC3cPriMLWDkLAOqhXZol9jp2cknPesRfDypOBzDnvECNP81F18t0/3u/Lgn9BWxYdefCm95Rkw3XjZwQTG1GVLqBHoWBpG3s8eLuQhChlbH52ecF7sFb3aSXdvTcOCSwVHMA0GRjPhcoNt91WOfI22tUaJJ7/H72IqosJw235lvgvqQ5UQSzh5BE/Wp0u9bGzpUHgODeRtfO45miC+5itGBs0r1KKjN0CEzQ8WsDzWK2eGmmnznW8f2osEa83C9sa41/dEC5U1Cy8vpMgp/nz+qjKf+wQ3OUsSEgOKrjvC3tZcWUivhyu/GPEhAHEF6XBOTOyMnspoZKWNL4RMxGxfpeG3ANoer4vmdizPK7C6h3eLyTTYfL0jcML9Ld+rFKMD7hVx8ATu32nQVt3GmXa9m8cp2rSNPdgRV36LMWRxW2aXMq+MksRZkNhhm4WxSGUykR3N8K8jFiD9LPrz0pEux8UXEfF8ZWEKlxn2jCn9Tjy1WLMQ5ljotGHt+eMsfkaWoC</eBayAuthToken>\r\n" + 
    			"  </RequesterCredentials>\r\n" + 
    			"  <ItemID>"+ input + "</ItemID>\r\n" + 
    			"</GetItemRequest>";
    	String url = "https://api.ebay.com/ws/api.dll";
    	String response = httpClass.genericSendPOST(url, requestBody, "getItem");
    	GetItem test = new GetItem();
    	return response.toString();
    }
    
    /**
     * Represents an api to bring information on non profits 
     * @param nonProfitInput - uses the externalId to bring up information on nonprofit 
     */
    @GET
    @Path("/findnonProfit")
    @Produces(MediaType.APPLICATION_XML)
    public String findNonProfit(@QueryParam("nonProfitInput")String nonProfitInput) throws IOException {
    	logger.info("Find Non Profit Method");
    	String requestBody = 
    			"<findNonprofitRequest xmlns=\"http://www.ebay.com/marketplace/fundraising/v1/services\">\r\n" + 
    			"    <searchFilter>\r\n" + 
    			"        <externalId>"+nonProfitInput+"</externalId>\r\n" + 
    			"    </searchFilter>\r\n" + 
    			"    <outputSelector>Mission</outputSelector>\r\n" + 
    			"    <outputSelector>Address</outputSelector>\r\n" + 
    			"    <outputSelector>LargeLogoURL</outputSelector>\r\n" + 
    			"    <outputSelector>PopularityIndex</outputSelector>\r\n" + 
    			"    <outputSelector>EIN</outputSelector>\r\n" + 
    			"    <outputSelector>Description</outputSelector>\r\n" + 
    			"    <paginationInput>\r\n" + 
    			"        <pageNumber>1</pageNumber>\r\n" + 
    			"        <pageSize>25</pageSize>\r\n" + 
    			"    </paginationInput>\r\n" + 
    			"</findNonprofitRequest>";
    	String url = ("http://svcs.ebay.com/services/fundraising/FundRaisingFindingService/v1");
    	String response = httpClass.genericSendPOST(url,requestBody,"nonProfit");
    	logger.info(response.toString());
    	return response.toString();
    }
}