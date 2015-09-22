import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.SwingWorker;

public class BgFestival extends SwingWorker<Void, Void> {

	protected int festID;
	private ArrayList<Integer> killPID = new ArrayList<Integer>();
	private String input;

	public BgFestival(String input, ArrayList<Integer> killPID) {
		this.input = input;
		this.killPID = killPID;
	}

	@Override
	protected Void doInBackground() throws Exception {

		try {
			// festival to speak out commentary by the user
			String cmd = "echo \"" + input + "\" | festival --tts & echo $!";
			// echo $! from the cmd above returns the process ID of the process
			// that has just been executed

			ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", cmd);
			Process process = builder.start();

			// read the output of the cmd to get the process ID for killing if
			// necessary
			InputStream stdout = process.getInputStream();
			BufferedReader stdoutbr = new BufferedReader(new InputStreamReader(stdout));
			String line = stdoutbr.readLine();
			festID = Integer.parseInt(line); // store ID for cancelling later
			System.out.println(festID);

			System.out.println("Be 0:" + killPID.size());
			killPID.add(festID);
			System.out.println("Be 1:" + killPID.size());

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;
	}

}
