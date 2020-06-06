package com.udemyAPI;

import static io.restassured.RestAssured.given;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class UdemyExapleLibraryAPI {

	@Test(dataProvider = "BooksData")

	public void addBook(String isbn, String aisle)

	{

		RestAssured.baseURI = "http://216.10.245.166";

		String resp = given().

				header("Content-Type", "application/json").

				body(payloads.Addbook(isbn, aisle)).

				when().

				post("/Library/Addbook.php").

				then().assertThat().statusCode(200).

				extract().response().asString();

		JsonPath js = ReUsableMethods.rawToJson(resp);

		String bookId = js.get("ID");
		System.out.println(bookId);

		String deleteResponse = given().log().all().header("Content_Type", "application/json")
				.body("{\r\n" + " \r\n" + "\"ID\" : \"" + bookId + "\"\r\n" + " \r\n" + "} ").when()
				.post("/Library/DeleteBook.php").then().log().all().assertThat().statusCode(200).extract().response()
				.asString();
		System.out.println("Book successfully Deleted " + deleteResponse);

	}

	@DataProvider(name = "BooksData")

	public Object[][] getData()

	{

//array=collection of elements

//multidimensional array= collection of arrays

		return new Object[][] { { "oj3k2", "93632" }, { "ch22sas", "42523" }, { "as22aqw", "52233" } };

	}

}
