package com.nathanormond.network.buildprogrammer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.nathanormond.network.AbstractUDPNetworkMethod;
import com.nathanormond.network.components.MySocket;
import com.nathanormond.network.components.Receiver;
import com.nathanormond.network.components.Transmitter;
import com.nathanormond.obsrv.observable.ORNames;
import com.nathanormond.obsrv.observable.ObservableFacade;
import com.nathanormond.xml.jaxb.commands.Command;
import com.nathanormond.xml.jaxb.commands.RunBuildStartAddress;


public class ExecuteRegionNetworkMethod extends AbstractUDPNetworkMethod {
	
	private String cmdid = "runBuild";
	private int loopAttempts = 10;

	public ExecuteRegionNetworkMethod(int fileWriteCacheBuffer) {
		super(fileWriteCacheBuffer);
	}

	@Override
	public void instantiateNetworkObjects() {
		socket = new MySocket(); 
		rxu = new Receiver(); 
	}

	@Override
	public void beforeNetworkLoop() {
		System.out.println("###### EXECUTE REGION STARTED: ");
		setObservableVar(ORNames.RUN_UDP_FPGAPROGRAMMER_NETWORK, true);
		setCount(1);
		int myPort = (int) getObserver(ORNames.MY_UDP_PORT).getVariableInstance();
		socket.openSocket(myPort);
		txuInit();
		sendExecuteRegionCmd();
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
		List<String> executeRegionCommand = new ArrayList<>();
		
		for(String headerCommand : (List<String>) ObservableFacade.getValue(ORNames.HEADER_COMMANDS)) { 
			executeRegionCommand.add(headerCommand);
		}
		
		
		for(Command command : (List<Command>) ObservableFacade.getValue(ORNames.PROGRAM_COMMANDS)) { 
			if(command.getId().equals(this.cmdid)) { 
				for(String line : command.getLines()) { 
					if(line.equals(ORNames.RUN_BUILD_START_ADDRESSES)) { 
						for(RunBuildStartAddress runBuildAddress : (List<RunBuildStartAddress>) ObservableFacade.getValue(ORNames.RUN_BUILD_START_ADDRESSES)) { 
							if(runBuildAddress.getId().equals(ObservableFacade.getValue(ORNames.SELECTED_FLASH_SLOT))) { 
								executeRegionCommand.add(runBuildAddress.getRunBuildStartAddress());
							}
						}
					} else {  
						executeRegionCommand.add(line);
					}
				}
			}
		}
		
		System.out.println(executeRegionCommand);
		
		return executeRegionCommand;
	}
	
	/**
	 * creates a packet to send after the network is finished use
	 * @return
	 */
	private List<String> createCloseCommand() { 
		List<String> closeCommand = new ArrayList<>();
		return closeCommand;
	}
	
	
	/**
	 * sends the command to execute a region
	 */
	private void sendExecuteRegionCmd() { 
		txu.sendOpenPacket(socket);
	}

	@Override
	public void networkLoop() {
		setObservableVar(ORNames.RUN_UDP_FPGAPROGRAMMER_NETWORK, false);
	}

	@Override
	public void afterNetworkLoop() {
		waitCommandLoop();	
		txu.sendClosePacket(socket);
		socket.closeSocket();
		System.out.println("###### EXECUTE REGION STOPPED: ");
	}
	
	private void waitCommandLoop() { 
		for(int i = 10; i >=0; i--) {
			ObservableFacade.setValue(ORNames.DOWNLOAD_CONSOLE_PRINT_STRING, String.format("CHANGING BUILD WAIT :: %d", i));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			ObservableFacade.setValue(ORNames.PROGRESS_BAR_STATUS, (100 - (10 * i)));
		}
		
		ObservableFacade.setValue(ORNames.DOWNLOAD_CONSOLE_PRINT_STRING, "COMPLETE, Select \"RUNNING BUILD\" to validate success");
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
