package com.nathanormond.network.buildprogrammer;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.nathanormond.bitmanipulations.BitManipulations;
import com.nathanormond.network.AbstractUDPNetworkMethod;
import com.nathanormond.network.components.MySocket;
import com.nathanormond.network.components.Receiver;
import com.nathanormond.network.components.Transmitter;
import com.nathanormond.obsrv.observable.ORNames;
import com.nathanormond.obsrv.observable.ObservableFacade;
import com.nathanormond.xml.jaxb.commands.Command;
import com.nathanormond.xml.jaxb.commands.SectorAddress;

public class EraseRegionNetworkMethod extends AbstractUDPNetworkMethod {

	private static final int BT_OFFSET = 0x28, FLAG_OFFSET = 0x2C;
	private static final String CMD_ID = "eraseBuild", CMD_STATUS_ID = "status";
	private int maxSectorSize = 32, currentSector = 0;
	private List<String> statusCmd;
	
	public EraseRegionNetworkMethod(int fileWriteCacheBuffer) {
		super(fileWriteCacheBuffer);
	}

	@Override
	public void instantiateNetworkObjects() {
		socket = new MySocket(); 
		rxu = new Receiver(); 
	}

	@Override
	public void beforeNetworkLoop() {
		System.out.println("###### ERASE SELECTED REGION STARTED: ");
		setObservableVar(ORNames.RUN_UDP_FPGAPROGRAMMER_NETWORK, true);
		setCount(1);
		int myPort = (int) getObserver(ORNames.MY_UDP_PORT).getVariableInstance();
		socket.openSocket(myPort);
		try {
			txu = new Transmitter((String) ObservableFacade.getValue(ORNames.IP_ADDRESS), (int) ObservableFacade.getValue(ORNames.FPGA_UDP_PORT));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.statusCmd = statusCommand();
	}
	
	/********************************************** 
	 * MESSAGE CREATION METHODS
	 */

	
	/**
	 * Creates Sector address (to be appended into erase command)
	 * for current iteration.
	 * @param baseSectorAddress
	 * @return hex String with sector address appended
	 */
	private String createSectorAddress(String baseSectorAddress) {
		String hex = Integer.toHexString(currentSector);
		char[] hexChars = hex.toCharArray();
		char[] chars = baseSectorAddress.toCharArray();

		switch (hexChars.length) {	//Assuming maximum hex value of f1
			case 1:
				chars[4] = hexChars[0];
				break;
			case 2:	
				chars[3] = hexChars[0];
				chars[4] = hexChars[1];
				break;
		}
		
		return String.valueOf(chars);
	}
	
	
	/**
	 * Creates Erase Command
	 * @return eraseCommand
	 */
	private List<String> eraseCommand(){ 
		List<String> eraseCommand = new ArrayList<>();
		
		for(String headerCommand : (List<String>) ObservableFacade.getValue(ORNames.HEADER_COMMANDS)) { 
			eraseCommand.add(headerCommand);
		}
		
		for(Command command : (List<Command>) ObservableFacade.getValue(ORNames.PROGRAM_COMMANDS)) { 
			if(command.getId().equals(this.CMD_ID)) { 
				for(String line : command.getLines()) { 
					if(line.equals(ORNames.SECTOR_ADDRESSES)) { 
						for(SectorAddress sectorAddress : (List<SectorAddress>) ObservableFacade.getValue(ORNames.SECTOR_ADDRESSES)) { 
							if(sectorAddress.getId().equals(ObservableFacade.getValue(ORNames.SELECTED_FLASH_SLOT))) { 
								eraseCommand.add(createSectorAddress(sectorAddress.getSectorAddress()));
							}
						}
					} else { 
						
						eraseCommand.add(line);
					}
				}
			}
		}
		
		return eraseCommand;
	}
	
	
	
	/**
	 * Creates Erase Status Command
	 * @return eraseStatusCommand
	 */
	private List<String> statusCommand(){ 
		List<String> statusCommand = new ArrayList<>();
		
		for(String headerCommand : (List<String>) ObservableFacade.getValue(ORNames.HEADER_COMMANDS)) { 
			statusCommand.add(headerCommand);
		}
		
		for(Command command : (List<Command>) ObservableFacade.getValue(ORNames.PROGRAM_COMMANDS)) { 
			if(command.getId().equals(this.CMD_STATUS_ID)) { 
				for(String line : command.getLines()) { 
					statusCommand.add(line);
				}
			}
		}
		
		return statusCommand;
	}

	
	/**
	 * sends the command to execute a region
	 */
	private void sendCmd(List<String> commandsToSend) { 
		isResponse = false;
		txu.sendCustomUDPPacket(commandsToSend, socket);;
	}
	
	
	/********************************************** 
	 * NETWORK LOGIC
	 */

	private String rx = "";
	private boolean isResponse = false;
	
	@Override
	public void networkLoop() {
		txu.sendCustomUDPPacket(eraseCommand(), socket);
		rx = "";
		while (!erasReceived()) {
			listenForResponse(eraseCommand());
		}
		
		txu.sendCustomUDPPacket(this.statusCmd, socket);
		rx = "";
		while(!erasStatusResponseContainsValidStatus()) {
			listenForResponse(this.statusCmd);
		}
		
		if(erasStatusResponseContainsValidStatus()) { 
			this.currentSector++;
		}
		
		updateOutputGraphics();
	}
	
	private void listenForResponse(List<String> commandsToSend) { 
		try {
			rxu.receivePacket(socket); /* Listen for FPGA response */
			isResponse = true;
			this.rx = new String(rxu.receiveData, StandardCharsets.UTF_8);
		} catch (SocketTimeoutException e) {
			sendCmd(commandsToSend);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * If FPGA receives ERAS Command it will reply with a packet containing ERAS in the header.
	 * @return if received packet contains 'ERAS'
	 */
	private boolean erasReceived() { 
		return rx.contains("ERAS");
//		return true;
	}
	
	/**
	 * Response packet to ERAS STATUS will contain a flag at packet offset 
	 * if value is 0x00 then return true. for anything else return false.
	 * @return erasStatusResponseContainsValidStatus
	 */
	public boolean erasStatusResponseContainsValidStatus() { 
		
		if(isResponse) { 			
			byte[] rxData = rxu.receiveData;
			ByteBuffer bb = ByteBuffer.wrap(rxData);
			
			String bt = BitManipulations.padLeft(bb.getInt(BT_OFFSET));
			String flag = BitManipulations.padLeft(bb.getInt(FLAG_OFFSET));
			
			String output = String.format("New Packet Received...\nEXPECTED %s :: ACTUAL %s \n EXPECTED %s :: ACTUAL %s", "42540021", bt, "00000000", flag);
			ObservableFacade.setValue(ORNames.DOWNLOAD_CONSOLE_PRINT_STRING, output);
			
			return ((bt.equals("42540021")) && (flag.equals("00000000")));
		}
		
		return false;
//		return true;
	}

	@Override
	public void afterNetworkLoop() {
		socket.closeSocket();
		System.out.println("######  ERASE SELECTED REGION STOPPED: ");
		ObservableFacade.setValue(ORNames.PROGRESS_BAR_STATUS, 100);
		ObservableFacade.setValue(ORNames.DOWNLOAD_CONSOLE_PRINT_STRING, "ERASE COMPLETE - POLL REGION TO VERIFY SUCCESS");
	}

	@Override
	public boolean networkLoopCondition() {
		return (((boolean) ObservableFacade.getValue(ORNames.RUN_UDP_FPGAPROGRAMMER_NETWORK)) && (this.currentSector < this.maxSectorSize));
	}

	@Override
	public void updateOutputGraphics() {
		
		float progress = ((float) (this.currentSector / (float) this.maxSectorSize) * 100);
		ObservableFacade.setValue(ORNames.PROGRESS_BAR_STATUS, Math.round(progress));
	}

}
