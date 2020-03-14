package com.nathanormond.gui.panels.generic.mainmenu;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;
import com.nathanormond.obsrv.ActionCommands;
import com.nathanormond.obsrv.observable.ORNames;
import com.nathanormond.obsrv.observable.ObservableFacade;

public class MainMenuPanel extends AbstractPanel implements Observer {
	
	private static final long serialVersionUID = 1L;
	JLabel title, loopbackModeTitle, fpgaProgrammerTitle, tapOnModeTitle;
	JTextArea loopbackModeArea, fpgaProgrammerArea, tapOnModeArea;
	JButton loopbackModeButton, fpgaProgrammerButton, tapOnModeButton;
	private static boolean isNetworkInUse, isCommandModeEnabled, isTapOnEnabled, isDownloadEnabled;
	private String name;
	
	private static String loopBackModeDescription() { 
		return "<html>This mode can be used for Loop Back testing. <br>"
			 + "Allows for sending UDP packets to the FPGA a specified<br>"
			 + "or infinite number of times at specified time intervals. <br>"
			 + "The tool will listen for a response and display it <br>"
			 + "with the option to log the entire output to a file.<br></html>";
	}
	
	private static String tapOnModeDescription() { 
		return "<html>Tap on mode will turn on the FPGA and listen to the <br>"
			 + "incoming UDP traffic at full rate. <br>"
			 + "This includes checking for dropped packets on the wire <br></html>";
	}
	
	private static String fpgaProgrammerDescription() { 
		return "<html>FPGA Programmer allows the user to interrogate the FPGA <br>"
			 + "for builds, erase and replace builds and set a different <br>"
			 + "region as the current build to be executed by the FPGA.<br></html>";
	}

	/**********************
	 * Initialisation
	 */
	
	public MainMenuPanel(ActionListener publicListener, String name) {
		super(publicListener);
		this.name = name;
		this.isNetworkInUse = ObservableFacade.getValue(ORNames.IS_NETWORK_IN_USE);
		ObservableFacade.addObserver(ORNames.IS_NETWORK_IN_USE, this);
		
	}

	@Override
	public void instantiateComponents() {
		
		loopbackModeTitle = new JLabel("COMMAND MODE");
		loopbackModeButton = new JButton("Command");
		
		tapOnModeTitle = new JLabel("ON THE WIRE");
		tapOnModeButton = new JButton("Tap On");
		
		fpgaProgrammerTitle = new JLabel("PROGRAM FPGA");
		fpgaProgrammerButton = new JButton("Program");
		
		this.isTapOnEnabled = true;
		this.isCommandModeEnabled = true;
		this.isDownloadEnabled = true;
		
		styleOutputArea();
	}
	
	private void styleOutputArea(){ 
		
		/**
		 * Title Styles
		 */
		
		Font f = new Font("Arial", Font.BOLD, 20);
		Border border = LineBorder.createGrayLineBorder();
		Dimension d = new Dimension(250, 30);
		
		loopbackModeTitle.setFont(f);					// Loop Back Mode Title
		loopbackModeTitle.setPreferredSize(d);
		loopbackModeTitle.setBorder(border);
		
		tapOnModeTitle.setFont(f);						// Tap On Mode Title
		tapOnModeTitle.setPreferredSize(d);
		tapOnModeTitle.setBorder(border);
		
		fpgaProgrammerTitle.setFont(f);					// FPGA Programmer Title
		fpgaProgrammerTitle.setPreferredSize(d);
		fpgaProgrammerTitle.setBorder(border);
		
		
		/**
		 * Button Styles
		 */
		
		Font f2 = new Font("Arial", Font.BOLD, 12);
		Dimension d2 = new Dimension(150, 30);
		
		loopbackModeButton.setFont(f2);				
		loopbackModeButton.setToolTipText(loopBackModeDescription());
		loopbackModeButton.setPreferredSize(d2);
		
		tapOnModeButton.setFont(f2);
		tapOnModeButton.setToolTipText(tapOnModeDescription());
		tapOnModeButton.setPreferredSize(d2);
		
		fpgaProgrammerButton.setFont(f2);
		fpgaProgrammerButton.setToolTipText(fpgaProgrammerDescription());
		fpgaProgrammerButton.setPreferredSize(d2);
		
		setButtonsEnabled();
	}

