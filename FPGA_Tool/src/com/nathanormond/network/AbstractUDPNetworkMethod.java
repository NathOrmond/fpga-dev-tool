package com.nathanormond.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.nathanormond.fileio.WriteFileThread;
import com.nathanormond.network.components.MySocket;
import com.nathanormond.network.components.Receiver;
import com.nathanormond.network.components.Transmitter;
import com.nathanormond.obsrv.observable.ORNames;
import com.nathanormond.obsrv.observable.ObservableFacade;
import com.nathanormond.obsrv.observer.VariableObserver;
import com.nathanormond.xml.jaxb.commands.Command;

/**
 * 
 * This class extracts the common framework used by classes orchestrating
 * an UDP "networking method" and creates the boiler plate scaffolding for 
 * classes extending this to use and specify.
 * 
 * @author User
 *
 */
public abstract class AbstractUDPNetworkMethod implements Runnable {
	
	
	/*************************************
	 * INITIALISATION
	 */
	
	private int count, fileWriteCacheBuffer, loopBuffer;
	public MySocket socket;
	public Receiver rxu; 
	public Transmitter txu;
	private static List<VariableObserver> observers;
	
	public AbstractUDPNetworkMethod(int fileWriteCacheBuffer) {
		System.out.println(consoleOutputBreak());
		this.fileWriteCacheBuffer = fileWriteCacheBuffer;
		observers = new ArrayList<VariableObserver>();
		defaultObservers();
		instantiateNetworkObjects();
	}
	
	public abstract void instantiateNetworkObjects();
	
	/*************************************
	 * NETWORK LOOP
	 */

	/**
	 * Networking is ran on separate thread for speed
	 */
	@Override
	public void run() {
		beforeNetworkLoop();
		while(networkLoopCondition()) { 
			networkLoop();
			boolean writeFile = (boolean) getObserver(ORNames.LOG_TO_OUTPUT).getVariableInstance();
			if (writeFile) {
				fileWrite();
			}
		}
		afterNetworkLoop();
	}
	
	public abstract void beforeNetworkLoop();
	
	public abstract void networkLoop();
	
	public abstract void afterNetworkLoop();
	
	/**
	 * Boolean defining conditions under which network loop method runs.
	 * @return continueToRunNetwork
	 */
	public abstract boolean networkLoopCondition();
	
	public abstract void updateOutputGraphics();
	
	/*************************************
	 * GENERIC NETWORK METHODS
	 */
	
	
	/**
	 * 
	 * Used for obtaining message to be transmitted. 
	 * Selects packet from XML chosen by "ID" and combines its content
	 * with header packet to create full Tx packet.
	 * 
	 * @param headerCommands
	 * @param commands
	 * @param id
	 * @return
	 */
	public List<String> getCombinedCommands(List<String> headerCommands, List<Command> commands, String id){ 
		List<String> combined = new ArrayList<String>(); 
		combined.addAll(headerCommands);
		for(Command command: commands){ 
			if(command.getId().equals(id)){ 
				combined.addAll(command.getLines());
			}
		}
		return combined;
	}
	
	/**
	 * Creates new transmitter, defining start and stop packets
	 * @param startCommandID
	 * @param stopCommandID
	 */
	@SuppressWarnings("unchecked")
	public void setTxuCommand(String startCommandID, String stopCommandID) { 
		try {
			List<String> headerCommands = (List<String>) getObserver(ORNames.HEADER_COMMANDS).getVariableInstance();
			List<Command> startCommands = (List<Command>) getObserver(ORNames.START_CMDS).getVariableInstance();
			List<Command> stopCommands = (List<Command>) getObserver(ORNames.STOP_CMDS).getVariableInstance();
			
			String ipAddress =  (String) getObserver(ORNames.IP_ADDRESS).getVariableInstance();
			int fpgaPort = (int) getObserver(ORNames.FPGA_UDP_PORT).getVariableInstance();
			
			txu = new Transmitter(getCombinedCommands(headerCommands, startCommands, startCommandID), getCombinedCommands(headerCommands, stopCommands, stopCommandID), ipAddress, fpgaPort);
		} catch (IOException e) {
			e.printStackTrace(); 
		}
	}
	
	/****************************************************
	 * Observer Methods
	 */
	
