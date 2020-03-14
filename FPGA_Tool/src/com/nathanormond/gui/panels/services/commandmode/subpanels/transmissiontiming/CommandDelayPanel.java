package com.nathanormond.gui.panels.services.commandmode.subpanels.transmissiontiming;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.nathanormond.gui.components.textarea.NumberTextArea;
import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;
import com.nathanormond.obsrv.ActionCommands;
import com.nathanormond.obsrv.observable.ORNames;
import com.nathanormond.obsrv.observable.ObservableFacade;


public class CommandDelayPanel extends AbstractPanel {
	
	private static final long serialVersionUID = 1L;
	private JLabel commandDelayLabel; 
	private JButton setCommandDelayButton;
	private NumberTextArea commandDelayArea;

	public CommandDelayPanel(ActionListener publicListener) {
		super(publicListener);
	}
	
	/******************
	 * CREATE PANEL
	 */
	
	@Override
	public void instantiateComponents() {
		commandDelayLabel = new JLabel("set command delay (seconds)");
		commandDelayArea = new NumberTextArea("command delay");
		setCommandDelayButton = new JButton("SET");
	}

	@Override
	public void addComponentsToPanel() {
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		addComponentToPanel(commandDelayLabel, 0, 0);
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		addComponentToPanel(commandDelayArea, 0, 1);
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		addComponentToPanel(setCommandDelayButton, 0, 2);
	}
	
	@Override
	public void setActionCommands() {
		setCommandDelayButton.setActionCommand(ActionCommands.SET_LOOP_DELAY_TIME);
	}
	
	
	/******************
	 * PUBLIC ACTION LISTENER
	 */
	
	@Override
	public void addPublicActionListeners(ActionListener listener) {
		//none
	}
	
	/******************
	 * THIS ACTION LISTENER
	 */
	
	@Override
	public void addLocalActionListeners() {
		setCommandDelayButton.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){ 
			case ActionCommands.SET_LOOP_DELAY_TIME:
				if(commandDelayArea.isContentInteger()){
					ObservableFacade.setValue(ORNames.COMMAND_DELAY, Integer.parseInt(commandDelayArea.getText()));
					ObservableFacade.setValue(ORNames.IS_DELAY_TIME_SET, true);
				}		
				break;
		}
		
	}
	
	/******************
	 * RETURN PANEL
	 */

	@Override
	public JPanel getPanel() {
		this.setBorder(new TitledBorder("command delay"));
		return this;
	}

}
