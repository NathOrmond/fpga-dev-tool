package com.nathanormond.gui.panels.services.commandmode.subpanels.transmissiontiming;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import com.nathanormond.gui.components.textarea.NumberTextArea;
import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;
import com.nathanormond.obsrv.ActionCommands;
import com.nathanormond.obsrv.observable.ORNames;
import com.nathanormond.obsrv.observable.ObservableFacade;

public class NumCommandsPanel extends AbstractPanel {

	private static final long serialVersionUID = 1L;
	private JRadioButton nRadioButton; 
	private JRadioButton infiniteRadioButton;
	private ButtonGroup radioButtonGroup;
	private NumberTextArea numberSendCommandsArea;
	private JButton setNumCommandsButton;
	private boolean infiniteCommands;
	private int numCommands;
	
	public NumCommandsPanel(ActionListener publicListener) {
		super(publicListener);
	}

	/******************
	 * CREATE PANEL
	 */
	
	@Override
	public void instantiateComponents() {
		nRadioButton = new JRadioButton("set number");
		infiniteRadioButton = new JRadioButton("infinite");
		radioButtonGroup = new ButtonGroup();
		numberSendCommandsArea = new NumberTextArea("enter num ");
		setNumCommandsButton = new JButton("SET");
	}

	@Override
	public void addComponentsToPanel() {
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		addComponentToPanel(nRadioButton, 0, 0);
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		addComponentToPanel(infiniteRadioButton, 1, 0);
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		addComponentToPanel(numberSendCommandsArea, 0, 1);
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		addComponentToPanel(setNumCommandsButton, 0, 2);
		
		numberSendCommandsArea.setEditable(!infiniteCommands);
		setNumCommandsButton.setEnabled(!infiniteCommands);
	}
	
	@Override
	public void setActionCommands() {
		nRadioButton.setActionCommand(ActionCommands.N_NUMBER_COMMANDS);
		infiniteRadioButton.setActionCommand(ActionCommands.INFINITE_NUMBER_COMMANDS);
		infiniteRadioButton.setSelected(true);
		infiniteCommands = true;
		radioButtonGroup.add(nRadioButton);
		radioButtonGroup.add(infiniteRadioButton);
		setNumCommandsButton.setActionCommand(ActionCommands.SET_NUM_COMMANDS);
		
		numberSendCommandsArea.setEditable(false);
	}
	
	
	/******************
	 * PUBLIC ACTION LISTENER
	 */
	
	@Override
	public void addPublicActionListeners(ActionListener listener) {
		// no public action listeners
	}
	
	/******************
	 * THIS ACTION LISTENER
	 */
	
	@Override
	public void addLocalActionListeners() {
		nRadioButton.addActionListener(this);
		infiniteRadioButton.addActionListener(this);
		setNumCommandsButton.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){ 
		case ActionCommands.N_NUMBER_COMMANDS:
			infiniteCommands = false;
			break; 
		case ActionCommands.INFINITE_NUMBER_COMMANDS: 
			infiniteCommands = true;
			numberSendCommandsArea.setRedBackGround();
			ObservableFacade.setValue(ORNames.IS_NUM_COMMANDS_SET, false);
			break;
		case ActionCommands.SET_NUM_COMMANDS: 
			if(numberSendCommandsArea.isContentInteger()){ 
				ObservableFacade.setValue(ORNames.NUM_COMMANDS_TO_SEND, Integer.parseInt(numberSendCommandsArea.getText()));
				ObservableFacade.setValue(ORNames.IS_NUM_COMMANDS_SET, true);
			}
			break;
		}
		numberSendCommandsArea.setEditable(!infiniteCommands);
		setNumCommandsButton.setEnabled(!infiniteCommands);
	}
	
	/******************
	 * RETURN PANEL
	 */

	@Override
	public JPanel getPanel() {
		this.setBorder(new TitledBorder("send command num times"));
		return this;
	}
	
	/******************
	 * GETTERS / SETTERS
	 */
	
	public boolean isInfiniteCommands() {
		return infiniteCommands;
	}
	
	public int getNumCommands() {
		return numCommands;
	}
}