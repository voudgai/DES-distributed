package project.server.mainServer;

import java.io.IOException;
import java.io.Serializable;

import rs.ac.bg.etf.sleep.simulation.Simulator;

public class RequestHandler extends Thread {
	private final ServerService service;
	private Simulator<Serializable> simulator;

	public RequestHandler(ServerService service) {
		this.service = service;
	}

	@Override
	public void run() {
		try {
			simulator = service.receiveSimulator();
			if (simulator == null)
				return;

			simulate(service.getSimulationTime());

			service.simulationEnded();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Simulation ended.");
			service.close();
		}
	}

	private void simulate(long simulationTime) {
		while (simulator.getlTime() < simulationTime) {
			simulator.execute();
			System.out.println("Time:" + simulator.getlTime());
		}
	}
}
