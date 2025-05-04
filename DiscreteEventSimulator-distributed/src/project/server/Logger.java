package project.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public abstract class Logger {

	static private final String LOG_FILE_PATH = "/DiscreteEventSimulator-distributed/src/project/server/LOG_INFO";
	static private PrintWriter LOG = null;

	static public void log(String info) {
		if (LOG == null) {
			try {
				LOG = new PrintWriter(new File(LOG_FILE_PATH));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		LOG.println(info);
	}
}
