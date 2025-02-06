package project.testClient;


import java.io.IOException;
import java.net.Socket;

import project.centralServer.ServiceComms;

public class ClientRequestsService extends ServiceComms{

	public ClientRequestsService(Socket socket) throws IOException {
		super(socket);
	}
	public int setI(int a) throws NumberFormatException, IOException
	{
		String msg = String.format("#setI#%d#", a);
		sendMsg(msg);
		
		return Integer.parseInt(receiveMsg());
	}
	public int add(int a, int b) throws NumberFormatException, IOException 
	{ 
		String msg = String.format("#add#%d#%d#", a, b);
		sendMsg(msg);
		
		return Integer.parseInt(receiveMsg());
	}

}