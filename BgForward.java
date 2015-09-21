import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;
import javax.swing.Timer;

import uk.co.caprica.vlcj.player.MediaPlayer;


public class BgForward extends SwingWorker<Void, Void> {
	
	private int interval;
	private MediaPlayer video;

	public BgForward(int interval, MediaPlayer video) {
		this.interval = interval;
		this.video = video;
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		
		Timer timer = new Timer(100, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!MainFrame.playClicked) {
					video.skip(interval);
				} else {
					((Timer)e.getSource()).stop();
				}
			}
		});
		timer.start();
		
		return null;
	}
}
