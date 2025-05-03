package project.client;

import java.io.IOException;
import java.net.Socket;

import project.commonServerClient.ServiceCommunications;

public class ClientService extends ServiceCommunications {

	public ClientService(Socket socket) throws IOException {
		super(socket);
	}

	public void startSimulation(SimulationType type, long simTime, String componentsPath, String connectionsPath)
			throws IOException {
		sendSimulationType(type);
		sendExpectedTime(simTime);
		sendNetlistInfo(componentsPath, connectionsPath);
	}

	public void receiveNetListResult(String componentsResultPath) throws IOException {
		receiveFile(componentsResultPath);
	}

	private void sendSimulationType(SimulationType type) throws IOException {
		sendLn(type.toString());
	}

	private void sendExpectedTime(long simTime) throws IOException {
		sendLn(Long.toString(simTime));
	}

	private void sendNetlistInfo(String componentsPath, String connectionsPath) throws IOException {
		sendComponents(componentsPath);
		sendConnections(connectionsPath);
	}

	private void sendComponents(String componentsPath) throws IOException {
		sendFile(componentsPath);
	}

	private void sendConnections(String connectionsPath) throws IOException {
		sendFile(connectionsPath);
	}
}