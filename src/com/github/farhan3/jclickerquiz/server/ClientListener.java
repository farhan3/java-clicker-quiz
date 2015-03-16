package com.github.farhan3.jclickerquiz.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.LinkedList;

import com.github.farhan3.jclickerquiz.model.Question;

/**
 * This class handles listening to connecting clients
 * and deals with them accordingly.
 * 
 * @author farhan
 *
 */
public class ClientListener extends Thread {
	
	private boolean _running = false;
	
	private ServerSocket _serverSocket;
	private Question _question;
	private Collection<ClientHandler> _connectedClients = new LinkedList<ClientHandler>();
	
	/**
	 * 
	 * @param serverSocket the server socket to listen on
	 * @param question the question object associated with the currently running question
	 */
	protected ClientListener(ServerSocket serverSocket, Question question) {
		super("Client listener thread.");
		_serverSocket = serverSocket;
		_question = question;
	}
	
	@Override
	public void run() {
		_running = true;
		Socket clientSocket = null;
			
		// keep the listener running until thread is stopped or server socket is closed
		while(_running && !_serverSocket.isClosed()) {	
			try {
				// listen for new clients
				clientSocket = _serverSocket.accept();
			} catch (IOException e) {
				System.out.println("Client listener: Responding to server being shutdown...");
				System.out.println("Client listener: Listener stopped.");
				return;
			}
			
			// new client connected, create a new thread for handling communication with it
			// so we don't hold up the listener thread; this allows concurrency
			if (clientSocket != null) {
				ClientHandler client = new ClientHandler(clientSocket, _question);
				client.start();
				_connectedClients.add(client);
				
			}
		}
	}
	
	/**
	 * signal the thread to stop
	 */
	protected void stopThread() {
		_running = false;
		
		for (ClientHandler client : _connectedClients) {
			if (client != null && client.isAlive()) {
				client.stopThread();
				try {
					client.join();
				} catch (InterruptedException e) {
					System.out.println("Client listener: Error while stopping client.");
				}
			}
		}
	}
}
