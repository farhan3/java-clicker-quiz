package com.github.farhan3.jclickerquiz.common;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This protocol handles communication between the client and server.
 * 
 * @author farhan
 *
 */
public class Protocol {

	private static final String AVALIABLE_CHOICES_KEY = "Avaliable Choices";
	private static final String STUDENT_NUMBER_KEY = "Student Number";
	private static final String ANSWER_KEY = "Answer";

	private static final String EQUALS = "=";
	
	public static final String NOT_IN_CLASS = "Not in class";
	public static final String ALREADY_ANSWERED = "Already answered";
	
	public static String sendStudentNumber(int studentNumber) {
		return STUDENT_NUMBER_KEY + EQUALS + studentNumber;
	}
	
	public static boolean isStudentNumberMessage(String inputLine) {
		return inputLine.startsWith(STUDENT_NUMBER_KEY);
	}
	
	public static int recieveStudentNumber(String inputLine) {
		int studentNumber = 0;
		
		if (isStudentNumberMessage(inputLine)) {
			String studentNumberStr = inputLine.substring(inputLine.indexOf(EQUALS) + 1);
			studentNumber = Integer.parseInt(studentNumberStr);
		}
		
		return studentNumber;
	}

	public static String sendChoices(int numberOfChoices) {
		return AVALIABLE_CHOICES_KEY + EQUALS + numberOfChoices;
	}
	
	public static boolean isChoicesMessage(String inputLine) {
		return inputLine.startsWith(AVALIABLE_CHOICES_KEY);
	}
	
	public static String recieveChoicesString(String inputLine) {
		
		int numOfChoices = 0;
		
		if (isChoicesMessage(inputLine)) {
			String numOfChoicesStr = inputLine.substring(inputLine.indexOf(EQUALS) + 1);
			numOfChoices = Integer.parseInt(numOfChoicesStr);
		}
		
		StringBuffer print = new StringBuffer(100); 
		print.append("Please select from one of the following options: ");
		print.append('\n');
		
		Option[] options = Option.values();
		for (int i = 0; i < numOfChoices; i++) {
			print.append(options[i]);
			print.append('\n');
		}
		
		return print.toString();
	}
	
	public static Collection<String> recieveChoices(String inputLine) {
		Collection<String> choices = new ArrayList<String>(5);
		int numOfChoices = 0;
		
		if (isChoicesMessage(inputLine)) {
			String numOfChoicesStr = inputLine.substring(inputLine.indexOf(EQUALS) + 1);
			numOfChoices = Integer.parseInt(numOfChoicesStr);
		}
		
		Option[] options = Option.values();
		for (int i = 0; i < numOfChoices; i++) {
			choices.add(options[i].toString());
		}
		
		return choices;
	}
	
	public static String sendAnswer(Option option) {
		return ANSWER_KEY + EQUALS + option;
	}
	
	public static boolean isAnswerMessage(String inputLine) {
		return inputLine.startsWith(ANSWER_KEY);
	}
	
	public static Option recieveAnswer(String inputLine) {
		String optionStr = inputLine.substring(inputLine.indexOf(EQUALS) + 1);
		
		return Option.valueOf(optionStr);
	}

}
