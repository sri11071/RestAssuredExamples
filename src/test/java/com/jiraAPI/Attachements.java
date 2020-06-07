package com.jiraAPI;

import java.io.File;

public class Attachements {
	
	
	public static String attachement() {
		
		String projectPath = System.getProperty("user.dir") + File.separator;
		String filePath = projectPath + "src" + File.separator + "test" + File.separator + "resources" + File.separator
				+ "data" + File.separator;
		String fileName = filePath+"Addbookdetails.json";
		
		return fileName;
	}

	

}
