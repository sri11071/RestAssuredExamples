package com.wavelabs;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.wavelabs.pojo.AddPlaceAPI;
import com.wavelabs.pojo.Location;

import files.Payload;
import io.restassured.RestAssured;

public class ExampleGoogleAPISerialize {

	@Test
	public void addPlace() {
		
		AddPlaceAPI p=new AddPlaceAPI();
		p.setAccuracy(50);
		p.setAddress("Srinivas");
		p.setLanguage("English -EN");
		
		List<String> mytype=new ArrayList<String>();
		mytype.add("shoee");
		mytype.add("wwiqwo");
		p.setTypes(mytype);
		
		Location l=new Location();
		l.setLat(-982.2922);
		l.setLng(12.9001);
		
		p.setPhone_number("+92921012121");
		p.setWebsite("www.ashah@ssdwsad.as");
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		//Addplace API:
		String response = given().log().all().queryParam("key", "qaclick123").header("Content_Type", "application/json")
				.body(p).when().post("maps/api/place/add/json").then().log().all().assertThat().statusCode(200)
				.extract().response().asString();

		System.out.println(response);
	}
}
