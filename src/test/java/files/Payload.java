package files;

import com.wavelabs.FirstRestAssuredExample;

public class Payload {
	
	
	public static String AddPlace() {
		
		return "{\r\n" + 
				"  \"location\": {\r\n" + 
				"    \"lat\": -37.383333,\r\n" + 
				"    \"lng\": 33.4232342\r\n" + 
				"  },\r\n" + 
				"  \"accuracy\": 50,\r\n" + 
				"  \"name\": \"Checked new house\",\r\n" + 
				"  \"phone_number\": \"(+91) 984 893 3937\",\r\n" + 
				"  \"address\": \"49, Neridda layout, cohen 09\",\r\n" + 
				"  \"types\": [\r\n" + 
				"    \"shoew park\",\r\n" + 
				"    \"shop\"\r\n" + 
				"  ],\r\n" + 
				"  \"website\": \"http://google.com\",\r\n" + 
				"  \"language\": \"French-IN\"\r\n" + 
				"}";
	}
	
	


}
