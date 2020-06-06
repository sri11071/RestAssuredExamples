package com.udemyAPI;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;

import java.nio.file.Paths;

import org.testng.annotations.Test;

import files.ReUsableMethods;

import files.payloads;

import io.restassured.RestAssured;

import io.restassured.path.json.JsonPath;

import io.restassured.response.Response;

public class UdemyStaticJson {

	@Test

	public void addBook() throws IOException

	{

		RestAssured.baseURI = "http://216.10.245.166";

		String projectPath = System.getProperty("user.dir") + File.separator;
		String filePath = projectPath + "src" + File.separator + "test" + File.separator + "resources" + File.separator
				+ "data" + File.separator;
		String fileName = filePath+"Addbookdetails.json";

		String resp = given().

				header("Content-Type", "application/json").

				body(Files.readAllBytes(Paths.get(fileName))).

				
				when().

				post("/Library/Addbook.php").

				then().assertThat().statusCode(200).

				extract().response().asString();

		JsonPath js = ReUsableMethods.rawToJson(resp);

		String id = js.get("ID");

		System.out.println(id);

		// deleteBOok

	}

	public static String GenerateStringFromResource(String path) throws IOException {

		return new String(Files.readAllBytes(Paths.get(path)));

	}

}