package com.nathanormond.gui.panels.services.buildprogrammer.subpanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;
import com.nathanormond.obsrv.ActionCommands;

public class WorkingRegionOptionsPanel extends AbstractPanel {
	
	private static final long serialVersionUID = 1L;
	JButton eraseButton, uploadButton, setRunningButton;
	
	public WorkingRegionOptionsPanel(ActionListener publicListener) {
		super(publicListener);
	}

	/***********************************************
	 * Instantiation
	 */
	
	
	@Override
	public void instantiateComponents() {
		
		eraseButton = new JButton("ERASE");
		uploadButton = new JButton("UPLOAD");
		setRunningButton = new JButton("SET RUNNING");
		
		styleComponenets();
	}
	
	private void styleComponenets() { 
		
		Font f = new Font("Arial", Font.BOLD, 12);
		Dimension d = new Dimension(150, 30);
		
		eraseButton.setFont(f);				
		eraseButton.setToolTipText(eraseRegionButtonToolTip());
		eraseButton.setPreferredSize(d);
		eraseButton.setBackground(Color.RED);
		eraseButton.setEnabled(true);
		
		uploadButton.setFont(f);				
		uploadButton.setToolTipText(uploadButtonToolTip());
		uploadButton.setPreferredSize(d);
		uploadButton.setEnabled(true);
		
		setRunningButton.setFont(f);				
		setRunningButton.setToolTipText(setRunningButtonToolTip());
		setRunningButton.setPreferredSize(d);
		setRunningButton.setEnabled(true);
	}
	
	@Override
	public void setActionCommands() {
		eraseButton.setActionCommand(ActionCommands.ERASE_REGION);
		uploadButton.setActionCommand(ActionCommands.UPLOAD_NEW_BUILD);
		setRunningButton.setActionCommand(ActionCommands.SET_RUNNING);
	}
	
	@Override
	public void addPublicActionListeners(ActionListener listener) {
		eraseButton.addActionListener(listener);
		uploadButton.addActionListener(listener);
		setRunningButton.addActionListener(listener);
	}

	@Override
	public void addLocalActionListeners() {
		// not used
	}

	@Override
	public void addComponentsToPanel() {
		addComponentToPanel(eraseButton, 0, 0);
		addComponentToPanel(uploadButton, 1, 0);
		addComponentToPanel(setRunningButton, 2, 0);
	}


	
	/***********************************************
	 * Action Events
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// not used as no actions	
	}
	
	/***********************************************
	 * TOOL TIP STRINGS
	 */

	private String eraseRegionButtonToolTip() { 
		return "<html>Erase memory contents of current selected region</html>";
	}
	
	private String uploadButtonToolTip() { 
		return "<html>Upload a new build to selected region</html>";
	}
	
	private String setRunningButtonToolTip() { 
		return "<html>This will set the currently selected region to run</html>";
	}
	
	/***********************************************
	 * GETTERS & SETTERS
	 */
	
	@Override
	public JPanel getPanel() {
		this.setBorder(new TitledBorder("SELECTED REGION PROGRAMMING OPTIONS"));
		return this;
	}


}