	/**
	 * Initialise Default Observers
	 */
	private void defaultObservers() { 
		VariableObserver<List<Command>> headerCommandsObserver = new VariableObserver<>(ORNames.HEADER_COMMANDS);
		VariableObserver<List<Command>> startCommandsObserver = new VariableObserver<>(ORNames.START_CMDS);
		VariableObserver<List<Command>> stopCommandObserver = new VariableObserver<>(ORNames.STOP_CMDS);
		VariableObserver<String> ipAddressObserver = new VariableObserver<>(ORNames.IP_ADDRESS);
		VariableObserver<Boolean> logFileObserver = new VariableObserver<>(ORNames.LOG_TO_OUTPUT);
		VariableObserver<String> logFilePathObserver = new VariableObserver<>(ORNames.OUTPUT_LOG_PATH);
		VariableObserver<Integer> myPortObserver = new VariableObserver<>(ORNames.MY_UDP_PORT);
		VariableObserver<Integer> fpgaPortObserver = new VariableObserver<>(ORNames.FPGA_UDP_PORT);
		VariableObserver<Boolean> runUDPTapOnObserver = new VariableObserver<>(ORNames.RUN_UDP_TAPONMODE_NETWORK);
		VariableObserver<String> selectedStartCommandObserver = new VariableObserver<>(ORNames.SELECTED_START_COMMAND);
		VariableObserver<String> selectedStopCommandObserver = new VariableObserver<>(ORNames.SELECTED_STOP_COMMAND);
		VariableObserver<Boolean> isSetDelayTimeObserver = new VariableObserver<>(ORNames.IS_DELAY_TIME_SET);
		VariableObserver<Integer> commandDelayObserver = new VariableObserver<>(ORNames.COMMAND_DELAY);
		VariableObserver<Boolean> isSetNumCommandsObserver = new VariableObserver<>(ORNames.IS_NUM_COMMANDS_SET);
		VariableObserver<Integer> commandsToSendObserver = new VariableObserver<>(ORNames.NUM_COMMANDS_TO_SEND);
		VariableObserver<Boolean> runUDPLoopBackObserver = new VariableObserver<>(ORNames.RUN_UDP_COMMANDMODE_NETWORK);
		VariableObserver<Boolean> isFullPacketContentsDisplayedObserver = new VariableObserver<>(ORNames.DISPLAY_FULL_RX_PACKET_CONTENT);
		
		headerCommandsObserver.setVariableInstance(ObservableFacade.getValue(ORNames.HEADER_COMMANDS));
		startCommandsObserver.setVariableInstance(ObservableFacade.getValue(ORNames.START_CMDS));
		stopCommandObserver.setVariableInstance(ObservableFacade.getValue(ORNames.STOP_CMDS));
		ipAddressObserver.setVariableInstance(ObservableFacade.getValue(ORNames.IP_ADDRESS));
		logFileObserver.setVariableInstance(ObservableFacade.getValue(ORNames.LOG_TO_OUTPUT));
		logFilePathObserver.setVariableInstance(ObservableFacade.getValue(ORNames.OUTPUT_LOG_PATH));
		myPortObserver.setVariableInstance(ObservableFacade.getValue(ORNames.MY_UDP_PORT));
		fpgaPortObserver.setVariableInstance(ObservableFacade.getValue(ORNames.FPGA_UDP_PORT));
		runUDPTapOnObserver.setVariableInstance(ObservableFacade.getValue(ORNames.RUN_UDP_TAPONMODE_NETWORK));
		selectedStartCommandObserver.setVariableInstance(ObservableFacade.getValue(ORNames.SELECTED_START_COMMAND));
		selectedStopCommandObserver.setVariableInstance(ObservableFacade.getValue(ORNames.SELECTED_STOP_COMMAND));
		isSetDelayTimeObserver.setVariableInstance(ObservableFacade.getValue(ORNames.IS_DELAY_TIME_SET));
		commandDelayObserver.setVariableInstance(ObservableFacade.getValue(ORNames.COMMAND_DELAY));
		isSetNumCommandsObserver.setVariableInstance(ObservableFacade.getValue(ORNames.IS_NUM_COMMANDS_SET));
		commandsToSendObserver.setVariableInstance(ObservableFacade.getValue(ORNames.NUM_COMMANDS_TO_SEND));
		runUDPLoopBackObserver.setVariableInstance(ObservableFacade.getValue(ORNames.RUN_UDP_COMMANDMODE_NETWORK));
		isFullPacketContentsDisplayedObserver.setVariableInstance(ObservableFacade.getValue(ORNames.DISPLAY_FULL_RX_PACKET_CONTENT)); 
		
		ObservableFacade.addObserver(ORNames.HEADER_COMMANDS, headerCommandsObserver);
		ObservableFacade.addObserver(ORNames.START_CMDS, startCommandsObserver);
		ObservableFacade.addObserver(ORNames.STOP_CMDS, stopCommandObserver);
		ObservableFacade.addObserver(ORNames.IP_ADDRESS, ipAddressObserver);
		ObservableFacade.addObserver(ORNames.LOG_TO_OUTPUT, logFileObserver);
		ObservableFacade.addObserver(ORNames.OUTPUT_LOG_PATH, logFilePathObserver);
		ObservableFacade.addObserver(ORNames.MY_UDP_PORT, myPortObserver);
		ObservableFacade.addObserver(ORNames.FPGA_UDP_PORT, fpgaPortObserver);
		ObservableFacade.addObserver(ORNames.RUN_UDP_TAPONMODE_NETWORK, runUDPTapOnObserver);
		ObservableFacade.addObserver(ORNames.SELECTED_START_COMMAND, selectedStartCommandObserver);
		ObservableFacade.addObserver(ORNames.SELECTED_STOP_COMMAND, selectedStopCommandObserver);
		ObservableFacade.addObserver(ORNames.IS_DELAY_TIME_SET, isSetDelayTimeObserver);
		ObservableFacade.addObserver(ORNames.COMMAND_DELAY, commandDelayObserver);
		ObservableFacade.addObserver(ORNames.IS_NUM_COMMANDS_SET, isSetNumCommandsObserver);
		ObservableFacade.addObserver(ORNames.NUM_COMMANDS_TO_SEND, commandsToSendObserver);		
		ObservableFacade.addObserver(ORNames.RUN_UDP_COMMANDMODE_NETWORK, runUDPLoopBackObserver);		
		ObservableFacade.addObserver(ORNames.DISPLAY_FULL_RX_PACKET_CONTENT, isFullPacketContentsDisplayedObserver);
		
		observers.add(headerCommandsObserver);
		observers.add(startCommandsObserver);
		observers.add(stopCommandObserver);
		observers.add(ipAddressObserver);
		observers.add(logFileObserver);
		observers.add(logFilePathObserver);
		observers.add(myPortObserver);
		observers.add(fpgaPortObserver);
		observers.add(runUDPTapOnObserver);
		observers.add(selectedStartCommandObserver);
		observers.add(selectedStopCommandObserver);
		observers.add(isSetDelayTimeObserver);
		observers.add(commandDelayObserver);
		observers.add(isSetNumCommandsObserver);
		observers.add(commandsToSendObserver);
		observers.add(runUDPLoopBackObserver);
		observers.add(isFullPacketContentsDisplayedObserver);

	}
	
