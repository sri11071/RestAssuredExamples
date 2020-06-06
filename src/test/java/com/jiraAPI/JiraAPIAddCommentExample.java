package com.jiraAPI;

import org.testng.annotations.Test;

import files.ReUsableMethods;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class JiraAPIAddCommentExample {

	@Test
	public void jiraAPIExample() {
		
		RestAssured.baseURI="http://localhost:8080/";
		
		SessionFilter session=new SessionFilter();
		
		given().header("Content-Type","application/json").log().all().
		body("{ \"username\": \"Srinivas\", \"password\": \"admin@123\" }").filter(session).
		when().post("rest/auth/1/session").then().assertThat().statusCode(200).log().all().extract().response().asString();
		
		String createResponse=given().header("Content-Type","application/json").log().all().filter(session).
		body("{\r\n" + 
				"  \"fields\": {\r\n" + 
				"  	\"project\": {\r\n" + 
				"      \"key\": \"RES\"\r\n" + 
				"    },\r\n" + 
				"    \"summary\": \"Another Defect From PostMan\",\r\n" + 
				"    \"issuetype\":{\r\n" + 
				"    	\"name\":\"Bug\"\r\n" + 
				"    },\r\n" + 
				"    \"description\":\"Description about jira\"\r\n" + 
				"  }\r\n" + 
				" }").when().post("rest/api/2/issue").then().assertThat().statusCode(201).log().all().extract().asString();
		
		JsonPath js=ReUsableMethods.rawToJson(createResponse);
		String key=js.getString("key");
		
		String commentResponse=given().pathParam("key", key).header("Content-Type","application/json").log().all().
		body("{\r\n" + 
				"    \"body\": \"Adding comment on new Jira \",\r\n" + 
				"    \"visibility\": {\r\n" + 
				"        \"type\": \"role\",\r\n" + 
				"        \"value\": \"Administrators\"\r\n" + 
				"    }\r\n" + 
				"}").filter(session).when().post("rest/api/2/issue/{key}/comment").
		then().assertThat().statusCode(201).log().all().extract().response().asString();
		
		JsonPath js1=ReUsableMethods.rawToJson(commentResponse);
		js1.get("id");
		//updateComment
		given().pathParam("key", key).pathParam("commentid", js1.get("id")).header("Content-Type","application/json").log().all().
		body("{\r\n" + 
				"    \"body\": \"Updated  comment on new Jira aklskla \",\r\n" + 
				"    \"visibility\": {\r\n" + 
				"        \"type\": \"role\",\r\n" + 
				"        \"value\": \"Administrators\"\r\n" + 
				"    }\r\n" + 
				"}").filter(session).when().put("rest/api/2/issue/{key}/comment/{commentid}").
		then().assertThat().statusCode(200).log().all().extract().response().asString();
		
		//delete Comment
		
		//delete Issue
		
		given().pathParam("key", key).header("Content-Type","application/json").log().all().filter(session)
		.when().delete("rest/api/2/issue/{key}").then().assertThat().statusCode(204)
		.log().all().extract().response().asString(); 
	
	
	}

}