	@Override
	public void setActionCommands() {
		loopbackModeButton.setActionCommand(ActionCommands.SELECT_COMMAND_MODE);
		tapOnModeButton.setActionCommand(ActionCommands.SELECT_TAP_ON_MODE);
		fpgaProgrammerButton.setActionCommand(ActionCommands.SELECT_FPGA_PROGRAMMER_MODE);
	}

	@Override
	public void addPublicActionListeners(ActionListener listener) {
		loopbackModeButton.addActionListener(listener);
		tapOnModeButton.addActionListener(listener);
		fpgaProgrammerButton.addActionListener(listener);
	}

	@Override
	public void addComponentsToPanel() {
		
		addComponentToPanel(loopbackModeTitle, 0, 0);
		addComponentToPanel(loopbackModeButton, 1, 0);
		
		addComponentToPanel(tapOnModeTitle, 0, 1);
		addComponentToPanel(tapOnModeButton, 1, 1);
		
		addComponentToPanel(fpgaProgrammerTitle, 0, 2);
		addComponentToPanel(fpgaProgrammerButton, 1, 2);
	}
	
	/**
	 * Does what it says on the tin
	 */
	private void setButtonsEnabled() { 
		
		System.out.println(String.format("\n###Changing Main Menu Options Availablility  \nLOOPBACK: %s\nTAP ON: %s\nPROGRAMMERL %s\n\n", this.isCommandModeEnabled, this.isTapOnEnabled, this.isDownloadEnabled)); // console / debug print
		loopbackModeButton.setEnabled(this.isCommandModeEnabled);
		tapOnModeButton.setEnabled(this.isTapOnEnabled);
		fpgaProgrammerButton.setEnabled(this.isDownloadEnabled);	
	}
	
	/**********************
	 * Methods
	 */
	
	@Override
	public JPanel getPanel() {
		this.setBorder(new TitledBorder("MAIN MENU"));
		return this;
	}
	
	
	@Override
	public String toString() {
		return this.name;
	}
	
	/**********************
	 * Observer Methods
	 */
	
	/**
	 * Update Logic for is Network in use :
	 * 
	 * 	TRUE : 
	 * 		Check which button is in use and disable use of other buttons.
	 * 	FALSE : 
	 * 		Set all buttons enabled.
	 */
	@Override
	public void update(Observable obs, Object obj) {
		this.isNetworkInUse = (Boolean) obj;	/**	Set local instance to new, changed value	**/
		
		if(isNetworkInUse) { 
			/*	Each of following conditionals should be mutually exclusive 	*/
			boolean runUDPLoopBack = (Boolean) ObservableFacade.getValue(ORNames.RUN_UDP_COMMANDMODE_NETWORK);
			boolean runUDPTapOn = (Boolean) ObservableFacade.getValue(ORNames.RUN_UDP_TAPONMODE_NETWORK);
			boolean runDownloadTool = (Boolean) ObservableFacade.getValue(ORNames.RUN_UDP_FPGAPROGRAMMER_NETWORK);
			
			if(runUDPLoopBack) { 
				this.isCommandModeEnabled =  true;
				this.isTapOnEnabled = false; 
				this.isDownloadEnabled = false;
			}
			
			if(runUDPTapOn) { 
				this.isCommandModeEnabled =  false;
				this.isTapOnEnabled = true; 
				this.isDownloadEnabled = false;
			}
			
			if(runDownloadTool) { 
				this.isCommandModeEnabled =  false;
				this.isTapOnEnabled = false; 
				this.isDownloadEnabled = true;
			}
			
		} else { 
			
			this.isCommandModeEnabled =  true;
			this.isTapOnEnabled = true; 
			this.isDownloadEnabled = true;
			
		}
		
		setButtonsEnabled();
	}
	
	/**********************
	 * Not used
	 */

	@Override
	public void addLocalActionListeners() {
		// Not used
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// Not used
	}


}
