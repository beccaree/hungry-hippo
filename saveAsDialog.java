import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class saveAsDialog extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTextField textField;
	private String cmd;
	private String textPath;
	private ProcessBuilder builder;
	private Process process;
	private String dir;
	
    /**
     * Create the dialog.
     */
    public saveAsDialog(final String commentary) {
    	
    	final JDialog thisDialog = this;
    	dir = System.getProperty("user.dir") + "/MP3Files/";
    	new File(dir).mkdir();
    	
        setBounds(200, 200, 450, 250);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        
        JLabel lblNameYourMp = new JLabel("Name your MP3 file");
        lblNameYourMp.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNameYourMp.setHorizontalAlignment(SwingConstants.CENTER);
        lblNameYourMp.setBounds(75, 44, 275, 40);
        contentPanel.add(lblNameYourMp);
        
        JLabel lblMpName = new JLabel("MP3 name:");
        lblMpName.setBounds(90, 130, 110, 20);
        contentPanel.add(lblMpName);
        
        textField = new JTextField();
        textField.setBounds(170, 130, 150, 20);
        contentPanel.add(textField);
        textField.setColumns(10);     
                
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("OK");

                okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
				   		if (commentary != null) {
				   		
							try {

								textPath = System.getProperty("user.dir")+ "/.commentary.txt";
								BufferedWriter bw = new BufferedWriter(new FileWriter(textPath, false));
								bw.write(commentary);
								bw.close();

								cmd = "text2wave " + textPath + " -o sound.wav";
								startProcess(cmd);		
								
								cmd = "find | grep -x \"./MP3Files/" + textField.getText() +".mp3\" | wc -l";
								startProcess(cmd);
								
								builder.redirectErrorStream(true);
								InputStream stdout = process.getInputStream();
								BufferedReader stdoutBuffered =	new BufferedReader(new InputStreamReader(stdout));
								String line = stdoutBuffered.readLine();								
								
								if(line.equals("0")){
									cmd = "ffmpeg -i sound.wav " + "\'MP3Files/" + textField.getText() + ".mp3\'";
									Thread.sleep(200);
									startProcess(cmd);
									JOptionPane.showMessageDialog(thisDialog, "Successfully saved "+textField.getText() +".mp3");
								
									thisDialog.dispose();
								
								} else {
									JOptionPane.showMessageDialog(thisDialog, "This name is taken. Please choose another.");
								}
								
							} catch (IOException | InterruptedException e1) {
								e1.printStackTrace();
							}
						}						
					}
                });
                okButton.setActionCommand("OK");
                buttonPane.add(okButton);

            }
            {
                JButton cancelButton = new JButton("Cancel");
                cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						thisDialog.dispose();
					}
				});
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
            }
        }
    } 
        
    public void startProcess(String cmd) throws IOException{
    	builder = new ProcessBuilder("/bin/bash", "-c", cmd);
		process = builder.start();
    }
}
