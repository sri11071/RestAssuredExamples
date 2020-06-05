package com.wavelabs;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import files.Payload;

public class FirstRestAssuredExample {

	public static void main(String[] args) {

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content_Type", "application/json")
				.body(Payload.AddPlace()).when().post("maps/api/place/add/json").then().log().all().assertThat().statusCode(200)
				.extract().response().asString();

		System.out.println(response);

		JsonPath js = new JsonPath(response);
		String placeid = js.getString("place_id");

		String updateresponse=given().log().all().queryParam("key", "qaclick123").header("Content_Type", "application/json")
				.body("{\r\n" + "\"place_id\":\"" + placeid + "\",\r\n"
						+ "\"address\":\"79 errrMerereauiiaa iooooooo, USA\",\r\n" + "\"key\":\"qaclick123\"\r\n" + "}")
				.when().put("maps/api/place/update/json").then().log().all().assertThat().statusCode(200).extract()
				.response().asString();
		
		System.out.println(updateresponse);

		String getresponse=given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeid)
				.when().get("maps/api/place/get/json").then().log().all()
				.assertThat().statusCode(200).extract().response().asString();
		
		
		System.out.println(getresponse);

	}
}
