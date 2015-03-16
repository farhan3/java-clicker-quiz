package com.github.farhan3.jclickerquiz.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.Collection;
import java.util.LinkedList;

import com.github.farhan3.jclickerquiz.common.Option;
import com.github.farhan3.jclickerquiz.model.Question;
import com.github.farhan3.jclickerquiz.model.ClassManager;

/**
 * This class handles the user's input cmds, such as
 * START_QUESTION(n) and LIST.
 * 
 * @author farhan
 *
 */
public class ServerControl {

	private static final char 		OPEN_BRACKET 					= '(';
	private static final String 	START_QUESTION_CMD_PREFIX 	= "START_QUESTION";
	private static final String 	START_QUESTION_CMD 			= "START_QUESTION(n)";
	private static final String 	END_QUESTION_CMD 				= "END_QUESTION()";
	private static final String 	LIST_CMD 						= "LIST";
	private static final String 	HELP_CMD 						= "HELP";
	private static final String 	EXIT_CMD 						= "EXIT";
	private static final String 	DONE_CMD 						= "DONE";
	private static final String 	ADD_STUDENTS_CMD				= "ADD_STUDENTS";
	
	private static final String MAN_PAGE = "Avaliable Commands:\n"
			+ HELP_CMD + ": View the avaliable commands.\n"
			+ EXIT_CMD + ": Stop the server and exit the program.\n"
			+ ADD_STUDENTS_CMD + ": Add students to the class.\n\n"
			+ START_QUESTION_CMD + ": Starts a question with n choices and waits for students to connect and respond.\n"
			+ END_QUESTION_CMD + ": Ends the question; students can no longer send responses.\n"
			+ LIST_CMD + ": Lists students who have sent answers.\n";
	
	private int _portNumber = -1;

	private boolean 			_questionStarted = false;
	private Question 			_question;
	private ClientListener 	_clientListener;
	private ServerSocket		_serverSocket;
	
	private Collection<Question> _questions = new LinkedList<Question>();
	
	/**
	 * 
	 * @param portNumber the port which to start the server
	 */
	protected ServerControl(int portNumber) {
		_portNumber = portNumber;
	}

	/**
	 * start the server's cmd handler.
	 * 
	 * Note: this does not handle listening to clients trying to connect,
	 * that is done by ClientListener
	 * 
	 */
	protected void run() {
		System.out.println("Starting server...\n");
		
		System.out.println(MAN_PAGE);
		
		try (
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		) {
			String userInput = null;
			
			System.out.print("> "); //formatting
			
			while ((userInput = stdIn.readLine()) != null) {
				System.out.println(); //formatting
				
				// handle the user's input
				if (userInput.startsWith(START_QUESTION_CMD_PREFIX)) { // handle the START_QUESTION(n) cmd
					String returnVal = handleStartQuestionCmd(userInput);
					if (returnVal.equals("continue")) {
						continue;
					} else if (returnVal.equals("break")) {
						break;
					}
				} else if (userInput.equals(END_QUESTION_CMD)) { // handle the END_QUESTION cmd
					handleEndQuestionCmd(userInput);
				} else if (userInput.equals(LIST_CMD)) { // handle LIST cmd
					handleListCmd(userInput);
				} else if (userInput.equals(HELP_CMD)) { // handle HELP cmd
					System.out.println(MAN_PAGE);
				} else if (userInput.equals(EXIT_CMD)) { // handle EXIT cmd
						break;
				} else if (userInput.equals(ADD_STUDENTS_CMD)) { // handle ADD_STUDENTS cmd
					handleAddStudentsCmd(userInput, stdIn);
				} else { // handle other input
					System.err.println("ERROR: Invalid input. Please use the help "
							+ "command to view the avalible commands. ");
				}

				System.out.print("\n> "); //formatting
			}
			
			shutdown();
		} catch (IOException e) {
			System.err.println("ERROR: Could not read from System Input. ");
		}
	}
	

	/**
	 * Handle the case when the user enters the START_QUESTION(n) cmd
	 * @param userInput
	 * @return
	 */
	private String handleStartQuestionCmd(String userInput) {
		System.out.println("Starting question...");
		
		// ensure that the cmd's syntax is correct
		if ((userInput.length() != START_QUESTION_CMD.length()) || 
				!userInput.substring(0, userInput.indexOf(OPEN_BRACKET)).equals(START_QUESTION_CMD_PREFIX)) {
			System.err.println("ERROR: Syntax error with " + START_QUESTION_CMD + " command. ");
			System.out.print("\n> "); //formatting
			return "continue";
		}
		
		Character inputNumber = userInput.charAt((userInput.indexOf(OPEN_BRACKET) + 1));

		// ensure that the number of choices is 2 >= n >= 5
		if ( inputNumber < '2' || inputNumber > '5') {
			System.err.println("\nERROR: Syntax error with " + START_QUESTION_CMD + " command.\n"
					+ "The number of choices n must be 2, 3, 4 or 5. ");
			System.out.print("\n> "); //formatting
			return "continue";
		}

		int nOfChoices = Integer.parseInt(inputNumber.toString());
		
		// only one question can run at a time
		if (_questionStarted) { 
			System.err.println("ERROR: A question is already running. Use the "
					+ END_QUESTION_CMD + " command to stop it.");
		} else {
			
			if (ClassManager.getInstance().getStudents().isEmpty()) {
				System.err.println("WARNING: There are currently no students in the class. "
						+ "Use the " + ADD_STUDENTS_CMD + " command to add students."
						+ "The question will still be started. ");
			}
			
			_question = new Question(nOfChoices);
			_questions.add(_question);
	
			try {
				_serverSocket = new ServerSocket(_portNumber);
			} catch (IOException e){
				System.err.println("ERROR: Could not create the server on the specified port.\n"
						+ "Please choose a different port and try again.");
				System.out.print("\n> "); //formatting
				return "break";
			}
			
			// create a client listener to handle connecting clients
			_clientListener = new ClientListener(_serverSocket, _question);
			_clientListener.start();
			
			_questionStarted = true;
			
			System.out.println("Started question");
		}
		
		return "ok";
	}
	
