import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;

import javax.swing.JTextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;


public class StartFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtVideoPath;
	private String videoPath;
	boolean isVideo = false;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartFrame frame = new StartFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public StartFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 150, 500, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		final JFrame thisFrame = this;
		
		JLabel lblChooseYourVideo = new JLabel("Choose Your Video");
		lblChooseYourVideo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblChooseYourVideo.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblChooseYourVideo, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		
		txtVideoPath = new JTextField();
		txtVideoPath.setText("/HOME/Documents/Videos/...");
		panel_1.add(txtVideoPath);
		txtVideoPath.setColumns(30);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 
				JFileChooser videoChooser = new JFileChooser();
				    FileNameExtensionFilter filter = new FileNameExtensionFilter("Video File", "avi");
				    videoChooser.setFileFilter(filter);
				    int okReturnVal = videoChooser.showOpenDialog(getParent());
				    if(okReturnVal == JFileChooser.APPROVE_OPTION) {
				    	videoPath = videoChooser.getSelectedFile().getPath();
				    	txtVideoPath.setText(videoPath);
				    }
			}
		});
		panel_1.add(btnBrowse);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		
		final JCheckBox chckbxDefaultVid = new JCheckBox("Use Bunny Video"); //isSelected() returns boolean, true if on and false if off
		panel_2.add(chckbxDefaultVid);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4, BorderLayout.EAST);
		
		JButton btnNewButton = new JButton("Ok");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
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

				if(chckbxDefaultVid.isSelected()) { //if the user chooses to use the bunny video
					thisFrame.dispose();
					new MainFrame("bunny.avi");
				} else if(isVideo){ //if the user has chosen a video for themselves
					thisFrame.dispose();
					new MainFrame(videoPath);	
				} else { //if the user has not chosen a video
					//Navigate to an error dialog
					System.out.println("goes to error dialog");
				}			

			}
		});
		panel_4.add(btnNewButton);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisFrame.dispose();
			}
		});
		panel_4.add(btnCancel);
	}

}
