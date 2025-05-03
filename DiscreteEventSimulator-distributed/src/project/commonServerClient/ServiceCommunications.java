package project.commonServerClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class ServiceCommunications {

	public enum SimulationType {
		MULTI_THREAD(0), OPTIMISTIC(1), UNKNOWN(Integer.MIN_VALUE);
		
		static public SimulationType decode(int val)
		{
			switch (val) {
			case 0:
				return MULTI_THREAD;
			case 1:
				return OPTIMISTIC;
			default:
				return UNKNOWN;
			}
		}

		private int value;

		SimulationType(int val) {
			this.value = val;
		}

		@Override
		public String toString() {
			return Integer.toString(this.value);
		}
	}

	private final Socket socket;
	protected final BufferedReader in;
	protected final PrintWriter out;

	public ServiceCommunications(Socket socket) throws IOException {
		this.socket = socket;

		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
	}

	public String receiveLn() throws IOException {
		String str = in.readLine();
		System.out.println("Receiving " + str);
		if(str.equals("null")) return null;
		return str;
	}

	public void sendLn(String str) throws IOException {
		System.out.println("Sending " + str);
		out.println(str);
	}

	public void close() {
		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void sendFile(String path) throws IOException {
		File file = new File(path);

		try (BufferedReader fileReader = new BufferedReader(new FileReader(file));) {
			String line = null;
			while ((line = fileReader.readLine()) != null) {
				sendLn(line);
			}
			sendLn(null); // marks the end of the file
		}
	}
	
	protected void receiveFile(String path) throws IOException {
		File file = new File(path);

		try (PrintWriter fileWriter = new PrintWriter(file);) {
			String line = null;
			while ((line = receiveLn()) != null) {
				fileWriter.println(line);
			}
		}
	}
}
