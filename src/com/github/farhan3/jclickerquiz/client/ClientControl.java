package com.github.farhan3.jclickerquiz.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Collection;

import com.github.farhan3.jclickerquiz.common.Option;
import com.github.farhan3.jclickerquiz.common.Protocol;

public class ClientControl {

	private static final String 	STUDENT_NUMBER_CMD		 	= "STUDENT_NUMBER";
	private static final String 	CLASS_INFO_CMD		 			= "CLASS_INFO";
	private static final String 	ENTER_CHOICE_CMD 				= "ENTER_CHOICE()";
	private static final String 	HELP_CMD 						= "HELP";
	private static final String 	EXIT_CMD 						= "EXIT";
	
	private static final String MAN_PAGE = "Avaliable Commands:\n"
			+ HELP_CMD + ": view the avaliable commands.\n"
			+ EXIT_CMD + ": stop the client and exit the program.\n\n"
			+ STUDENT_NUMBER_CMD + ": Allows the student to enter their student number.\n"
			+ CLASS_INFO_CMD + ": Allows the student to enter the host name/IP address \n"
					+ "and port number of the instructor's computer.\n"
			+ ENTER_CHOICE_CMD + ": Start a client process, connect to server \n"
					+ "(using the information above) and send the student number, \n"
					+ "receive the number of choices, prompt the student user for \n"
					+ "his/her choice and send this choice to the server and close connection. \n";
	
	private int _studentNumber = 0;
	private String _host;
	private int _port = 0;
	
	public ClientControl(){
		
	}
	
	public void run() {
		System.out.println("Starting client...\n");
		
		System.out.println(MAN_PAGE);
		
		System.out.print("> ");
		
      try (
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		) {
			String userInput = null;
		
			while ((userInput = stdIn.readLine()) != null) {
				System.out.println();
				
				if (userInput.startsWith(STUDENT_NUMBER_CMD)) {
					System.out.println("Please enter your student number: ");
					
					try {
						_studentNumber = Integer.parseInt(stdIn.readLine());
						System.out.println("Student number accepted.");
					} catch (NumberFormatException e) {
						System.err.println("ERROR: Invalid student number. ");
					}		

				} else if (userInput.startsWith(CLASS_INFO_CMD)) {
					System.out.println("Please enter the host name/IP address. For example, 192.168.0.1: ");
					_host = stdIn.readLine();
					
					System.out.println("Host accepted.");
					
					System.out.println("Please enter the host's port number: ");
					
					try {
						_port = Integer.parseInt(stdIn.readLine());
						System.out.println("Port accepted.");
					} catch (NumberFormatException e) {
						System.err.println("ERROR: Invalid port number entered. Please use the " + CLASS_INFO_CMD + " again. ");
					}	
					
				} else if (userInput.startsWith(ENTER_CHOICE_CMD)) {
					if (_studentNumber == 0 || _host == null || _port == 0) {
						System.out.println("Please ensure the provided commands to enter the following information: \n"
								+ "1) Student number \n"
								+ "2) Host name/IP address \n"
								+ "3) Host port number");
					} else {
						System.out.println("\nConnecting to " + _host + ":" + _port + " as " + _studentNumber + ". ");
						
						try (
				          Socket socket = new Socket(_host, _port);
				          PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				          BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					   ) {
							
							out.println(Protocol.sendStudentNumber(_studentNumber));
							
							String serverResponse = in.readLine();
							if (!socket.isClosed() && !serverResponse.equals("Goodbye. ")) {
								System.out.println("\nConnected to the server...");
								System.out.println("\n" + Protocol.recieveChoicesString(serverResponse));
								
								Collection<String> recievedChoices = Protocol.recieveChoices(serverResponse);
								
								System.out.print("Respone: ");
								
								while ((userInput = stdIn.readLine()) != null) {
									if (!socket.isClosed()) {		
										if (recievedChoices.contains(userInput)) {
											out.println(Protocol.sendAnswer(Option.valueOf(userInput)));
											System.out.println("\nResponse sent.");
											break;
										} else {
											System.err.println("ERROR: Invalid choice. Try again.");
											System.out.print("\nResponse: ");
										}			
									}
								}
								
							} else if (serverResponse.equals("Goodbye. ")) {
								System.err.println("ERROR: Server disconnected immediately. \n"
										+ "Please ensure you are in the class.");
							} else {
								System.err.println("ERROR: No response from server.");
							}
						} catch (IOException e) {
							System.err.println("ERROR: An error occured while connecting to the server.\n"
									+ "Please ensure the following information is correct:\n"
									+ "Host: " + _host + "\n"
									+ "Port: " + _port + "\n");
						}
					}
				} else if (userInput.equals(HELP_CMD)) {
					System.out.println(MAN_PAGE);
				} else if (userInput.equals(EXIT_CMD)) {
						break;
				} else {
					System.err.println("ERROR: Invalid input. Please use the " + HELP_CMD + " "
							+ "command to view the avalible commands. ");
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
