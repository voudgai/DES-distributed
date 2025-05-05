package project.server.mainServer;

import java.io.IOException;
import java.util.List;

import project.server.Job;

public class RequestHandler extends Thread {
	private final ServerService service;
	private final Server server;

	public RequestHandler(ServerService service, Server server) {
		this.service = service;
		this.server = server;
	}

	@Override
	public void run() {

		try {
			Job job = service.receiveJob();
			List<Job> list = job.split(server.getNumOfWorkerStations());
			server.addJobsAndGetResults(list);
			
			// wait for job(s) to get done, every job returns netlist after completetion, combine netlist and return it to client
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

//run{		
//	try {
//		simulator = service.receiveSimulator();
//		if (simulator == null)
//			return;
//
//		simulate(service.getSimulationTime());
//
//		service.simulationEnded();
//	} catch (IOException e) {
//		e.printStackTrace();
//	} finally {
//		System.out.println("Simulation ended.");
//		service.close();
//	}
//}