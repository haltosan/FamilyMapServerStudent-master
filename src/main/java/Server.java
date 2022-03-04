import java.io.*;
import java.net.*;

import DataAccess.Database;
import Handler.*;
import com.sun.net.httpserver.*;


public class Server {

	private static final int MAX_WAITING_CONNECTIONS = 12;

	private final Database db;

	public Server(){
		db = new Database();
	}

	/**
	 * This method initializes and runs the server.
	 * The "portNumber" parameter specifies the port number on which the
	 * server should accept incoming client connections.
	 *
	 * @param portNumber String for port number to run server on
	 */
	private void run(String portNumber) {

		System.out.println("Initializing HTTP Server");
		HttpServer server;
		try {
			/* Create a new HttpServer object.
			// Rather than calling "new" directly, we instead create
			// the object by calling the HttpServer.create static factory method.
			// Just like "new", this method returns a reference to the new object. */
			server = HttpServer.create(
						new InetSocketAddress(Integer.parseInt(portNumber)),
						MAX_WAITING_CONNECTIONS);
		}
		catch (IOException e) {
			e.printStackTrace();
			return;
		}

		/* Indicate that we are using the default "executor".
		// This line is necessary, but its function is unimportant for our purposes. */
		server.setExecutor(null);

		/* The HttpServer class listens for incoming HTTP requests.  When one
		// is received, it looks at the URL path inside the HTTP request, and
		// forwards the request to the handler for that URL path. */
		System.out.println("Creating contexts");
		server.createContext("/user/register", new RegisterHandler(db));
		server.createContext("/user/login", new LoginHandler(db));
		server.createContext("/clear", new ClearHandler(db));
		server.createContext("/fill/", new FillHandler(db)); //[username]/{generations}
		server.createContext("/load", new LoadHandler());
		server.createContext("/person/", new PersonHandler(db)); //[personID]
		server.createContext("/person", new AllPersonHandler(db));
		server.createContext("/event/", new EventHandler(db)); //[eventID]
		server.createContext("/event", new AllEventHandler(db));
		server.createContext("/", new FileHandler());

		/* Tells the HttpServer to start accepting incoming client connections.
		// This method call will return immediately, and the "main" method
		// for the program will also complete.
		// Even though the "main" method has completed, the program will continue
		// running because the HttpServer object we created is still running
		// in the background. */
		System.out.println("Starting server");
		server.start();
		
		System.out.println("Server started");
	}

	/* "main" method for the server program
	// "args" should contain one command-line argument, which is the port number
	// on which the server should accept incoming client connections. */
	public static void main(String[] args) {
		String portNumber = args[0];
		new Server().run(portNumber);
	}
}

