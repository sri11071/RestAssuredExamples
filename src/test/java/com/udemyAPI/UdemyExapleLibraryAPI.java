package com.udemyAPI;

import static io.restassured.RestAssured.given;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class UdemyExapleLibraryAPI {

	@Test(dataProvider = "BooksData")

	public void addBook(String name,String isbn, String aisle,String author)

	{

		RestAssured.baseURI = "http://216.10.245.166";

		String resp = given().

				header("Content-Type", "application/json").

				body(payloads.Addbook(name,isbn, aisle,author)).

				when().

				post("/Library/Addbook.php").

				then().assertThat().statusCode(200).

				extract().response().asString();

		JsonPath js = ReUsableMethods.rawToJson(resp);

		String bookId = js.get("ID");
		System.out.println(bookId);

		String deleteResponse = given().log().all().header("Content_Type", "application/json")
				.body("{\r\n" + " \r\n" + "\"ID\" : \"" + bookId + "\"\r\n" + " \r\n" + "} ").when()
				.delete("/Library/DeleteBook.php").then().log().all().assertThat().statusCode(200).extract().response()
				.asString();
		System.out.println("Book successfully Deleted " + deleteResponse);

	}

	@DataProvider(name = "BooksData")

	public Object[][] getData()

	{

//array=collection of elements

//multidimensional array= collection of arrays

		return new Object[][] { {"888122", "J212", "J345","weqew" },{"432", "L212", "K93","211eqw" },{"1231", "J9282", "J829212","8293wwe" }};
	}

}
