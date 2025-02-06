package project.centralServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServiceComms {
	private final Socket socket;
	private final BufferedReader in;
	private final PrintWriter out;
	
	public ServiceComms(Socket socket) throws IOException {
		this.socket = socket;
		
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
	}
	
	public void sendMsg(String msg)
	{
		out.println(msg);
	}
	public String receiveMsg() throws IOException
	{
		return in.readLine();
	}
	
	public void close()
	{
		try
		{
			in.close();
			out.close();
			socket.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
