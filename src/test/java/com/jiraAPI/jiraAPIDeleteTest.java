package com.jiraAPI;

import static io.restassured.RestAssured.*;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;

public class jiraAPIDeleteTest {
	
	@Test
	public void deleteIssue() {
	RestAssured.baseURI="http://localhost:8080/";
	
	SessionFilter session=new SessionFilter();
	
	String issueID="RES-13";
	given().header("Content-Type","application/json").log().all().
	body("{ \"username\": \"Srinivas\", \"password\": \"admin@123\" }").filter(session).
	when().post("rest/auth/1/session").then().assertThat().statusCode(200).log().all().extract().response().asString();

	given().pathParam("key", issueID).header("Content-Type","application/json").log().all().filter(session)
	.when().delete("rest/api/2/issue/{key}").then().assertThat().statusCode(204)
	.log().all().extract().response().asString(); 

	}
}