	public <T> VariableObserver<T> getObserver(int i){ 
		return observers.get(i);
	}
	
	public <T> VariableObserver<T> getObserver(String name){ 
		for(VariableObserver observer : observers) { 
			if(observer.toString().equals(name)) { 
				return observer;
			}
		}
		return null;
	}
	
	public List<VariableObserver> getObservers(){ 
		return observers;
	}
	
	public <T> void setObservableVar(String varName, T newValue) { 
		ObservableFacade.setValue(varName, newValue);
	}
	
	public <T> T getObservableVar(String varName) { 
		return ObservableFacade.getValue(varName);
	}
	
	/****************************************************
	 * File Writing
	 */
	
	/**
	 * starts a new thread to append the file
	 */
	public void fileWrite(){ 
		if ((count % fileWriteCacheBuffer) == 0) { /* Cache log file writing (improves speed, prevents dropped packets) */
			VariableObserver<String> observer = getObserver(ORNames.OUTPUT_LOG_PATH);
			WriteFileThread fileWriter = new WriteFileThread(observer.getVariableInstance());
			WriteFileThread.clearCache();
			Thread t2 = new Thread(fileWriter);
			t2.start();
		}
	}
	

	/*************************************
	 * GETTERS & SETTERS
	 */
	
	public String consoleOutputBreak() {
		return "=========================================";
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public MySocket getSocket() {
		return socket;
	}

	public void setSocket(MySocket socket) {
		this.socket = socket;
	}

	public Receiver getRxu() {
		return rxu;
	}

	public void setRxu(Receiver rxu) {
		this.rxu = rxu;
	}

	public Transmitter getTxu() {
		return txu;
	}

	public void setTxu(Transmitter txu) {
		this.txu = txu;
	}
	
	public int getFileWriteCacheBuffer() {
		return fileWriteCacheBuffer;
	}

	public void setFileWriteCacheBuffer(int cacheBuffer) {
		this.fileWriteCacheBuffer = cacheBuffer;
	}
	
	public int getLoopBuffer() {
		return loopBuffer;
	}

	public void setLoopBuffer(int loopBuffer) {
		this.loopBuffer = loopBuffer;
	}
}
