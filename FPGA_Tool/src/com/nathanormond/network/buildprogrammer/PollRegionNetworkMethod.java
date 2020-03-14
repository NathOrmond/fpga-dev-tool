package com.nathanormond.network.buildprogrammer;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.nathanormond.network.AbstractUDPNetworkMethod;
import com.nathanormond.network.components.MySocket;
import com.nathanormond.network.components.Receiver;
import com.nathanormond.network.components.Transmitter;
import com.nathanormond.obsrv.observable.ORNames;
import com.nathanormond.obsrv.observable.ObservableFacade;
import com.nathanormond.xml.jaxb.commands.Command;
import com.nathanormond.xml.jaxb.commands.FlashConfigAddress;

public class PollRegionNetworkMethod extends AbstractUDPNetworkMethod {

	private String cmdid = "pollRegion";
	private int loopAttempts = 10;
	private String receivedMsg;
	
	public PollRegionNetworkMethod(int fileWriteCacheBuffer) {
		super(fileWriteCacheBuffer);
	}
	
	@Override
	public void instantiateNetworkObjects() {
		socket = new MySocket(); 
		rxu = new Receiver(); 
	}

	@Override
	public void beforeNetworkLoop() {
		System.out.println("###### POLL SELECTED REGION STARTED: ");
		setObservableVar(ORNames.RUN_UDP_FPGAPROGRAMMER_NETWORK, true);
		setCount(1);
		int myPort = (int) getObserver(ORNames.MY_UDP_PORT).getVariableInstance();
		socket.openSocket(myPort);
		txuInit();
		sendExecuteRegionCmd();
	}
	
	/**
	 * sends the command to execute a region
	 */
	private void sendExecuteRegionCmd() { 
		txu.sendOpenPacket(socket);
	}
	
	
	/**
	 * Initialises new transmitter for selected region
	 */
	private void txuInit() { 
		try {
			txu = new Transmitter(createExecuteRegionCommand(), createCloseCommand(), (String) ObservableFacade.getValue(ORNames.IP_ADDRESS), (int) ObservableFacade.getValue(ORNames.FPGA_UDP_PORT));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Creates a list of the items to include in the send packet
	 * @return executeRegionCommand
	 */
	private List<String> createExecuteRegionCommand() { 
		List<String> pollRegionCommand = new ArrayList<>();
		
		for(String headerCommand : (List<String>) ObservableFacade.getValue(ORNames.HEADER_COMMANDS)) { 
			pollRegionCommand.add(headerCommand);
		}
		
		
		for(Command command : (List<Command>) ObservableFacade.getValue(ORNames.PROGRAM_COMMANDS)) { 
			if(command.getId().equals(this.cmdid)) { 
				for(String line : command.getLines()) { 
					if(line.equals(ORNames.FLASH_CONFIG_ADDRESSES)) { 
						for(FlashConfigAddress flashConfigAddress : (List<FlashConfigAddress>) ObservableFacade.getValue(ORNames.FLASH_CONFIG_ADDRESSES)) { 
							if(flashConfigAddress.getId().equals(ObservableFacade.getValue(ORNames.SELECTED_FLASH_SLOT))) { 
								pollRegionCommand.add(flashConfigAddress.getFlashConfigAddress());
							}
						}
					} else {  
						pollRegionCommand.add(line);
					}
				}
			}
		}
		
		System.out.println(pollRegionCommand);
		
		return pollRegionCommand;
	}
	
	/**
	 * creates a packet to send after the network is finished use
	 * @return
	 */
	private List<String> createCloseCommand() { 
		List<String> closeCommand = new ArrayList<>();
		return closeCommand;
	}
	

	@Override
	public void networkLoop() {
		boolean response = false;
		
		try {
			rxu.receivePacket(socket); /* Listen for FPGA response */
			System.out.println("Response Received!");
			String rx = new String(rxu.receiveData, StandardCharsets.UTF_8);
			System.out.println(rx);
			this.receivedMsg = rx;
			setObservableVar(ORNames.RUN_UDP_FPGAPROGRAMMER_NETWORK, false);
			response = true;
		} catch (SocketTimeoutException e) {
			System.out.println("NO RESPONSE, CONNECTION TIMED OUT");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(!response) {
		ObservableFacade.setValue(ORNames.DOWNLOAD_CONSOLE_PRINT_STRING, String.format("No Packet Received: Timeout :: %d", getCount()));
		} else { 
			ObservableFacade.setValue(ORNames.DOWNLOAD_CONSOLE_PRINT_STRING, "PACKET RECEIVED... FORMATTING");
		}
		setCount(getCount() + 1);
		ObservableFacade.setValue(ORNames.PROGRESS_BAR_STATUS, getCount());
		
		if(getCount() > loopAttempts) { 
			ObservableFacade.setValue(ORNames.DOWNLOAD_CONSOLE_PRINT_STRING, "NO RESPONSE : TERMINATING ATTEMPT");
			setObservableVar(ORNames.RUN_UDP_FPGAPROGRAMMER_NETWORK, false);
		}
	}

	/**
	 * @param rx
	 * @return
	 */
	private String printRxOutput(String rx) {
		String rv = "";
		if (rx != null) {
			
			rv = rx.substring(((16 * 4)), (16 * 12));
			ObservableFacade.setValue(ORNames.PROGRESS_BAR_STATUS, 80);
		} else { 
			rv = "NULL RECEIVED STRING";
			ObservableFacade.setValue(ORNames.PROGRESS_BAR_STATUS, 80);
		}
			
		return rv;
	}

	@Override
	public void afterNetworkLoop() {
		txu.sendClosePacket(socket);
		socket.closeSocket();
		System.out.println("######  POLL SELECTED REGION STOPPED: ");
		ObservableFacade.setValue(ORNames.DOWNLOAD_CONSOLE_PRINT_STRING, printRxOutput(receivedMsg));
		ObservableFacade.setValue(ORNames.PROGRESS_BAR_STATUS, 100);
	}

	@Override
	public boolean networkLoopCondition() {
		return (boolean) ObservableFacade.getValue(ORNames.RUN_UDP_FPGAPROGRAMMER_NETWORK);
	}

	@Override
	public void updateOutputGraphics() {
		
	}

}
