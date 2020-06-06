package com.wavelabs;

import org.testng.Assert;
import org.testng.annotations.Test;
import files.ReUsableMethods;
import io.restassured.path.json.JsonPath;

public class ComplexJsonTasks {

	@Test
	public static void jsonTasks(){
		// TODO Auto-generated method stub

		/*
		 * 1. Print No of courses returned by API
		 * 
		 * 2.Print Purchase Amount
		 * 
		 * 3. Print Title of the first course
		 * 
		 * 4. Print All course titles and their respective Prices
		 * 
		 * 5. Print no of copies sold by RPA Course
		 * 
		 * 6. Verify if Sum of all Course prices matches with Purchase Amount
		 */
		String response = MockResponse.mockResponse();
		System.out.println(response);
		JsonPath js = ReUsableMethods.rawToJson(response);
		// 1.Print No of courses returned by API
		int noOfcourses = js.getInt("courses.size()");
		System.out.println("Print No of courses returned by API :" + noOfcourses);

		// 2.Print Purchase Amount
		int purchaseamount = js.getInt("dashboard.purchaseAmount");
		System.out.println("Print Purchase Amount :" + purchaseamount);

		// Print Title of the first course
		String firstcourse = js.getString("courses[0].title");
		System.out.println("Print Title of the first course :" + firstcourse);

		// Print All course titles and their respective Prices

		for (int i = 0; i < noOfcourses; i++) {
			String coursttitle = js.getString("courses[" + i + "].title");
			int coursprice = js.getInt("courses[" + i + "].price");
			System.out.println("Print Title of the[" + i + "] course :" + coursttitle);
			System.out.println("Print price of the [" + i + "] course :" + coursprice);
		}

		// Print no of copies sold by RPA Course
		int RPAcopies = 0;
		for (int i = 0; i < noOfcourses; i++) {
			String coursttitle = js.getString("courses[" + i + "].title");
			if (coursttitle.equalsIgnoreCase("RPA")) {
				RPAcopies = js.getInt("courses[" + i + "].copies");
				break;
			}

		}
		System.out.println("Print no of copies sold by RPA Course :" + RPAcopies);

		// total Amount
		int allCoursesAmount = 0;

		for (int i = 0; i < noOfcourses; i++) {
			String coursttitle = js.getString("courses[" + i + "].title");
			int coursprice = js.getInt("courses[" + i + "].price");
			int courscopies = js.getInt("courses[" + i + "].copies");
			System.out.println("Print Title of the[" + i + "] course :" + coursttitle);
			System.out.println("Print price of the [" + i + "] course :" + coursprice);
			System.out.println("Print copies of the [" + i + "] course :" + courscopies);
			System.out.println("This course Amount is :" + coursprice * courscopies);
			allCoursesAmount = allCoursesAmount + (coursprice * courscopies);
		}

		System.out.println("total Amount: " + allCoursesAmount);

		Assert.assertEquals(purchaseamount, allCoursesAmount);

	}

}
