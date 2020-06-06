package com.wavelabs;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LibraryAPITest {

	@Test(dataProvider = "booksInformation")
	public void addBookTest(String name, String isbn, String aisle, String author) {

		RestAssured.baseURI = "http://216.10.245.166";
		String addbookResponse = given().log().all().header("Content_Type", "application/json")
				.body(LibraryPayload.addBook(name, isbn, aisle, author)).when().post("Library/Addbook.php").then().log()
				.all().assertThat().statusCode(200).extract().response().asString();

		JsonPath js = ReUsableMethods.rawToJson(addbookResponse);
		String bookId = js.get("ID");
		System.out.println(bookId);

		String deleteResponse = given().log().all().header("Content_Type", "application/json")
				.body("{\r\n" + " \r\n" + "\"ID\" : \"" + bookId + "\"\r\n" + " \r\n" + "} ").when()
				.post("/Library/DeleteBook.php").then().log().all().assertThat().statusCode(200).extract().response()
				.asString();
		System.out.println("Book successfully Deleted " + deleteResponse);
	}

	@DataProvider
	public Object[][] booksInformation() throws Exception {
		int rowCount = 0;
		int colCount = 0;
		String projectPath = System.getProperty("user.dir") + File.separator;
		String filePath = projectPath + "src" + File.separator + "test" + File.separator + "resources" + File.separator
				+ "data" + File.separator;
		String fileName = "Books.xlsx";
		FileInputStream fis = new FileInputStream(filePath + fileName);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet mySheet = workbook.getSheet("bookDetails");
		rowCount = mySheet.getLastRowNum() + 1;
		XSSFRow row = mySheet.getRow(0);
		colCount = row.getLastCellNum();

		Object[][] data = new Object[rowCount][colCount];

		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				data[i][j] = mySheet.getRow(i).getCell(j).getStringCellValue();
				System.out.println("Row details  " + i + " column details J:" + j);
				System.out.println(mySheet.getRow(i).getCell(j).getStringCellValue() + "\t");

			}
		}
		workbook.close();
		return data;
	}
}
