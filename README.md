#java-clicker-quiz

## Description
This project is an example demonstrating the Client-Server model utilizing sockets.

The program has a server side that listens for clients; once a client connects and is authenticated, it is provided with some options to answer the question, choosing an option sends a response back to the server.

## Design
The overall design of the program was split up into two components, one for the server and one for the client. These two components interact with each other using a protocol. The UML diagram in the figure below shows how the main components of the software interact.

![Design](https://github.com/farhan3/java-clicker-quiz/blob/master/JClickerQuiz.png)

## Usage
The [server jar file](server) and [client jar file](client) can be found in their respective directories. 

### Server
The server side has a main class named Server; it handles the processing and error checking of the input parameters. This class must be provided the port number on which the server should be run. Additionally, a file can be provided to it that contains the list of students in the class. It then passes this information to an instance of the ServerControl class.

The ServerControl class is responsible for handling the input commands from the user; in this case it would normally be a teacher who is running the server. It is important to note that this is just the user interface for the teacher; it does not start listening to clients yet. The commands handled by the ServerControl class are:

```
HELP: 			    View the available commands.
EXIT: 				Stop the server and exit the program.
ADD_STUDENTS: 		Add students to the class.
START_QUESTION(n): 	Start a question with n choices and waits for students to connect and respond.
END_QUESTION(): 	End the question; students can no longer send responses.
LIST: 				Lists students who have sent answers.
```

Once a question is started using the START_QUESTION(n) command, a ClientListener is created in a concurrent thread in the background. This new thread then starts listening for clients on the specified port. At the same time, the main thread is still running and the teacher can continue to enter commands, such as LIST. When the question needs to be ended, the teacher can enter the END_QUESTION() command.  

When a client tries to connect to the server on the specified port, a new instance of a ClientHandler is created, which is run as a concurrent thread. Therefore, multiple clients can be served at once. The ClientHandler deals with the communication between the server and the client by utilizing the Protocol class. 

### Client
The client side is much simpler than the server. Again, there is a main class named Client that handles the creation of the client. In this case however, the main method does not need to deal with any parameters since the information required from the client is provided through the available command.

The Client class creates a new instance of the ClientControl class. Similar to the ServerControl class, the ClientControl class is responsible for handling input commands from the user, who in this case is the student. The commands handled by the ClientControl class are:  

```
HELP:           View the available commands.
EXIT:           Stop the client and exit the program.
STUDENT_NUMBER: Allows the student to enter their student number.
CLASS_INFO:     Allows the student to enter the host name/IP address and port number of the instructor's computer.
ENTER_CHOICE(): Start a client process, connect to server (using the information above) and send the student number, receive the number of choices, prompt the student user for his/her choice and send this choice to the server and close connection.
```

It is important to note that the client only connects to the server once the ENTER_CHOICE() command is received. Of course the student number and class info must also be provided. 

## Development
This is an Eclipse project and can therefore be imported into Eclipse for further development work.

A [GUI version](src/com/github/farhan3/jclickerquiz/gui) using SWT is in development, however it is currently not working.

## End Notes
This project is published under the Apache License Version 2.0.

