package VIDIVOX_prototype;
import java.awt.BorderLayout;
import java.awt.Component;
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


/** 
 * @author Isabel Zhuang and Rebecca Lee
 * Class contains implementation for generating a dialog for naming an mp3 or video file.
 */
public class saveAsDialog extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTextField textField;
    protected boolean cancelClicked;
	
    /**
     * Create the dialog.
     */
    public saveAsDialog(final String type, final String commentary) {
    	
    	final JDialog thisDialog = this;
    	
        setBounds(200, 200, 450, 250);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        
        JLabel lblNameYour = new JLabel("Name your MP3 file");
        if(type.equals("video")) {
        	lblNameYour = new JLabel("Name your merged video file");
        }
        lblNameYour.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNameYour.setHorizontalAlignment(SwingConstants.CENTER);
        lblNameYour.setBounds(75, 44, 275, 40);
        contentPanel.add(lblNameYour);
        
        JLabel lblMpName = new JLabel("Name:");
        lblMpName.setBounds(90, 130, 110, 20);
        contentPanel.add(lblMpName);
        
        textField = new JTextField();
        textField.setBounds(170, 130, 150, 20);
        contentPanel.add(textField);
        textField.setColumns(10);     
                
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		// Compares if dialog is used for naming an mp3 file or video
        		if(type.equals("mp3")) {
        			File.saveAsMp3(commentary, textField.getText(), thisDialog); // Will save commentary as mp3
        		} else {
        			MainFrame.videoName = textField.getText(); // Gets the user-entered video name for executing further commands      			
        			thisDialog.dispose();
        		}	
        	}
		});
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		cancelClicked = true;
        		thisDialog.dispose();
        	}
	});
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
    
    }
}

