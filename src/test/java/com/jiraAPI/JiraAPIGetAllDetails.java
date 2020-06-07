package com.jiraAPI;

import static io.restassured.RestAssured.*;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;

public class JiraAPIGetAllDetails {
	
	
	
	@Test
	public void getJiraDetails() {
	RestAssured.baseURI="http://localhost:8080/";
	
	SessionFilter session=new SessionFilter();
	
	given().header("Content-Type","application/json").log().all().
	body("{ \"username\": \"Srinivas\", \"password\": \"admin@123\" }").filter(session).
	when().post("rest/auth/1/session").then().assertThat().statusCode(200).log().all().extract().response().asString();
	
	
	String issueDetails=given().header("Content-Type","application/json").log().all().filter(session).pathParam("key", "RES-19").
	when().get("/rest/api/2/issue/{key}").then().log().all().extract().response().asString();
	
	System.out.println(issueDetails);
	}

}
