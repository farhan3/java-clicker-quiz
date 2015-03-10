package com.github.farhan3.jclickerquiz.client;

import java.io.IOException;

/**
 * The Client class is the main class to start the client for
 * JClickerQuiz
 * 
 * @author farhan
 *
 */
public class Client {

	public static void main(String[] args) throws IOException {
		if (args.length > 0) {
			System.err.println("Usage: java -jar client.jar");
			System.exit(1);
		}
		
		new ClientControl().run();
	}

}
