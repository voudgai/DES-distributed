package project.testClient;

import java.io.IOException;
import java.net.Socket;

import project.centralServer.Server;

public class Client extends Thread {
	private final String componentsPath, connectionsPath, componentsResultPath;
	private ClientService service;
	private ClientService.SimulationType simulationType;
	private long simulationTime;

	Client(ClientService.SimulationType simulationType, long simulationTime, String componentsPath,
			String connectionsPath, String componentsResultPath) {
		this.componentsPath = componentsPath;
		this.connectionsPath = connectionsPath;
		this.componentsResultPath = componentsResultPath;
		this.simulationType = simulationType;
		this.simulationTime = simulationTime;
	}

	@Override
	public void run() {
		try {
			Socket socket = new Socket(Server.IP_ADDRESS, Server.RECEIVING_PORT);
			service = new ClientService(socket);
			service.startSimulation(simulationType, simulationTime, componentsPath, connectionsPath);
			service.receiveNetListResult(componentsResultPath);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			service.close();
		}
	}

	static private final long SIMULATION_TIME = 100;

	public static void main(String[] args) {
		Client client = null;
		client = new Client(ClientService.SimulationType.MULTI_THREAD, SIMULATION_TIME, args[0], args[1], args[2]);
		client.start();
	}
}
