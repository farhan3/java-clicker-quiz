package com.github.farhan3.jclickerquiz.model;

import static org.junit.Assert.*;

import java.net.URISyntaxException;
import java.util.Collection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataUtilTest {

	private static final String STUDENT_NUMBERS_FILE = 
			"/com/github/farhan3/jclickerquiz/resources/student_numbers.txt";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetStudentNumbers() throws URISyntaxException {
		Collection<Integer> studentNums = 
				DataUtil.getStudentNumbers(STUDENT_NUMBERS_FILE);
		
		assertFalse("The list of student numbers was empty. ", studentNums.isEmpty());
		
		int i = 1;
		
		System.out.println("Student Name \tNumber");
		for (Integer studentNum : studentNums) {
			System.out.println("Student " + i++ + ": \t" + studentNum);
		}
	}

}
