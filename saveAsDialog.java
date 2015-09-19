import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;


public class saveAsDialog extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTextField textField;
	private String cmd;
	private String textPath;
	private ProcessBuilder builder;
	private Process process;
	
    /**
     * Create the dialog.
     */
    public saveAsDialog(final String commentary) {
    	
    	final JDialog thisDialog = this;
    	
        setBounds(200, 200, 450, 250);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        
        JLabel lblNameYourMp = new JLabel("Name your  MP3 file");
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

								textPath = System.getProperty("user.dir")+ "/commentary.txt";
								BufferedWriter bw = new BufferedWriter(new FileWriter(textPath, false));
								bw.write(commentary);
								bw.close();

								cmd = "text2wave " + textPath + " -o sound.wav";
								builder = new ProcessBuilder("/bin/bash", "-c", cmd);
								process = builder.start();
								System.out.println("soundwas created");
								
								cmd = "ffmpeg -i sound.wav " + textField.getText() + ".mp3";
								Thread.sleep(150);
								builder = new ProcessBuilder("/bin/bash", "-c", cmd);
								process = builder.start();
								
								System.out.println("MP3 created");
								
							} catch (IOException | InterruptedException e1) {
								e1.printStackTrace();
							}
		
							thisDialog.dispose();

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
}
