package project.server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import project.commonServerClient.ServiceCommunications.SimulationType;
import rs.ac.bg.etf.sleep.simulation.Netlist;
import rs.ac.bg.etf.sleep.simulation.Simulator;

public class Job {
	enum Status {
		READY, SCHEDULED, RUNNING, DONE, FAILED, ABORTED, SPLITTED
	};

	private long rating = 0;
	private volatile Status status = Status.READY;
	private SimulationType simulationType = null;
	private long simulationTime = -1L;
	private Netlist<Serializable> netlist = null;
	private Netlist<Serializable> netlistResult = null;
	private Simulator<Serializable> simulator = null;

	public Job(SimulationType simulationType, long simulationTime, Netlist<Serializable> netlist)
			throws IllegalArgumentException {

		if (simulationType == null || simulationTime < 0 || netlist == null)
			throw new IllegalArgumentException();

		this.simulationType = simulationType;
		this.simulationTime = simulationTime;
		this.netlist = netlist;

		generateRating();
	}

	public List<Job> split(int numOfWorkers) {
		// TODO split the job into smaller jobs that match rating mentioned, or by some
		// other way
		List<Job> result = new ArrayList<Job>();
		result.add(this);
		return result;
//		rating = 0;
//		simulationType = null;
//		simulationTime = -1L;
//		netlist = null;
//		simulator = null;
//		status = Status.SPLITTED;
//
//		return null;
	}

	public void simulate() throws IllegalArgumentException {
		if (status == Status.DONE || status == Status.SPLITTED) {
			System.out.println("Job already simulated or splitted into more jobs.");
			return;
		}

		if (simulationType == null || simulationTime < 0 || netlist == null)
			throw new IllegalArgumentException();

		simulator = SimulationType.newSimulator(simulationType);

		if (simulator == null)
			throw new IllegalArgumentException();

		simulator.setNetlist(netlist);
		simulator.init();

		while (simulator.getlTime() < simulationTime) {
			simulator.execute();
			System.out.println("Time:" + simulator.getlTime());
		}
		netlistResult = simulator.getNetlist();
		status = Status.DONE;
	}

	public Netlist<Serializable> getNetlist() {
		return netlistResult;
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
