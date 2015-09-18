import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.SwingWorker;

public class BgFestival extends SwingWorker<Void, Void> {
		
		protected int festID;
		
		private String input;
		
		public BgFestival(String input) {
			this.input = input;
		}

		@Override
		protected Void doInBackground() throws Exception {
		
			try {
				//festival to speak out commentary by the user
				String cmd = "echo \""+input+"\" | festival --tts & echo $!";
				//echo $! from the cmd above returns the process ID of the process that has just been executed
				
				ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", cmd);
				Process process = builder.start();
				
				//read the output of the cmd to get the process ID for killing if necessary
				InputStream stout = process.getInputStream();
				BufferedReader stoutbr = new BufferedReader(new InputStreamReader(stout));
				String line = null;
				while((line=stoutbr.readLine()) != null) {
					festID = Integer.parseInt(line); //store ID for cancelling later
				}
			} catch(Exception ex) {
				ex.printStackTrace();
			}

			return null;
		}
	
	}

