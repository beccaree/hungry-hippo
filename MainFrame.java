import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;

import java.awt.Color;
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

import javax.swing.SwingConstants;

//import uk.co.caprica.vlcj.player.MediaPlayer; //getTime(), skip(), mute(), pause(), play()
//import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent; 

public class MainFrame extends JFrame {
	
	private int festID; //for saving the ID of festival for killing later
	private String videoPath;
	boolean isVideo = false;

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setTitle("VIDIVOX by twerking-hippo :)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 50, 1000, 650);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);		
		
		JMenuItem mntmOpenNewVideo = new JMenuItem("Open New Video...");
		mntmOpenNewVideo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//changing the video that we are editing
				//Open file chooser
				JFileChooser videoChooser = new JFileChooser();
			    FileNameExtensionFilter filter = new FileNameExtensionFilter("AVI File", "avi");
			    videoChooser.setFileFilter(filter);
			    int okReturnVal = videoChooser.showOpenDialog(getParent());
			    if(okReturnVal == JFileChooser.APPROVE_OPTION) {
			    	videoPath = videoChooser.getSelectedFile().getPath();
			    	
			    	String cmd = "file "+ videoPath;
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
								isVideo = true;
							}
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					

					if(!isVideo){
						//Navigate to an error dialog on top of MainFrame
						JDialog errorDialog = new StartErrorDialog();
						errorDialog.setVisible(true);
						System.out.println("goes to error dialog");
					}else{
				    	//TO DO:Change name of current video editing if it is a video
					}
			    }
			}
		});
		mnFile.add(mntmOpenNewVideo);
		
		JPanel screen_play = new JPanel();
		screen_play.setLayout(new BorderLayout(0, 0));
		
		JPanel screen = new JPanel();
		screen.setBackground(Color.BLACK);
		//EmbeddedMediaPlayerComponent screen = new EmbeddedMediaPlayerComponent();
		//screen.getMediaPlayer().playMedia("bunny.avi");
		screen_play.add(screen, BorderLayout.CENTER);
		
		JPanel controls = new JPanel();
		screen_play.add(controls, BorderLayout.SOUTH);
		controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
		
		JPanel video_control = new JPanel();
		controls.add(video_control);
		video_control.setLayout(new BoxLayout(video_control, BoxLayout.X_AXIS));
		
		JButton btnReverse = new JButton("l <<");
		btnReverse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//should continue rewinding until user clicks play
			}
		});
		video_control.add(btnReverse);
		
		JButton btnPlayPause = new JButton("> / l l");
		btnPlayPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//play or pause video
			}
		});
		video_control.add(btnPlayPause);
		
		JButton btnForward = new JButton(">> l");
		btnForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//should continue forwarding until user clicks play
			}
		});
		video_control.add(btnForward);
		
		JPanel volume_control = new JPanel();
		controls.add(volume_control);
		volume_control.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		volume_control.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblSound = new JLabel("Sound");
		panel_1.add(lblSound);
		lblSound.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JSlider slider = new JSlider();
		panel_1.add(slider);
		
		JButton btnMute = new JButton("Mute");
		btnMute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//mute the sound when clicked, unmute when clicked again
			}
		});
		panel_1.add(btnMute);
		
		JPanel audio_editing = new JPanel();
		audio_editing.setMinimumSize(new Dimension(300, 500));
		screen_play.setMinimumSize(new Dimension(300, 500));
		
		JSplitPane splitPane = new JSplitPane();
		setContentPane(splitPane);
		splitPane.setLeftComponent(screen_play);
		splitPane.setRightComponent(audio_editing);
		splitPane.setDividerLocation(700 + splitPane.getInsets().left);
		audio_editing.setLayout(new BoxLayout(audio_editing, BoxLayout.Y_AXIS));
		
		JPanel panel_2 = new JPanel();
		audio_editing.add(panel_2);
		
		JLabel lblEnterYourCommentary = new JLabel("Enter your commentary below:");
		lblEnterYourCommentary.setHorizontalAlignment(SwingConstants.LEFT);
		lblEnterYourCommentary.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblEnterYourCommentary.setFont(new Font("Tahoma", Font.PLAIN, 13));
		audio_editing.add(lblEnterYourCommentary);
		
		JTextArea txtrCommentary = new JTextArea();
		txtrCommentary.setText("(max 40 words)");
		txtrCommentary.setLineWrap(true);
		txtrCommentary.setPreferredSize(new Dimension(270, 300));
		audio_editing.add(txtrCommentary);
		
		JPanel audio_options = new JPanel();
		audio_editing.add(audio_options);
		
		final JButton btnSpeak = new JButton("Speak");
		btnSpeak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//use festival to speak out what the user has inputed in text area
				if(btnSpeak.getText().equals("Speak")) {
					btnSpeak.setText("Stop");
					//execute background process of festival
					//return something when completed and change button back to speak
				} else {
					btnSpeak.setText("Speak");
					//kill the festival process
				}
			}
		});
		audio_options.add(btnSpeak);
		
		JButton btnSaveAs = new JButton("Save as MP3");
		btnSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//save input in textarea as .wav file and convert to .mp3 and save
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
			}
		});
		panel.add(btnMerge);
	}

}

