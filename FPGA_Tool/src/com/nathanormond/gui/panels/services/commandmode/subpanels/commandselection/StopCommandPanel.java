package com.nathanormond.gui.panels.services.commandmode.subpanels.commandselection;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;

import com.nathanormond.gui.components.jlist.CommandList;
import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;
import com.nathanormond.obsrv.observable.ORNames;
import com.nathanormond.obsrv.observable.ObservableFacade;
import com.nathanormond.obsrv.observer.VariableObserver;
import com.nathanormond.xml.jaxb.commands.Command;


public class StopCommandPanel extends AbstractPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CommandList stopCommandList;
	private JLabel title;
	private JScrollPane pane;
	private List<Command> stopCommands;

	
	public StopCommandPanel(ActionListener publicListener) {
		super(publicListener);
	}

	/******************
	 * CREATE PANEL
	 */
	
	@Override
	public void instantiateComponents() {
		
		stopCommands = ObservableFacade.getValue(ORNames.STOP_CMDS);
		
		updateList();
		
		title = new JLabel("STOP CMD");
		title.setFont(getFont());
		pane = new JScrollPane(stopCommandList.getJList());
		pane.setPreferredSize(new Dimension(210,210));
	}
	
	public Font getFont(){ 
		return new Font("Arial", Font.BOLD, 20);
	}
	
	/**
	 * 
	 */
	public void updateList() { 
		List<String> commandIDs = new ArrayList<String>();
		for(Command command : stopCommands){ 
			commandIDs.add(command.getId());
		}
		
		stopCommandList = new CommandList(commandIDs){

			private static final long serialVersionUID = 1L;

			@Override
			public void valueChanged(ListSelectionEvent e) {
				ObservableFacade.setValue(ORNames.SELECTED_STOP_COMMAND, getSelectedValue().toString());
			} 
			
		}; 
	}

	@Override
	public void addComponentsToPanel() {
		addComponentToPanel(title , 0, 0);
		addComponentToPanel(pane, 0, 1);
	}
	
	@Override
	public void setActionCommands() {
		//not used
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
		//not used
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//not used
	}
	
	/******************
	 * RETURN PANEL
	 */

	@Override
	public JPanel getPanel() {
		return this;
	}
	
	public void setStopCommands(List<Command> stopCommands) {
		this.stopCommands = stopCommands;
	}
	
	
}
