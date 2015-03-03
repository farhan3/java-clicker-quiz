package com.github.farhan3.jclickerquiz.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.github.farhan3.jclickerquiz.model.Question;

public class ClientListener extends Thread {
	
	private boolean _running = false;
	
	private ServerSocket _serverSocket;
	private Question _question;
	
	public ClientListener(ServerSocket serverSocket, Question question) {
		super("Client listener thread.");
		_serverSocket = serverSocket;
		_question = question;
	}
	
	@Override
	public void run() {
		_running = true;
		Socket clientSocket = null;
			while(_running && !_serverSocket.isClosed()) {
				
				try {
					clientSocket = _serverSocket.accept();
				} catch (IOException e) {
					System.out.println("Client listener: Responding to server being shutdown...");
					System.out.println("Client listener: Listener stopped.");
					return;
				}
				
				new ClientHandler(clientSocket, _question).start();
			}
	}
	
	public void stopThread() {
		_running = false;
	}
}
