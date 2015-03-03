package com.github.farhan3.jclickerquiz.client;

import java.io.IOException;

public class Client {

	public static void main(String[] args) throws IOException {
		if (args.length > 0) {
			System.err.println("Usage: java -jar client.jar");
			System.exit(1);
		}
		
		new ClientControl().run();
	}

}
