package project.server.mainServer;

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import project.server.Job;
import project.server.Scheduler;
import rs.ac.bg.etf.sleep.simulation.Netlist;

public class Server {
	static public final String IP_ADDRESS = "localhost";
	static public final int RECEIVING_PORT = 5555;

	private final Scheduler scheduler = new Scheduler();
	private List<Integer> threadsPerWorkerStation = new ArrayList<>();

	public Server() {
	}

	public synchronized int signInAsWorkerStation(int threads) {
		// TODO possibly add some hashmap that maps particular thread to its number of
		// threads
		threadsPerWorkerStation.add(threads);

		return threadsPerWorkerStation.size() - 1; // returns ID of worker
	}

	public Netlist<Serializable> addJobsAndGetResults(List<Job> jobsList) {
		scheduler.addJobs(jobsList);
		Netlist<Serializable> results = new Netlist<>();
		for(Job job : jobsList) {
			job.waitToFinish();
			results.a
		}
	}

	public synchronized void signOutAsWorkerStation(int id) {
		threadsPerWorkerStation.remove(id);
	}

	public synchronized int getNumOfWorkerStations() {
		return threadsPerWorkerStation.size();
	}

	public synchronized int getNumOfWorkerThreads() {
		int threads = 0;
		for (int thr : threadsPerWorkerStation)
			threads += thr;

		return threads;
	}

	public void run() {
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(RECEIVING_PORT);

			while (true) {
				System.out.println("Waiting for new client");
				Socket clientSocket = serverSocket.accept();
				System.out.println("Client accepted");

				ServerService service = new ServerService(clientSocket);

				Thread t = new RequestHandler(service, this);
				t.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		(new Server()).run();
	}
}
