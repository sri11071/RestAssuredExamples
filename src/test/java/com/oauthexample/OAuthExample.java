package com.oauthexample;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import files.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class OAuthExample {

	@Test
	public void getCourses() {

		String url = "https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2F0gHQA0o9P37-h2XjfllHyPFI9avdAm1wajEiGnsnrWAfIp8DHxNefgz1S9GjdFM_siHQdjzARpPM38_fx1yBPcQ&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";

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

		String coursedetails = given().header("Content-Type", "application/json")
				.queryParam("access_token", accessToken).when().get("https://rahulshettyacademy.com/getCourse.php")
				.then().log().all().extract().response().asString();

		System.out.println(coursedetails);
	}

}
