package project.centralServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import CommonServerClient.ServiceCommunications;

public class Server {
	static public final String IP_ADDRESS = "localhost";
	static public final int RECEIVING_PORT = 5555;
	public Server() {
		// TODO Auto-generated constructor stub
	}
	
	public void run()
	{
		ServerSocket serverSocket = null;
		
		try {
			serverSocket = new ServerSocket(RECEIVING_PORT);
			
			while(true)
			{
				System.out.println("Waiting for new client");
				Socket clientSocket = serverSocket.accept();
				System.out.println("Client accepted");
				
				ServerService service = new ServerService(clientSocket);
				
				Thread t = new RequestHandler(service);
				t.start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		(new Server()).run();
	}
}
