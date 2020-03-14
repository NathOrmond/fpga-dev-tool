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

public class RunningBuildNetworkMethod extends AbstractUDPNetworkMethod{

	private String cmdid = "currentBuild", receivedMsg = "NULL";
	private int numNetworkIterations = 10;
	
	public RunningBuildNetworkMethod(int fileWriteCacheBuffer) {
		super(fileWriteCacheBuffer);
	}

	@Override
	public void instantiateNetworkObjects() {
		socket = new MySocket(); 
		rxu = new Receiver(); 
	}

	@Override
	public void beforeNetworkLoop() {
		System.out.println("###### POLL RUNNING REGION START: ");
		setObservableVar(ORNames.RUN_UDP_FPGAPROGRAMMER_NETWORK, true);
		setCount(1);
		int myPort = (int) getObserver(ORNames.MY_UDP_PORT).getVariableInstance();
		socket.openSocket(myPort);
		txuInit();
		sendExecuteRegionCmd();
	}
	
	private void txuInit() { 
		try {
			txu = new Transmitter(createExecuteRegionCommand(), createCloseCommand(), (String) ObservableFacade.getValue(ORNames.IP_ADDRESS), (int) ObservableFacade.getValue(ORNames.FPGA_UDP_PORT));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private List<String> createExecuteRegionCommand() { 
		List<String> pollRegionCommand = new ArrayList<>();
		
		for(String headerCommand : (List<String>) ObservableFacade.getValue(ORNames.HEADER_COMMANDS)) { 
			pollRegionCommand.add(headerCommand);
		}
		
		for(Command command : (List<Command>) ObservableFacade.getValue(ORNames.PROGRAM_COMMANDS)) { 
			if(command.getId().equals(this.cmdid)) { 
				for(String line : command.getLines()) {
					pollRegionCommand.add(line);
				}
			}
		}
		return pollRegionCommand;
	}
	
	private List<String> createCloseCommand(){ 
		List<String> closeCommand = new ArrayList<>();
		return closeCommand;
	}
	
	private void sendExecuteRegionCmd() { 
		txu.sendOpenPacket(socket);
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
		if(getCount() > numNetworkIterations) { 
			ObservableFacade.setValue(ORNames.DOWNLOAD_CONSOLE_PRINT_STRING, "NO RESPONSE : TERMINATING ATTEMPT");
			setObservableVar(ORNames.RUN_UDP_FPGAPROGRAMMER_NETWORK, false);
		}
	}
	
	/**
	 * 
	 * @param rx
	 * @return
	 */
	private String printRxOutput(String rx) {
		String rv = "";
		if (rx != null) {
			
			rv = rx.substring(((16 * 4)), (16 * 12));
			
		} else { 
			rv = "NULL RECEIVED STRING";
		}
			
		return rv;
	}

	@Override
	public void afterNetworkLoop() {
		txu.sendClosePacket(socket);
		socket.closeSocket();
		System.out.println("######  POLL RUNNING REGION STOPPED: ");
		ObservableFacade.setValue(ORNames.DOWNLOAD_CONSOLE_PRINT_STRING, printRxOutput(receivedMsg));
		ObservableFacade.setValue(ORNames.PROGRESS_BAR_STATUS, 100);
	}

	@Override
	public boolean networkLoopCondition() {
		return (boolean) ObservableFacade.getValue(ORNames.RUN_UDP_FPGAPROGRAMMER_NETWORK);
	}

	@Override
	public void updateOutputGraphics() {
		// not used
	}
}
