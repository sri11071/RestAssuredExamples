package com.jiraAPI;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.ReUsableMethods;
import files.ReuseableCode;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class JiraAPIGetDetailsandValidate {

	String dir = System.getProperty("user.dir");
	String datafile = dir + "\\src\\test\\resources\\data.properties";
	Properties pos = new Properties();

	@Test()

	public void getPartialResponseandverifyDetails() throws Exception {

		FileInputStream fis = new FileInputStream(datafile);
		pos.load(fis);
		RestAssured.baseURI = "http://localhost:8080/";
		SessionFilter session = new SessionFilter();

		given().log().all().header("Content-Type", "application/json").filter(session)
				.body("{ \"username\": \"Srinivas\", \"password\": \"admin@123\" }").when().post("rest/auth/1/session")
				.then().log().all().extract().response().asString();

		
		 String createResponse = given().header("Content-Type",
		 "application/json").log().all().filter(session) .body("{\r\n" +
		  "  \"fields\": {\r\n" + "  	\"project\": {\r\n" +
		  "      \"key\": \"RES\"\r\n" + "    },\r\n" +
		  "    \"summary\": \" JiraAPIGetDetailsandValidate. getPartialResponseandverifyDetails \",\r\n"
		  + "    \"issuetype\":{\r\n" + "    	\"name\":\"Bug\"\r\n" + "    },\r\n" +
		  "    \"description\":\"Description about jira\"\r\n" + "  }\r\n" + " }")
		  .when().post("rest/api/2/issue").then().assertThat().statusCode(201).log().
		  all().extract().asString();
		  
		 String actualcomment = "First comment on jira with method alResponseandverifyDetails ";

		  JsonPath js = ReUsableMethods.rawToJson(createResponse); String key =
		  js.getString("key");
		  
		  given().pathParam("key", key).header("Content-Type", "application/json").log()
			.all()
			.body("{\r\n" + "    \"body\": \"" + actualcomment +"\",\r\n" + "    \"visibility\": {\r\n"
					+ "        \"type\": \"role\",\r\n" + "        \"value\": \"Administrators\"\r\n" + "    }\r\n"
					+ "}")
			.filter(session).when().post("rest/api/2/issue/{key}/comment").then().assertThat().statusCode(201).log()
			.all().extract().response().asString();
		 

		String actualcomment1 = "This Verification comment JiraAPIGetDetailsandValidate. getPartialResponseandverifyDetails ";

		String commentResponse = given().pathParam("key", key).header("Content-Type", "application/json").log()
				.all()
				.body("{\r\n" + "    \"body\": \"" + actualcomment1 +"\",\r\n" + "    \"visibility\": {\r\n"
						+ "        \"type\": \"role\",\r\n" + "        \"value\": \"Administrators\"\r\n" + "    }\r\n"
						+ "}")
				.filter(session).when().post("rest/api/2/issue/{key}/comment").then().assertThat().statusCode(201).log()
				.all().extract().response().asString();

		JsonPath js1 = ReUsableMethods.rawToJson(commentResponse);
		String commentId = js1.getString("id");

		String issueDetails = given().pathParam("key", key).header("Content-Type", "application/json").log().all()
				.filter(session).queryParam("fields", "comment").when().get("rest/api/2/issue/{key}").then().log().all()
				.extract().response().asString();

		System.out.println(issueDetails);
		JsonPath js2 = ReuseableCode.rawtoJson(issueDetails);
		int totalcomments = js2.getInt("fields.comment.comments.size()");
		System.out.println(totalcomments);

		for (int i = 0; i < totalcomments; i++) {
			String commentIdIssue = js2.get("fields.comment.comments[" + i + "].id").toString();

			if (commentIdIssue.equalsIgnoreCase(commentId))

			{

				String message = js2.get("fields.comment.comments[" + i + "].body").toString();

				System.out.println(message);

				Assert.assertEquals(message, actualcomment1);
			}

		}

	}

}
