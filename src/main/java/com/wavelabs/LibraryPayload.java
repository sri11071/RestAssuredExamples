package com.wavelabs;

public class LibraryPayload {

	public static String addBook(String name, String isbn, String aisle, String author) {

		String addbook_payload = "{\r\n" + "\r\n" + "\"name\":\"" + name + "\",\r\n" + "\"isbn\":\"" + isbn + "\",\r\n"
				+ "\"aisle\":\"" + aisle + "\",\r\n" + "\"author\":\"" + author + "\"\r\n" + "}";

		return addbook_payload;
	}

}