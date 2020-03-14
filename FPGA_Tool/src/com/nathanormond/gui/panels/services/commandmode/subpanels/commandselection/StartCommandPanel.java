package com.nathanormond.gui.panels.services.commandmode.subpanels.commandselection;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

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


public class StartCommandPanel extends AbstractPanel {

	private static final long serialVersionUID = 1L;
	private CommandList startCommandList;
	private JLabel title;
	private JScrollPane pane;
	private List<Command> startCommands;
	
	
	public StartCommandPanel(ActionListener publicListener) {
		super(publicListener);
	}

	/******************
	 * CREATE PANEL
	 */
	
	@Override
	public void instantiateComponents() {
		
		startCommands = ObservableFacade.getValue(ORNames.START_CMDS);
		
		
		updateList();
		
		title = new JLabel("START CMD");
		title.setFont(getFont());
		
	}
	
	/**
	 * 
	 */
	public Font getFont(){ 
		return new Font("Arial", Font.BOLD, 20);
	}
	
	/**
	 * 
	 */
	public void updateList() { 
		List<String> commandIDs = new ArrayList<String>();
		for(Command command : startCommands){ 
			commandIDs.add(command.getId());
		}
		
		startCommandList = new CommandList(commandIDs){
			private static final long serialVersionUID = 1L;
			@Override
			public void valueChanged(ListSelectionEvent e) {
				ObservableFacade.setValue(ORNames.SELECTED_START_COMMAND, getSelectedValue().toString());
			} 
		}; 
		
		pane = new JScrollPane(startCommandList.getJList());
		pane.setPreferredSize(new Dimension(210,210));
		
		addComponentToPanel(pane, 0, 1);
	}

	@Override
	public void addComponentsToPanel() {
		addComponentToPanel(title , 0, 0);
	}
	
	@Override
	public void setActionCommands() {
		// not used
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


	
	public void setStartCommands(List<Command> startCommands) {
		this.startCommands = startCommands;
		updateList();
	}
	
	
}
