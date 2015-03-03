package com.github.farhan3.jclickerquiz.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class DataUtil {
	
	/**
	 * Read a text file containing student number, one per line, and
	 * return the list of student numbers.
	 * @param studentNumFilePath the file path of the text file
	 * @return list of student numbers obtained from the text file
	 * @throws URISyntaxException if there was an issue with the given file path
	 */
	public static Collection<Integer> getStudentNumbers(String studentNumFilePath)
			throws URISyntaxException {
		
		if (studentNumFilePath == null) {
			return Collections.emptyList();
		}
		
		Collection<Integer> studentNumber = new LinkedList<Integer>();

		URL url = DataUtil.class.getResource(studentNumFilePath);
		
		if (url == null) {
			System.err.println(Thread.currentThread().getStackTrace()[1] + ": \n \t" + 
					"The URL for the file <" + studentNumFilePath + "> was null. ");
			return null;
		}
		
		File file;
		try {
			file = new File(url.toURI());
		} catch (URISyntaxException err) {
			err.printStackTrace();
			return null;
		}
		
		if (file == null || !file.isFile()) {
			System.err.println(Thread.currentThread().getStackTrace()[1] + ": \n \t" + 
					"The file <" + studentNumFilePath + "> is not a file. ");
			return null;
		}
		
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				studentNumber.add(Integer.parseInt(line));
			}
		} catch (FileNotFoundException err) {
		    err.printStackTrace();
		    return null;
		} catch (IOException err) {
		    err.printStackTrace();
		    return null;
		} finally {
		    try {
		        if (reader != null) {
		            reader.close();
		        }
		    } catch (IOException err2) {
		   	 err2.printStackTrace();
		    }
		}
		
		return studentNumber;
	}
}
