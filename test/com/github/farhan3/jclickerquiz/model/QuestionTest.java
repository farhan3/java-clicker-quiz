package com.github.farhan3.jclickerquiz.model;

import static org.junit.Assert.*;

import java.security.InvalidParameterException;
import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.farhan3.jclickerquiz.common.Option;

public class QuestionTest {

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
	public void testInvalidQuestionConstructor() {
		
		try {
			new Question(0);
			assert false;
		} catch (InvalidParameterException e) {
			// do nothing
		}
		
		try {
			new Question(1);
			assert false;
		} catch (InvalidParameterException e) {
			// do nothing
		}
		
		try {
			new Question(6);
			assert false;
		} catch (InvalidParameterException e) {
			// do nothing
		}
	}
	
	@Test
	public void testValidNewQuestionConstructor() {
		Question validQuestion;
		
		validQuestion = new Question(2);
		assertEquals("Number of choices did not match", 2,
				validQuestion.getNumberOfChoices());
		
		validQuestion = new Question(3);
		assertEquals("Number of choices did not match", 3,
				validQuestion.getNumberOfChoices());
		
		validQuestion = new Question(4);
		assertEquals("Number of choices did not match", 4,
				validQuestion.getNumberOfChoices());
		
		validQuestion = new Question(5);
		assertEquals("Number of choices did not match", 5,
				validQuestion.getNumberOfChoices());
	}
	
	@Test
	public void testLogAnswer() {
		Question question = new Question(4);
		
		assertTrue("Answer was not logged successfully for " + 12345, 
				question.logAnswer(12345, Option.A));

		assertTrue("Answer was not logged successfully for " + 54321, 
				question.logAnswer(54321, Option.D));
		
		assertFalse("Answer should not have been logged successfully for " + -1,
				question.logAnswer(-1, Option.A));
		
		assertFalse("Answer should not have been logged successfully for " + 467,
				question.logAnswer(4561, Option.E));
		
	}

	@Test
	public void testQuestion() {
		Question question = new Question(5);
		
		int student1 = 12345;
		question.logAnswer(student1, Option.A);
		
		int student2 = 753753;
		question.logAnswer(student2, Option.E);
		
		int student3 = 9515158;
		question.logAnswer(student3, Option.C);
		
		Collection<Integer> studentNumbers = question.getListOfStudents();
		assertEquals("Question contained more student numbers than expected. ", 3, studentNumbers.size());
		
		assertTrue("Student's list did not contain <" + student1 + ">. ", studentNumbers.contains(student1));
		assertTrue("Student's list did not contain <" + student2 + ">. ", studentNumbers.contains(student2));
		assertTrue("Student's list did not contain <" + student3 + ">. ", studentNumbers.contains(student3));

	}

}
