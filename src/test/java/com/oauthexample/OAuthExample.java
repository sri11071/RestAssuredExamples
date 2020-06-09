package com.oauthexample;

import org.testng.annotations.Test;

import com.wavelabs.pojo.Api;
import com.wavelabs.pojo.GetCourses;
import com.wavelabs.pojo.WebAutomation;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import files.*;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

public class OAuthExample {

	@Test
	public void getCourses() {

		String url = "https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2F0gF3FXmB39yZSNH0reWRDgx9_w8wuLsNi9O2hgxEdqVnJKzoZQjTzGDt-TMq_BGeinBFFJlPj-YzEy8gnn36a1c&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";
		String partialcode = url.split("code=")[1];

		String code = partialcode.split("&scope")[0];

		String response = given().urlEncodingEnabled(false).log().all().queryParams("code", code)
				.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParams("grant_type", "authorization_code").queryParams("state", "verifyfjdss")
				.queryParam("scope", "email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email").when()
				.post("https://www.googleapis.com/oauth2/v4/token").then().extract().response().asString();

		JsonPath jsonPath = new JsonPath(response);

		String accessToken = jsonPath.getString("access_token");

		System.out.println(accessToken);

		GetCourses gc = given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON).when()
				.get("https://rahulshettyacademy.com/getCourse.php").then().log().all().extract().response()
				.as(GetCourses.class);

		System.out.println(gc.getUrl());
		System.out.println(gc.getInstructor());

		List<Api> apicourse = gc.getCourses().getApi();
		for (int i = 0; i < apicourse.size(); i++) {
			String Coursetitle = gc.getCourses().getApi().get(i).getCourseTitle();

			if (Coursetitle.equalsIgnoreCase("SoapUI Webservices testing")) {
				System.out.println(gc.getCourses().getApi().get(i).getPrice());

			}
		}

		ArrayList<String> acl=new ArrayList<String>();
		List<WebAutomation> webautomation = gc.getCourses().getWebAutomation();
		for (int i = 0; i < webautomation.size(); i++) {
			String Coursetitle = gc.getCourses().getWebAutomation().get(i).getCourseTitle();
			System.out.println(Coursetitle);
			acl.add(Coursetitle);
			System.out.println(gc.getCourses().getWebAutomation().get(i).getPrice());

		}
		
	}

}
