import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JSplitPane;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.SwingConstants;

import uk.co.caprica.vlcj.player.MediaPlayer; //getTime(), skip(), mute(), pause(), play()
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent; 

public class MainFrame extends JFrame {
	
	int festID = 0; //because process ID is very unlikely to be 0
	static boolean playClicked = true;
	static boolean muteClicked = false;
	static boolean stopForward = false;
	private ArrayList<Integer> killPID = new ArrayList<Integer>();
	
	private final EmbeddedMediaPlayerComponent component;
	private final MediaPlayer video;
	private String videoPath;
	
	/**
	 * Create the frame.
	 */
	public MainFrame(String videoPath) {
		this.videoPath = videoPath;
		setTitle("VIDIVOX by twerking-hippo :)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 50, 1000, 650);
		final JFrame thisFrame = this;
		
		//Top menu bar implementation -------------------------------------------------->
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpenNewVideo = new JMenuItem("Open New Video...");
		mntmOpenNewVideo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//changing the video that we are editing
				//prompt user for the video
				String newPath;
				JFileChooser videoChooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Video File", "avi");
				videoChooser.setFileFilter(filter);
				int okReturnVal = videoChooser.showOpenDialog(getParent());
				if(okReturnVal == JFileChooser.APPROVE_OPTION) {
					newPath = videoChooser.getSelectedFile().getPath();
					//check if file chosen is a video, if yes, change video, if not, error dialog and do nothing
					if(File.isVideo(newPath)) {
						video.playMedia(newPath);
					} else {
						JOptionPane.showMessageDialog(thisFrame, "The file you have chosen is not a video, please try again.");
					}
				}
				
			}
		});
		mnFile.add(mntmOpenNewVideo);
		
		
		//Video player implementation -------------------------------------------------->
		JPanel videoPane = new JPanel(); //left side of the split pane
        videoPane.setLayout(new BorderLayout());
        
        //add a media component
        component = new EmbeddedMediaPlayerComponent();
        videoPane.add(component, BorderLayout.CENTER);
        video = component.getMediaPlayer();
		
		JPanel controls = new JPanel();
		videoPane.add(controls, BorderLayout.SOUTH);
		controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
		
		JPanel progress = new JPanel();
		controls.add(progress);
		progress.setLayout(new BoxLayout(progress, BoxLayout.X_AXIS));
		
		final JLabel lblTime = new JLabel("0 secs"); //time label for the GUI
		progress.add(lblTime);
		
		final JProgressBar bar = new JProgressBar(); //progress bar of the GUI
		progress.add(bar);

		JPanel video_control = new JPanel(); //panel for holding all the control buttons (play/pause, rewind, forward)
		controls.add(video_control);
		video_control.setLayout(new BoxLayout(video_control, BoxLayout.X_AXIS));
		
		JButton btnSkipBack = new JButton();
		btnSkipBack.setIcon(new ImageIcon("buttons/skipb.png"));
		JButton btnRewind = new JButton();
		btnRewind.setIcon(new ImageIcon("buttons/rewind.png"));
		final JButton btnPlay = new JButton();
		btnPlay.setIcon(new ImageIcon("buttons/pause.png"));
		JButton btnForward = new JButton();
		btnForward.setIcon(new ImageIcon("buttons/forward.png"));
		JButton btnSkipForward = new JButton();
		btnSkipForward.setIcon(new ImageIcon("buttons/skipf.png"));
		
		btnSkipBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//skips backward 5 seconds every time it is clicked
				video.skip(-5000);
			}
		});
		video_control.add(btnSkipBack);
		
		btnRewind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//should continue rewinding until user clicks play
				playClicked = false;
				btnPlay.setIcon(new ImageIcon("buttons/play.png")); //first set button to play
				BgForward rewind = new BgForward(-500, video); //make a new background task
				rewind.execute();
			}
		});
		video_control.add(btnRewind);
		
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//play or pause video
				if(!playClicked) {
					btnPlay.setIcon(new ImageIcon("buttons/pause.png"));
					video.play(); //play the video
					playClicked = true;
					stopForward = false;
				} else {
					btnPlay.setIcon(new ImageIcon("buttons/play.png"));
					video.pause(); //pause the video
					playClicked = false;
				}
			}
		});
		video_control.add(btnPlay);

		btnForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//should continue forwarding until user clicks play
				playClicked = false;
				BgForward forward = new BgForward(500, video); //make a new background task
				btnPlay.setIcon(new ImageIcon("buttons/play.png")); //first set button to play
				forward.execute();
			}
		});
		video_control.add(btnForward);
		
		btnSkipForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//skips forward 5 seconds every time it is clicked
				video.skip(5000);
			}
		});
		video_control.add(btnSkipForward);
		
		JPanel volume_control = new JPanel(); //panel for holding the volume control buttons (jslider and mute btn)
		controls.add(volume_control);
		volume_control.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel(); //panel used for layout purposes
		volume_control.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblSound = new JLabel("Volume");
		panel_1.add(lblSound);
		lblSound.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JSlider slider = new JSlider(); //slider for volume control
		slider.addChangeListener(new ChangeListener() {
	        @Override
	        public void stateChanged(ChangeEvent e) {
	        	//change the volume of the video to the value value obtained from .getVal
	            video.setVolume(((JSlider) e.getSource()).getValue());
	        }
	    });
		panel_1.add(slider);
		
		final JButton btnMute = new JButton();
		btnMute.setIcon(new ImageIcon("buttons/mute.png"));
		btnMute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//mute the sound when clicked, unmute when clicked again
				if(!muteClicked) {
					btnMute.setIcon(new ImageIcon("buttons/unmute.png"));
					video.mute(); //toggles mute for the video
					muteClicked = true;
				} else {
					btnMute.setIcon(new ImageIcon("buttons/mute.png"));
					video.mute(); //toggles mute for the video
					muteClicked = false;
				}
			}
		});
		panel_1.add(btnMute);
		videoPane.setMinimumSize(new Dimension(300, 500));
		
		
		//Audio editing implementation ---------------------------------------------------->
		JPanel audio_editing = new JPanel(); //the right side of the split pane
		audio_editing.setLayout(new BoxLayout(audio_editing, BoxLayout.Y_AXIS));
		audio_editing.setMinimumSize(new Dimension(300, 500));

		JLabel lblEnterYourCommentary = new JLabel("Commentary here:");
		lblEnterYourCommentary.setHorizontalAlignment(SwingConstants.LEFT);
		lblEnterYourCommentary.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblEnterYourCommentary.setFont(new Font("Tahoma", Font.PLAIN, 15));
		audio_editing.add(lblEnterYourCommentary);
		
		final JTextArea txtrCommentary = new JTextArea();
		txtrCommentary.setText("(max 40 words)");
		txtrCommentary.setLineWrap(true);
		txtrCommentary.setPreferredSize(new Dimension(270, 300));
		audio_editing.add(txtrCommentary);
		
		JPanel audio_options = new JPanel();
		audio_editing.add(audio_options);
		
		JButton btnSpeak = new JButton("Speak");
		btnSpeak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//use festival to speak out what the user has inputed in text area
					
				//execute background process of festival
				String input = txtrCommentary.getText();
				BgFestival bg = new BgFestival(input, killPID);
				bg.execute();
				killPID.removeAll(killPID);
				System.out.println("Be 0:"+killPID.size());
			}
		});
		audio_options.add(btnSpeak);
		
		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//kill the process --------------------------------------------------------
				System.out.println("Be 1:"+killPID.size());
				if (!killPID.isEmpty()) {
					if (killPID.get(0) != 0) {
						festID = killPID.get(0)+4;
						System.out.println(festID);
						String cmd = "kill " + (festID);
						ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", cmd);
						try {
							builder.start();
							killPID.set(0, 0);
							System.out.println(killPID.get(0));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		audio_options.add(btnStop);
		
		JButton btnSaveAs = new JButton("Save as MP3");
		btnSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//save input in textarea as .wav file and convert to .mp3 and save
				String words = txtrCommentary.getText();
	   			StringTokenizer st = new StringTokenizer(words);
	   			st.countTokens();
	   			
	   			if (st.countTokens() <= 40){				
	   				JDialog saveDialog = new saveAsDialog(txtrCommentary.getText());
	   				saveDialog.setVisible(true);
	   			}else{
	   				JOptionPane.showMessageDialog(thisFrame, "Numbers of words in commentary exceeds 40. Please try again.");
	   			}
				
				
			}
		});
		audio_options.add(btnSaveAs);
		
		JPanel panel = new JPanel();
		audio_editing.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnMerge = new JButton("Merge With MP3");
		btnMerge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//merge mp3 with current video 
				//prompt user to choose mp3 file to merge with
				String mp3Path = null;
				
				//select mp3
				JFileChooser mp3Chooser = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter("MP3 File", "mp3");
			    mp3Chooser.setFileFilter(filter);
			    int okReturnVal = mp3Chooser.showOpenDialog(getParent());
			    if(okReturnVal == JFileChooser.APPROVE_OPTION) {
			    	mp3Path = mp3Chooser.getSelectedFile().getPath();
			    	System.out.println(mp3Path);
			    	if(File.isMp3(mp3Path)){
			    		//merge mp3 with current video 
			    		String videoPath = "bunny.avi";
			    		File.mergeMp3(mp3Path, videoPath);
					
			    	} else {
			    		//Navigate to an error dialog
			    		JOptionPane.showMessageDialog(thisFrame, "Please make sure the file you have chosen is an audio (.MP3).");
			    		System.out.println("goes to error dialog");
			    	}
			    }
			}
		});
		panel.add(btnMerge);
		
		
		//Adding the two different panels to the two sided of the split pane ---------------->
		JSplitPane splitPane = new JSplitPane();
		setContentPane(splitPane);
		splitPane.setResizeWeight(0.8); //resizes the frames in a 8:2 ratio
		splitPane.setLeftComponent(videoPane);
		splitPane.setRightComponent(audio_editing);
		splitPane.setDividerLocation(700 + splitPane.getInsets().left);
		
		
		//video manipulation implementation ------------------------------------------------->
		this.setVisible(true); //set the frame to visible before playing the video
		
		video.playMedia(videoPath); //play the video
		video.setVolume(50); //set initial volume to 50 (same as JSlider)
		
		video.addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
		    @Override
		    public void finished(MediaPlayer mediaPlayer) {
		        //button to play when media player finished playing...
		    	playClicked = false;
		    	btnPlay.setIcon(new ImageIcon("buttons/play.png"));
		    	stopForward = true;
		    }
		});
		
		final int[] vidLength = {0}; //initailise as array so final value can be changed
		while(vidLength[0] == 0) {
			vidLength[0] = (int)((video.getLength())/1000);
		}
		
		bar.setMaximum(vidLength[0]);
		
		//timer for updating the label and progress bar every second
		Timer timer = new Timer(500, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblTime.setText((video.getTime())/1000+ " secs"); //update the label
				bar.setValue((int)(video.getTime())/1000); //update the progress bar
				if(video.getLength() == 0) {
					//if video gets to the beginning or video get to the end, stop the rewinding and fast forwarding
					stopForward = true;
				}
			}
		});
		timer.start();
		
		//to fix problem for video being muted when last video exits while muted.
	    addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e)
            {
            	e.getWindow().dispose();
            	//if the video is muted, unmuted before exiting the program
            	if(muteClicked) {
		    		video.mute();
		    	}
            }
        });
	}
}
