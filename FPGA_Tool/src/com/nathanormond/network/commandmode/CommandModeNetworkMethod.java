package com.nathanormond.network.commandmode;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;

import com.nathanormond.fileio.WriteFileThread;
import com.nathanormond.gui.panels.generic.views.temperatures.MultiTempPanel;
import com.nathanormond.network.AbstractUDPNetworkMethod;
import com.nathanormond.network.components.MySocket;
import com.nathanormond.network.components.Receiver;
import com.nathanormond.obsrv.observable.ORNames;
import com.nathanormond.obsrv.observable.ObservableFacade;

public class CommandModeNetworkMethod extends AbstractUDPNetworkMethod {

	
	/*************************************
	 * INSTANTIATION
	 */
	
	private long  threadSleepMS = 1000;
	private static int fileWriteCacheBuffer = 10;
	
	public CommandModeNetworkMethod() {
		super(fileWriteCacheBuffer);
	}

	/**
	 * At instantiation a new socket and receiver are created
	 */
	@Override
	public void instantiateNetworkObjects() {
		socket = new MySocket(); 
		rxu = new Receiver(); 
		System.out.println("Network Ready : COMMAND MODE");
		System.out.println(consoleOutputBreak());
	}
	
	
	/*************************************
	 * NETWORK LOOP
	 */
	
	/**
	 * Here Txu is created (with message selected from GUI) 
	 * Opens resources (Socket). 
	 * Clears any file writing cache
	 * Rests counter
	 */
	@Override
	public void beforeNetworkLoop() {
		System.out.println("##### COMMAND MODE NETWORK STARTING :");
		ObservableFacade.setValue(ORNames.RUN_UDP_COMMANDMODE_NETWORK, true);
		int myPort = (int) getObserver(ORNames.MY_UDP_PORT).getVariableInstance();
		socket.openSocket(myPort);
		WriteFileThread.clearCache();
		setCount(1);
		
		setTxuCommand((String) getObserver(ORNames.SELECTED_START_COMMAND).getVariableInstance(), (String) getObserver(ORNames.SELECTED_STOP_COMMAND).getVariableInstance());
		
		boolean isSetDelayTime = (boolean) getObserver(ORNames.IS_DELAY_TIME_SET).getVariableInstance();
		if(isSetDelayTime){ 
			threadSleepMS = (int) getObserver(ORNames.COMMAND_DELAY).getVariableInstance() * 1000;
		}
	}
	
	/**
	 * Checks if setNumCommands is set to true: 
	 * 		TRUE: Checks count has not reached the number of commands to send AND runLoopBackTest is true (returns && value)
	 * 		FALSE: checks runUDPLoopBacktest is true (returns its value)
	 * One line ternary statement antipattern
	 */
	@Override
	public boolean networkLoopCondition() {
		boolean isSetNumCommands = (boolean) getObserver(ORNames.IS_NUM_COMMANDS_SET).getVariableInstance();
		int commandsToSend = (int) getObserver(ORNames.NUM_COMMANDS_TO_SEND).getVariableInstance();
		boolean runUDPLoopBack =  (boolean) getObserver(ORNames.RUN_UDP_COMMANDMODE_NETWORK).getVariableInstance();
		return isSetNumCommands ? ((((int) getCount() - 1) != commandsToSend) &&  runUDPLoopBack) : runUDPLoopBack;
	}
	

	/**
	 * Sends TxU command 
	 * Listens for a response 
	 * Updates GUI based on response 
	 * Waits for defined time before repeating 
	 * increments counter
	 */
	@Override
	public void networkLoop() {
		System.out.println("SENDING NEW PACKET...\n" + consoleOutputBreak() + getCount());
		
		txu.sendOpenPacket(socket);											/*Send ON command to FPGA*/
		
		try{
			rxu.receivePacket(socket);										/*Listen for FPGA response*/
			System.out.println("RESPONSE RECEIVED : " + rxu.receiveData);
		} catch (SocketTimeoutException e){ 
			System.out.println("NO RESPONSE, CONNECTION TIMED OUT");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		updateOutputGraphics();
	
		try {
			Thread.sleep(threadSleepMS);									/*Delay timer (loop frequency)*/
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		setCount(getCount() + 1);
	}
	
	@Override
	public void updateOutputGraphics() {
		ByteBuffer byteBuffer = ByteBuffer.wrap(rxu.receiveData);
		if((boolean) getObserver(ORNames.DISPLAY_FULL_RX_PACKET_CONTENT).getVariableInstance()) {
			FullPacketContentAnalysis.modifyOutputs(byteBuffer, getCount());
		} else { 
			LoopBackModePacketAnalysis.loopBackOutputString(byteBuffer, getCount());
		}
		
	}

	/**
	 * Closes resources. 
	 * Awaits stop button being pressed (If test was set to run a particular number of times).
	 */
	@Override
	public void afterNetworkLoop() {		
		txu.sendClosePacket(socket);
		socket.closeSocket();
		System.out.println("##### COMMAND MODE NETWORK STOPPED :");
		awaitStopButton();
		
	}
	
	/**
	 * Loop Runs awaiting stop button to be pressed by user
	 */
	private void awaitStopButton() {
		int count = 0;
		while ((boolean) getObserver(ORNames.RUN_UDP_COMMANDMODE_NETWORK).getVariableInstance()) {

			if ((count % 20) == 0) {
				System.out.println(String.format("\n\n %s \n\nEnd of Transmission\nPress Stop Button .. ", consoleOutputBreak()));
				
			}
			
			try {
				Thread.sleep(threadSleepMS); // slow down potentially infinite loop
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			count++;
		}
	}

}
