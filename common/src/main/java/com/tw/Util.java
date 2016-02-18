package com.tw;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.io.BufferedReader;
import java.io.FileReader;

public class Util {
	public static String getTextToDisplay() throws IOException {
		String parsedProps = "";
		Properties sysprops = System.getProperties();
		Enumeration e = sysprops.propertyNames();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			System.out.println(key + " : " + sysprops.getProperty(key));
			parsedProps += key + " : " + sysprops.getProperty(key)+"\n";
		}
		return parsedProps;
	}

	public static String getFileContentToDisplay() {
		String result = "";
		String obpUserHome = System.getProperty("user.home");
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(obpUserHome+"/buildinfo.props"));
			String line = br.readLine();
			while (line != null) {
				result += line+"\n";
				line = br.readLine();
			}
		} catch (IOException e) {
			result = "Trying to open file...\nCould not access file\nCheck if Java property 'user.home' is set to a directory\nand 'buildinfo.properties' file is there";
		}
		return result;
	}
}