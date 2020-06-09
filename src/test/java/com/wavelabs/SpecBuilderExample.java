package com.wavelabs;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.wavelabs.pojo.AddPlaceAPI;
import com.wavelabs.pojo.Location;

import files.Payload;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilderExample {

	public static void main(String args[]) {

		AddPlaceAPI p = new AddPlaceAPI();
		p.setAccuracy(90);
		p.setAddress("Sriqqn1ivas");
		p.setLanguage("Enqgl1ish -EN");

		List<String> mytype = new ArrayList<String>();
		mytype.add("sh1qoee");
		mytype.add("ww1qiqwo");
		p.setTypes(mytype);
		Location l = new Location();
		l.setLat(-232.2922);
		l.setLng(312.9001);
		p.setLocation(l);
		p.setPhone_number("+929221012121");
		p.setWebsite("www.ashah1@ssdwsad.as");
		p.setName("Frontline house");
		

		RequestSpecification reqbuild = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();

		RequestSpecification addPlaceRequest1 = given().spec(reqbuild).body(p);

		ResponseSpecification resspec = new ResponseSpecBuilder().expectStatusCode(200)
				.expectContentType(ContentType.JSON).build();

		Response response = addPlaceRequest1.when().post("/maps/api/place/add/json").then().spec(resspec).extract()
				.response();

		String responseString = response.asString();
		System.out.println(responseString);
		
		JsonPath js = new JsonPath(responseString);
		String placeid = js.getString("place_id");

		// getPlace API:
		String getresponse = addPlaceRequest1.queryParam("place_id", placeid).when()
				.get("maps/api/place/get/json").then().log().all().assertThat().statusCode(200).extract().response()
				.asString();

		System.out.println(getresponse);

	}
}
