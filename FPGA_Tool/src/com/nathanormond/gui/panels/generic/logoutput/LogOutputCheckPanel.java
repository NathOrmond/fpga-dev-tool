package com.nathanormond.gui.panels.generic.logoutput;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;
import com.nathanormond.obsrv.ActionCommands;
import com.nathanormond.obsrv.observable.ORNames;
import com.nathanormond.obsrv.observable.ObservableFacade;


public class LogOutputCheckPanel extends AbstractPanel {
	
	private static final long serialVersionUID = 1L;
	JLabel logOutputLabel; 
	JCheckBox logOutputCheckBox;

	public LogOutputCheckPanel(ActionListener publicListener) {
		super(publicListener);
		// TODO Auto-generated constructor stub
	}

	/******************
	 * CREATE PANEL
	 */
	
	@Override
	public void instantiateComponents() {
		logOutputLabel = new JLabel("Log Output?");
		logOutputCheckBox = new JCheckBox();
	}

	@Override
	public void addComponentsToPanel() {
		addComponentToPanel(logOutputLabel, 0, 0);
		addComponentToPanel(logOutputCheckBox, 0, 1);
	}
	
	@Override
	public void setActionCommands() {
		logOutputCheckBox.setActionCommand(ActionCommands.LOG_FILE_CHECKBOX);
	}
	
	
	/******************
	 * PUBLIC ACTION LISTENER
	 */
	
	@Override
	public void addPublicActionListeners(ActionListener listener) {
		//not used
	}
	
	/******************
	 * THIS ACTION LISTENER
	 */
	
	@Override
	public void addLocalActionListeners() {
		logOutputCheckBox.addActionListener(this);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){ 
			case ActionCommands.LOG_FILE_CHECKBOX:
				ObservableFacade.setValue(ORNames.LOG_TO_OUTPUT, logOutputCheckBox.isSelected());
				break;
		}
		
	}
	
	/******************
	 * RETURN PANEL
	 */

	@Override
	public JPanel getPanel() {
		return this;
	}
	
}