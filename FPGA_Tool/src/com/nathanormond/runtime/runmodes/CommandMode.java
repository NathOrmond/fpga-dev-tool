package com.nathanormond.runtime.runmodes;

import java.util.Observable;

import javax.swing.JPanel;

import com.nathanormond.gui.frames.OutputTerminalFrame;
import com.nathanormond.gui.panels.outputterminal.commandmode.fullpacket.OutputFullPacketDisplayPanel;
import com.nathanormond.gui.panels.outputterminal.commandmode.loopback.OutputLoopBackTestPanel;
import com.nathanormond.gui.panels.services.commandmode.CommandModePanel;
import com.nathanormond.network.commandmode.CommandModeNetworkMethod;
import com.nathanormond.obsrv.ActionCommands;
import com.nathanormond.obsrv.observable.ORNames;
import com.nathanormond.obsrv.observable.ObservableFacade;
import com.nathanormond.obsrv.observer.VariableObserver;

public class CommandMode extends AbstractModeComposer {
	
	public static CommandModePanel commandModePanel;
	private static CommandModeNetworkMethod network;
	private OutputLoopBackTestPanel outputLoopback;
	private OutputFullPacketDisplayPanel outputFull;
	private static VariableObserver<Boolean> runUDPLoopBacktestObserver;
	private static VariableObserver<Boolean> fullPacketsContentDisplayedObserver;
	
	/********************************
	 * Initialisation
	 */

	public CommandMode() {
		super();
	}

	@Override
	public void instantiateObjects() {
		commandModePanel = new CommandModePanel(this);
		network = new CommandModeNetworkMethod();
		addObservers();
	}
	
	/********************************
	 * Action Switch
	 */

	@Override
	public void actionSwitch(String actionCommand) {
		switch(actionCommand) {
			case ActionCommands.START: 
				startSubRoutine();
				break;
			case ActionCommands.STOP:
				stopSubRoutine();
				break;
		}
	}
	
	/********************************
	 * Action Methods
	 */

	private void startSubRoutine() {
		commandModePanel.startStopPanel.start.startButton.setEnabled(false);
		commandModePanel.startStopPanel.stop.stopButton.setEnabled(true);
		ObservableFacade.setValue(ORNames.RUN_UDP_COMMANDMODE_NETWORK, true);
		ObservableFacade.setValue(ORNames.IS_NETWORK_IN_USE, true);
		createOutputWindow();
		Thread t1 = new Thread(network);		// network test
		t1.start();
	}

	private void stopSubRoutine() {
		commandModePanel.startStopPanel.start.startButton.setEnabled(true);
		commandModePanel.startStopPanel.stop.stopButton.setEnabled(false);
		ObservableFacade.setValue(ORNames.RUN_UDP_COMMANDMODE_NETWORK, false);
		ObservableFacade.setValue(ORNames.IS_NETWORK_IN_USE, false);
	}
	
	private void createOutputWindow() { 
		if((boolean) ObservableFacade.getValue(ORNames.DISPLAY_FULL_RX_PACKET_CONTENT)) { 
			outputFull = new OutputFullPacketDisplayPanel(this);
			new OutputTerminalFrame(outputFull);
		} else { 
			outputLoopback = new OutputLoopBackTestPanel(this);
			new OutputTerminalFrame(outputLoopback);
		}
	}
	
	
	/********************************
	 * OBSERVERS
	 */
	
	/**
	 * 
	 */
	private void addObservers() { 
		ObservableFacade.addObserver(ORNames.START_CMDS, this);
		ObservableFacade.addObserver(ORNames.STOP_CMDS, this);
		runUDPLoopBacktestObserver = new VariableObserver<>(String.format("%s : %s", ORNames.RUN_UDP_COMMANDMODE_NETWORK, "Command Mode Observer"));
		fullPacketsContentDisplayedObserver = new VariableObserver<>(String.format("%s : %s", ORNames.DISPLAY_FULL_RX_PACKET_CONTENT, "Command Mode Observer"));
		runUDPLoopBacktestObserver.setVariableInstance(ObservableFacade.getValue(ORNames.RUN_UDP_COMMANDMODE_NETWORK));
		fullPacketsContentDisplayedObserver.setVariableInstance(ObservableFacade.getValue(ORNames.DISPLAY_FULL_RX_PACKET_CONTENT));
		ObservableFacade.addObserver(ORNames.RUN_UDP_COMMANDMODE_NETWORK, runUDPLoopBacktestObserver);
		ObservableFacade.addObserver(ORNames.DISPLAY_FULL_RX_PACKET_CONTENT, fullPacketsContentDisplayedObserver);
	}
	
	@Override
	public void update(Observable obs, Object obj) {
		commandModePanel = new CommandModePanel(this);
	}
	
	/********************************
	 * GETTERS & SETTERS
	 */
	
	public JPanel getCommandModePanel() {
		return commandModePanel.getPanel();
	}




}
