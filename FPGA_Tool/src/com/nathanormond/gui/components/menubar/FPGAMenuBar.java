package com.nathanormond.gui.components.menubar;

import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.nathanormond.obsrv.ActionCommands;

public class FPGAMenuBar extends JMenuBar {
	
	//ToDo :
	// Instructions
	
	
	private static final long serialVersionUID = 1L;
	JMenu fpgaTool;
	JMenuItem configure, logOutputs;
	
//	JMenu instructions;
//	JMenuItem commandsInstructions, sequenceCounterInstructions, buildProgrammerInstructions;

	public FPGAMenuBar(ActionListener listener) {
		instantiateMenu();
		addActionListeners(listener);
		addItemsToMenu();
	}
	
	private void instantiateMenu() { 
		fpgaTool = new JMenu("Options");
		configure = new JMenuItem("Configure Tool Settings");
		logOutputs = new JMenuItem("Log Output to file");
		
//		instructions = new JMenu("Instructions");
//		commandsInstructions = new JMenuItem("Command Sending / Receiving Instructions");
//		sequenceCounterInstructions = new JMenuItem("Sequence Counter Instructions");
//		buildProgrammerInstructions = new JMenuItem("Build Programmer Instructions");
	}
	
	private void addActionListeners(ActionListener listener){
		configure.setActionCommand(ActionCommands.CONFIG_MENU_ITEM);
		configure.addActionListener(listener);
		
		logOutputs.setActionCommand(ActionCommands.LOG_OUTPUT_MENU_ITEM);
		logOutputs.addActionListener(listener);
	}
	
	private void addItemsToMenu(){ 
		// Add Menu Items
		
		fpgaTool.add(configure);
		fpgaTool.add(logOutputs);
		
//		instructions.add(commandsInstructions);
//		instructions.add(sequenceCounterInstructions);
//		instructions.add(buildProgrammerInstructions);
		
		// Add Menu 
		add(fpgaTool);
//		add(instructions);
	}
	
}