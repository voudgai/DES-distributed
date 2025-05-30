package project.server.mainServer;

import java.io.Serializable;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import project.commonServerClient.ServiceCommunications;
import project.server.Job;
import rs.ac.bg.etf.sleep.simulation.Netlist;
import rs.ac.bg.etf.sleep.simulation.SimComponent;
import rs.ac.bg.etf.sleep.simulation.Simulator;

public class ServerService extends ServiceCommunications {

	private SimulationType simulationType = null;
	private long simulationTime = -1L;
	private Netlist<Serializable> netlist = null;
	private Job job = null;
//	private Simulator<Serializable> simulator = null;

	public ServerService(Socket socket) throws IOException {
		super(socket);
	}

	public Job receiveJob() throws IOException {
		if (job != null)
			return job;

		receiveSimulationType();
		receiveExpectedTime();
		receiveAndGenerateNetlist();

		job = new Job(simulationType, simulationTime, netlist);

		return job;
	}

	public long getSimulationTime() {
		return simulationTime;
	}

	public void jobEnded() throws IOException {
		sendNetList();
	}

	private void receiveSimulationType() throws IOException {
		simulationType = SimulationType.decode(Integer.parseInt(receiveLn()));
	}

	private void receiveExpectedTime() throws NumberFormatException, IOException {
		simulationTime = Long.decode(receiveLn());
	}

	private void receiveAndGenerateNetlist() throws IOException {
		netlist = new Netlist<Serializable>();

		String line;
		while ((line = receiveLn()) != null) {
			String[] names = line.split(" ");
			netlist.addComponent(names);
		}

		List<String[]> cc = new LinkedList<String[]>();
		while ((line = receiveLn()) != null) {
			String[] names = line.split(" ");
			cc.add(names);
		}

		String[][] con = new String[cc.size() - 1][];
		for (int i = 0; i < cc.size() - 1; i++) {
			con[i] = cc.get(i + 1);
		}
		netlist.addConnection(con);
	}

	private void sendNetList() throws IOException {

		for (SimComponent<Serializable> c : netlist.getComponents().values()) {
			String[] context = c.getState();
			String contextString = "";
			for (String s : context) {
				contextString += s + " ";
			}
			contextString = contextString.trim();
			sendLn(contextString);
		}
		sendLn(null);
	}

}
