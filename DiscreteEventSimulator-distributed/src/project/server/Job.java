package project.server;

import java.io.Serializable;
import java.util.List;

import project.commonServerClient.ServiceCommunications.SimulationType;
import rs.ac.bg.etf.sleep.simulation.Netlist;
import rs.ac.bg.etf.sleep.simulation.Simulator;

public class Job {
	enum Status {READY, SCHEDULED, RUNNING, DONE, FAILED, ABORTED, SPLITTED};
	
	private long rating = 0;
	private Status status = Status.READY;
	private SimulationType simulationType = null;
	private long simulationTime = -1L;
	private Netlist<Serializable> netlist = null;
	private Simulator<Serializable> simulator = null;

	public Job(SimulationType simulationType, long simulationTime, Netlist<Serializable> netlist) {
		this.simulationType = simulationType;
		this.simulationTime = simulationTime;
		this.netlist = netlist;

		generateRating();
	}
	
	public List<Job> split(long rating){
		// TODO split the job into smaller jobs that match rating mentioned, or by some other way
		
		rating = 0;
		simulationType = null;
		simulationTime = -1L;
		netlist = null;
		simulator = null;
		status = Status.SPLITTED;
		
		return null;
	}

	public boolean simulate() {
		if (simulationType == null || simulationTime < 0 || netlist == null)
			return false;
		
		simulator = SimulationType.newSimulator(simulationType);
		
		if (simulator == null)
			return false;

		simulator.setNetlist(netlist);
		simulator.init();

		while (simulator.getlTime() < simulationTime) {
			simulator.execute();
			System.out.println("Time:" + simulator.getlTime());
		}
		return true;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public long getRating() {
		return rating;
	}

	private void generateRating() {
		rating = 1; // TODO make rating generation using Netlist
	}

}
