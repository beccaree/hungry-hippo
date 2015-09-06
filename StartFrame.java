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
import java.awt.FlowLayout;

import javax.swing.JTextField;



//import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
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
				    FileNameExtensionFilter filter = new FileNameExtensionFilter("MP3 File", "mp3");
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
		
		JCheckBox chckbxDefaultVid = new JCheckBox("Use Default Video"); //getState() returns boolean, true if on and false if off
		panel_2.add(chckbxDefaultVid);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4, BorderLayout.EAST);
		
		JButton btnNewButton = new JButton("Ok");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		//panel.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{panel_1, panel_2}));
	}

}
