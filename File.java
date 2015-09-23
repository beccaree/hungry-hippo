import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * @author Isabel Zhuang and Rebecca Lee
 * Class contains necessary methods for completing actions in MainFrame
 */
public class File {

	/**
	 * Checks if the chosen file path from JFileChooser is a video (avi/mpeg4)
	 * @param path
	 * @return
	 */
	protected static boolean isVideo(String path) {
		
		String cmd = "file "+ path;
		
		// Determines if the file path chosen is a video file
		ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", cmd);
		Process process;
		try {
			process = processBuilder.start();
			InputStream output = process.getInputStream();
			BufferedReader stdout = new BufferedReader(new InputStreamReader(output));

			String line = null;
			while ((line = stdout.readLine()) != null) {
				if (line.matches("(.*)video: FFMpeg MPEG-4(.*)")){ // Matches video format
					return true;
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}	
		
		return false;
	}
	
	/**
	 * Checks if the chosen file path from JFileChooser is an mp3/audio file
	 * @param path
	 * @return
	 */
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
				if (line.matches("(.*): Audio file(.*)")){ // Matches audio format
					return true;
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		return false;		
	}
	
	/**
	 * Uses BASH commands to generate an output video merged with the selected mp3 file 
	 * @param mp3Path
	 * @param videoPath
	 */
	protected static void mergeMp3(String mp3Path, String videoPath) {
		try {
			// Creates an mp3 file from the existing video's audio
			String cmd = "ffmpeg -i " + videoPath + " -map 0:1 ./MP3Files/.vidAudio.mp3";
			startProcess(cmd);
			
			// Deletes any existing output.mp3 file
			cmd = "rm -r ./MP3Files/.output.mp3";
			startProcess(cmd);
			
			Thread.sleep(200); // Stops thread from continuing(sleep) so previous command can finish executing
			
			// Creates an mp3 file output.mp3 that combines the video audio and selected mp3 audio for merging 
			cmd = "ffmpeg -i " + mp3Path + " -i ./MP3Files/.vidAudio.mp3 -filter_complex amix=inputs=2 ./MP3Files/.output.mp3";
			startProcess(cmd);
			
			// Deletes any existing output.avi file
			cmd = "rm -r ./VideoFiles/output.avi";
			startProcess(cmd);
			
			Thread.sleep(200); // Stops thread from continuing(sleep) so previous command can finish executing
			
			// Creates an output.avi file from merging combined audio (0:0) and existing video stream (1:0)
			cmd = "ffmpeg -i ./MP3Files/.output.mp3 -i " + videoPath + " -map 0:0 -map 1:0 -acodec copy -vcodec copy ./VideoFiles/output.avi";
			startProcess(cmd);
			
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}					
	}
	
	
	/**
	 * Starts building a process for any BASH command passed in
	 * @param cmd
	 * @throws IOException
	 */
	private static void startProcess(String cmd) throws IOException{
		ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", cmd);
		builder.start();
	}
	
	/**
	 * Gets the basename of a file path and returns it as a string
	 * @param path
	 * @return
	 */
	protected static String getBasename(String path){
		int index = path.lastIndexOf('/');
		return path.substring(index+1); // Get string after index+1 (basename)		
	}

	/**
	 * Sets the videoPath of current video
	 * @param newPath
	 */
	public static void setCurrentVideoPath(String newPath) {
		MainFrame.currentVideoPath = newPath;
	}
	
	/**
	 * Returns the videoPath of current video
	 * @return
	 */
	public static String getCurrentVideoPath() {
		return MainFrame.currentVideoPath;
	}
}

