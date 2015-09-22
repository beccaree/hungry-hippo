import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class File {
	//class containing file checking methods

	protected static boolean isVideo(String path) {
		//method for checking if file is a video, to prevent everything being on the same file
		
		String cmd = "file "+ path;
		
		//Determine if file chosen is a video file
		ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", cmd);
		Process process;
		try {
			process = processBuilder.start();
			InputStream output = process.getInputStream();
			BufferedReader stdout = new BufferedReader(new InputStreamReader(output));

			String line = null;
			while ((line = stdout.readLine()) != null) {
				if (line.matches("(.*)video: FFMpeg MPEG-4(.*)")){
					return true;
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}	
		
		return false;
	}
	
	protected static boolean isMp3(String path) {
		
		//Determine whether it is an mp3 file
	    String cmd = "file "+ path;
		
		//Determine if file chosen is a video file
		ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", cmd);
		Process process;
		try {
			process = processBuilder.start();
			InputStream output = process.getInputStream();
			BufferedReader stdout = new BufferedReader(new InputStreamReader(output));

			String line = null;
			while ((line = stdout.readLine()) != null) {
				if (line.matches("(.*): Audio file(.*)")){
					return true;
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return false;
		
	}
}
