package com.wavelabs;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import files.Payload;

public class FirstRestAssuredExample {

	public static void main(String[] args) {

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response=given().log().all().queryParam("key", "qaclick123").header("Content_Type", "application/json")
				.body(Payload.AddPlace()).when().post("maps/api/place/add/json").then().assertThat()
				.statusCode(200).extract().response().asString();
		
		System.out.println(response);
		
		JsonPath js=new JsonPath(response);
		String place_id=js.getString("place_id");
	}
}
