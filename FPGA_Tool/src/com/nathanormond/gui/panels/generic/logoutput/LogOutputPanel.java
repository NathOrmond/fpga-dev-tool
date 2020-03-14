package com.nathanormond.gui.panels.generic.logoutput;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;

public class LogOutputPanel extends AbstractPanel {

	private static final long serialVersionUID = 1L;
	private LogOutputCheckPanel logOutputCheckPanel;
	private ChooseLogFilePanel chooseLogFilePanel;
	
	public LogOutputPanel(ActionListener publicListener) {
		super(publicListener, true);
	}

	/******************
	 * CREATE PANEL
	 */
	
	@Override
	public void instantiateComponents() {
		// not used with this constructor
	}

	@Override
	public void addComponentsToPanel() {
		addComponentToPanel(logOutputCheckPanel.getPanel(), 0, 0);
		addComponentToPanel(chooseLogFilePanel.getPanel(), 1, 0);
	}
	
	@Override
	public void setActionCommands() {
		
	}
	
	
	/******************
	 * PUBLIC ACTION LISTENER
	 */
	
	@Override
	public void addPublicActionListeners(ActionListener listener) {
		logOutputCheckPanel = new LogOutputCheckPanel(listener);
		chooseLogFilePanel = new ChooseLogFilePanel(listener);
	}
	
	/******************
	 * THIS ACTION LISTENER
	 */
	
	@Override
	public void addLocalActionListeners() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/******************
	 * RETURN PANEL
	 */

	@Override
	public JPanel getPanel() {
		this.setBorder(new TitledBorder("Output File (.txt)"));
		return this;
	}

}
