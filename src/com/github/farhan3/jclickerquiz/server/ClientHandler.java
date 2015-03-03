package com.github.farhan3.jclickerquiz.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.github.farhan3.jclickerquiz.common.Option;
import com.github.farhan3.jclickerquiz.common.Protocol;
import com.github.farhan3.jclickerquiz.model.Question;
import com.github.farhan3.jclickerquiz.model.StudentManager;

public class ClientHandler extends Thread {

	private Socket _socket;
	private Question _question;
	
	public ClientHandler(Socket socket, Question question) {
		super("Client handler thread.");
		_socket = socket;
		_question = question;
	}
	
	@Override
	public void run() {
		try (
				PrintWriter out = new PrintWriter(_socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
		) {
			String inputLine = null;
			int studentNumber = 0;
			
			while ((inputLine = in.readLine()) != null) {
				
				if (Protocol.isStudentNumberMessage(inputLine)) {
					if (!StudentManager.getInstance().checkStudentInClass(Protocol.recieveStudentNumber(inputLine))) {
						break;
					} else {
						studentNumber = Protocol.recieveStudentNumber(inputLine);
						out.println(Protocol.sendChoices(_question.getNumberOfChoices()));
					}
				} else if (Protocol.isAnswerMessage(inputLine)) {
					Option answer = Protocol.recieveAnswer(inputLine);
					
					if (studentNumber != 0) {
						_question.logAnswer(studentNumber, answer);
						break;
					}
				}
			}
			
			out.println("Goodbye. ");
			_socket.close();
		} catch (IOException e) {
			System.err.println("ERROR: An IOException has occured in the Client Handler. ");
			e.printStackTrace();
		}
		
	}

}
