import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
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


public class MainFrame extends JFrame {

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
			}
		});
		mnFile.add(mntmOpenNewVideo);
		
		JPanel screen_play = new JPanel();
		//contentPane.add(screen_play);
		screen_play.setLayout(new BorderLayout(0, 0));
		
		JPanel screen = new JPanel();
		screen.setBackground(Color.BLACK);
		screen_play.add(screen, BorderLayout.CENTER);
		
		JPanel controls = new JPanel();
		screen_play.add(controls, BorderLayout.SOUTH);
		controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
		
		JPanel video_control = new JPanel();
		controls.add(video_control);
		video_control.setLayout(new BoxLayout(video_control, BoxLayout.X_AXIS));
		
		JButton btnI = new JButton("l <<");
		video_control.add(btnI);
		
		JButton btnll = new JButton("> / l l");
		video_control.add(btnll);
		
		JButton btnNewButton = new JButton(">> l");
		video_control.add(btnNewButton);
		
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
		panel_1.add(btnMute);
		
		JPanel audio_editing = new JPanel();
		
		JSplitPane splitPane = new JSplitPane();
		setContentPane(splitPane);
		splitPane.setLeftComponent(screen_play);
		splitPane.setRightComponent(audio_editing);
		splitPane.setDividerLocation(700 + splitPane.getInsets().left);
		audio_editing.setLayout(new BoxLayout(audio_editing, BoxLayout.Y_AXIS));
		
		JTextArea txtrCommentary = new JTextArea();
		txtrCommentary.setText("Type your commentary here...");
		txtrCommentary.setPreferredSize(new Dimension(300, 550));
		audio_editing.add(txtrCommentary);
		
		JPanel audio_options = new JPanel();
		audio_editing.add(audio_options);
		
		JButton btnSpeak = new JButton("Speak");
		audio_options.add(btnSpeak);
		
		JButton btnSaveAs = new JButton("Save as MP3");
		audio_options.add(btnSaveAs);
		
		JPanel panel = new JPanel();
		audio_editing.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnNewButton_1 = new JButton("Merge With MP3");
		panel.add(btnNewButton_1);
	}

}