	/**
	 * Handle the case when the user enters the END_QUESTION() cmd
	 * @param userInput
	 */
	private void handleEndQuestionCmd(String userInput) {
		System.out.println("Ending question...");
		
		// ensure that there is a question currently started
		if (!_questionStarted) {
			System.err.println("ERROR: There is no question currently running. Use the "
					+ START_QUESTION_CMD + " command to start a question.");
		} else {
			// stop the listener thread and close the server socket
			_clientListener.stopThread();
			try {
				_serverSocket.close();
				_clientListener.join();
				
				_questionStarted = false; 
				System.out.println("Ended question.");
			} catch (InterruptedException | IOException e) {
				System.err.println("ERROR: Unable to stop the client listener or close the server socket.");
				_questionStarted = false; 
			}
		}
	}
	
	/**
	 * Handle the case when the user enters the LIST cmd
	 * @param userInput
	 */
	private void handleListCmd(String userInput) {
		if (_question == null || _questions.isEmpty()) {
			System.err.println("ERROR: There was no question(s) created. Use the "
					+ START_QUESTION_CMD + " command to start a question.");
		} else if (ClassManager.getInstance().getStudents().isEmpty()) {
			System.err.println("ERROR: There was no students in the class. Use the "
					+ ADD_STUDENTS_CMD + " command to start add students.");
		} else {	
			System.out.println("The following students answered the question(s) (dash means no answer): ");
			
			int numOfQuestions = _questions.size();
			
			StringBuffer columnHeaders = new StringBuffer(100);
			
			columnHeaders.append("Student Number");
			
			for (int i = columnHeaders.length(); i < 20; i++) {
				columnHeaders.append(" ");
			}
			
			for (int i = 1; i <= numOfQuestions; i++) {
				columnHeaders.append("Q" + i + "   ");
			}
			
			System.out.println(columnHeaders.toString());
			
			StringBuffer studentRow;
			Option studentAnswer;
			for (int studentNumber : ClassManager.getInstance().getStudents()) {
				studentRow = new StringBuffer(50);
				studentRow.append(studentNumber);
				
				for (int i = studentRow.length(); i < 20; i++) {
					studentRow.append(" ");
				}
				
				for (Question question : _questions) {
					studentAnswer = question.getStudentsAnswer(studentNumber);
					
					if (studentAnswer == null) {
						studentRow.append("-");
					} else {
						studentRow.append(studentAnswer.toString());
					}
					
					studentRow.append("    ");
				}
				
				System.out.println(studentRow.toString());
			}
		}
	}
	
	/**
	 * Handle the case when the user enters the ADD_STUDENTS cmd
	 * @param userInput
	 * @param stdIn required to continue reading from the std input; caller should close stream
	 * @throws IOException
	 */
	private void handleAddStudentsCmd(String userInput, BufferedReader stdIn) throws IOException {
		System.out.println("Adding new students to the class.\n"
				+ "To add a student to the class, type a student number and press enter.\n"
				+ "Ensure that the student number is not larger than " + Integer.MAX_VALUE + ".\n"
				+ "When you are finished adding student numbers, enter \"DONE\".");
		
		int studentNumber;
		while ((userInput = stdIn.readLine()) != null) {
			
			if (userInput.equals(DONE_CMD)) {
				System.out.println("Finished adding students. ");
				break;
			} else {
				try {
					studentNumber = Integer.parseInt(userInput);
					ClassManager.getInstance().addStudent(studentNumber);
				} catch (NumberFormatException e) {
					System.err.println("ERROR: Invalid student number. ");
				}			
			}
		}

	}
	
	/**
	 * Handle shutdown gracefully
	 */
	private void shutdown() {
		System.out.println("\nShutting down...");
		try {
			if (_serverSocket != null) {
				_serverSocket.close();
			}
		} catch (IOException e) {
			System.err.println("ERROR: Could not close the server port. ");
		}
	}
	
	/**
	 * Handle force shutdown
	 */
	protected class ShutdownHandler extends Thread {
	
		protected ShutdownHandler() {
			super("Shutdown handler thread.");
		}
		
		@Override
		public void run() {
			shutdown();
		}
	}
}
