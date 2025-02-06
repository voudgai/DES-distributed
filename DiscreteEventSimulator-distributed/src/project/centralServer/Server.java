package project.centralServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public Server() {
		// TODO Auto-generated constructor stub
	}
	
	public void run()
	{
		ServerSocket serverSocket = null;
		
		try {
			serverSocket = new ServerSocket(5555);
			
			while(true)
			{
				System.out.println("Waiting for new client");
				Socket clientSocket = serverSocket.accept();
				System.out.println("Client accepted");
				
				ServiceComms service = new ServiceComms(clientSocket);
				
				Thread t = new RequestHandler(service);
				t.start();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		(new Server()).run();
	}
}
