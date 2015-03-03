package com.github.farhan3.jclickerquiz.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.Collection;

import com.github.farhan3.jclickerquiz.model.Question;
import com.github.farhan3.jclickerquiz.model.StudentManager;

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
	
	public ServerControl(int portNumber) {
		_portNumber = portNumber;
	}

	public void run() {
		System.out.println("Starting server...\n");
		
		System.out.println(MAN_PAGE);
		
		try (
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		) {
			String userInput = null;
			
			System.out.print("> ");
			
			while ((userInput = stdIn.readLine()) != null) {
				System.out.println();
				
				if (userInput.startsWith(START_QUESTION_CMD_PREFIX)) {
					System.out.println("Starting question...");
					
					if ((userInput.length() != START_QUESTION_CMD.length()) && 
							userInput.substring(0, userInput.indexOf('(')).equals(START_QUESTION_CMD_PREFIX)) {
						System.err.println("ERROR: Syntax error with " + START_QUESTION_CMD + " command. \n> ");
						continue;
					}
					
					Character inputNumber = userInput.charAt((userInput.indexOf(OPEN_BRACKET) + 1));

					if ( inputNumber < '2' || inputNumber > '5') {
						System.err.println("\nERROR: Syntax error with " + START_QUESTION_CMD + " command.\n"
								+ "The number of choices n must be 2, 3, 4 or 5. \n> ");
						continue;
					}

					int nOfChoices = Integer.parseInt(inputNumber.toString());
					
					if (_questionStarted) {
						System.err.println("ERROR: A question is already running. Use the "
								+ END_QUESTION_CMD + " command to stop it.");
					} else {
						
						if (StudentManager.getInstance().getStudents().isEmpty()) {
							System.err.println("WARNING: There are currently no students in the class. "
									+ "Use the " + ADD_STUDENTS_CMD + " command to add students."
									+ "The question will still be started. ");
						}
						
						_question = new Question(nOfChoices);
						
						try {
							_serverSocket = new ServerSocket(_portNumber);
						} catch (IOException e){
							System.err.println("ERROR: Could not create the server on the specified port.\n"
									+ "Please choose a different port and try again.\n> ");
							break;
						}
						
						_clientListener = new ClientListener(_serverSocket, _question);
						_clientListener.start();
						
						_questionStarted = true;
						
						System.out.println("Started question");
					}
					
				} else if (userInput.equals(END_QUESTION_CMD)) {
					System.out.println("Ending question...");
					
					if (!_questionStarted) {
						System.err.println("ERROR: There is no question currently running. Use the "
								+ START_QUESTION_CMD + " command to start a question.");
					} else {
						_clientListener.stopThread();
						try {
							_serverSocket.close();
							_clientListener.join();
							System.out.println("Ended question.");
						} catch (InterruptedException e) {
							System.err.println("ERROR: Unable to stop the client listener.");
						}
					}
					
				} else if (userInput.equals(LIST_CMD)) {
					if (_question == null) {
						System.err.println("ERROR: There was no question created. Use the "
								+ START_QUESTION_CMD + " command to start a question.");
					} else {
						Collection<Integer> studentNumbers = _question.getListOfStudents();
						
						if (studentNumbers.isEmpty()) {
							System.out.println("No students have answered the question. ");
						} else {
							System.out.println("The following students answered the question: ");
							System.out.println("Student_number \t\t\tResponse");
							for (Integer studentNumber : studentNumbers) {
								System.out.println(studentNumber + "\t" + _question.getStudentsAnswer(studentNumber));
							}
						}
					}
				} else if (userInput.equals(HELP_CMD)) {
					System.out.println(MAN_PAGE);
				} else if (userInput.equals(EXIT_CMD)) {
						break;
				} else if (userInput.equals(ADD_STUDENTS_CMD)) {
					System.out.println("Adding new students to the class.\n"
							+ "To add a student to the class, type a student number and press enter.\n"
							+ "Ensure that the student number is not larger than " + Integer.MAX_VALUE + ".\n"
							+ "When you are finished adding student number, enter \"DONE\".");
					
					int studentNumber;
					while ((userInput = stdIn.readLine()) != null) {
						
						if (userInput.equals(DONE_CMD)) {
							System.out.println("Finished adding students. ");
							break;
						} else {
							try {
								studentNumber = Integer.parseInt(userInput);
								StudentManager.getInstance().addStudent(studentNumber);
							} catch (NumberFormatException e) {
								System.err.println("ERROR: Invalid student number. ");
							}			
						}
					}

				} else {
					System.err.println("ERROR: Invalid input. Please use the help command to view the avalible commands. ");
				}

				System.out.print("\n> ");
			}
			
			shutdown();
		} catch (IOException e) {
			System.err.println("ERROR: Could not read from System Input. ");
		}
	}
	
	public void shutdown() {
		System.out.println("\nShutting down...");
		try {
			if (_serverSocket != null) {
				_serverSocket.close();
			}
		} catch (IOException e) {
			System.err.println("ERROR: Could not close the server port. ");
		}
	}
	
	class ShutdownHandler extends Thread {
	
		protected ShutdownHandler() {
			super("Shutdown handler thread.");
		}
		
		@Override
		public void run() {
			shutdown();
		}
	}
}
