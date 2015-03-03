package com.github.farhan3.jclickerquiz.model;

import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.github.farhan3.jclickerquiz.common.Option;

/**
 * A Question object is created by specifying the number of choices
 * available to the students. The number of choices can be 2, 3, 4, or 5.
 * 
 * The answer provided by a specific student can then be added.
 * 
 * You can also obtain a list of students who have answered the question.
 * 
 * @author farhan
 *
 */
public class Question {
	
	private Map<Integer, Option> _results = new HashMap<Integer, Option>();
	
	// maximum number of choices available to the student for this question
	private int _numOfChoices = 0;
	
	// the string representing the question, this field can be optionally set
	private String _questionString;
	
	/**
	 * A Question object is created by specifying the number of choices
	 * available to the students. The number of choices can be 2, 3, 4, or 5.
	 * @param numOfChoices 2, 3, 4, or 5
	 */
	public Question(int numOfChoices) throws InvalidParameterException {
		
		if (numOfChoices < 2 || numOfChoices > 5) {
			throw new InvalidParameterException(
					"A Question must have 2, 3, 4, or 5 choices. ");
		}
		
		this._numOfChoices = numOfChoices;
	}

	/**
	 * Log a student's answer
	 * @param studentNum student number of the student
	 * @param option the option chosen by the student; this value
	 * should not be more than the number of choices this question allows
	 * @return true if the student's option was successfully logged, false otherwise
	 */
	public boolean logAnswer(Integer studentNum, Option option) {
		if (studentNum <= 0) {
			System.err.println(Thread.currentThread().getStackTrace()[1] + ": \n \t" + 
					"Student number <" + studentNum + "> was invalid.");
			return false;
		}
		
		if ((option.ordinal() + 1) > _numOfChoices) {
			System.err.println(Thread.currentThread().getStackTrace()[1] + ": \n \t" + 
					"The input option <" + option + "> exceeded " +
					"the maximum number of choices for this Question.");
			return false;
		}
		
		_results.put(studentNum, option);
		
		return true;
	}
	
	/**
	 * Get the option chosen by the student with the specified student number
	 * @param studentNum the student's number
	 * @return the student's entered option or null if there was no option logged
	 * for the student
	 */
	public Option getStudentsAnswer(Integer studentNum) {
		return _results.get(studentNum);
	}
	
	/**
	 * Associate a String with this question
	 * <br>
	 * Note: this field is optional
	 * @param questionString the String to associate with the question
	 */
	public void setQuestionString(String questionString) {
		_questionString = questionString;
	}
	
	/**
	 * Get the String associated with the question
	 * @return the String associated with the question, or null if no String is
	 * associated with the question
	 */
	public String getQuestionString() {
		return _questionString;
	}
	
	/**
	 * 
	 * @return number of choices this question has
	 */
	public int getNumberOfChoices() {
		return _numOfChoices;
	}
	
	/**
	 * Get a list of students who have answered this question
	 * @return list of students who have answered this question
	 */
	public Collection<Integer> getListOfStudents() {
		return _results.keySet();
	}
}
