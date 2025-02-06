package project.centralServer;

import java.io.IOException;

public class RequestHandler extends Thread{
	private final ServiceComms service;
//	private final ServerService serverService;

	public RequestHandler(ServiceComms service) {
			this.service = service;
//			this.serverService = new ServerService();
		}

	public void run() {
		while (true) {
			String msg;
			try {
				msg = service.receiveMsg();

				if (msg == null) {
					break;
				}
				service.sendMsg(parseAndRespond(msg));

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		service.close();
	}

	private String parseAndRespond(String msg) {
		// #setI#a#
		// #add#a#b#q
//		String[] data = msg.split("#");

		int ret = 5;
//		if ("setI".equals(data[1])) {
//			ret = serverService.setI(Integer.parseInt(data[2]));
//		} 
		return Integer.toString(ret);

	}
}
