package com.nathanormond.network.buildprogrammer;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.nathanormond.bitmanipulations.BitManipulations;
import com.nathanormond.fileio.FileNavigator;
import com.nathanormond.network.AbstractUDPNetworkMethod;
import com.nathanormond.network.components.MySocket;
import com.nathanormond.network.components.Receiver;
import com.nathanormond.network.components.Transmitter;
import com.nathanormond.obsrv.observable.ORNames;
import com.nathanormond.obsrv.observable.ObservableFacade;
import com.nathanormond.xml.jaxb.commands.Command;
import com.nathanormond.xml.jaxb.commands.DownloadAddress;

public class DownloadBuildNetworkMethod extends AbstractUDPNetworkMethod {


	private String rx = ""; 
	private boolean isResponse = false;
	private FileNavigator fileNavigator;
	private File buildFile;
	private int currentIteration;
	private static final String CMD_ID = "loadFile", CMD_STATUS_ID = "status";
	private static final int BLOCK = 512;
	private int readFileSection;
	private static final int BT_OFFSET = 0x28, FLAG_OFFSET = 0x2C;
	private List<String> statusCmd;
	

	public DownloadBuildNetworkMethod(int fileWriteCacheBuffer) {
		super(fileWriteCacheBuffer);
	}

	@Override
	public void instantiateNetworkObjects() {
		socket = new MySocket();
		rxu = new Receiver();
	}

	@Override
	public void beforeNetworkLoop() {
		selectInputFile();

		System.out.println("###### DOWNLOAD TO SELECTED REGION STARTED: ");
		setObservableVar(ORNames.RUN_UDP_FPGAPROGRAMMER_NETWORK, true);
		setCount(1);
		int myPort = (int) getObserver(ORNames.MY_UDP_PORT).getVariableInstance();
		socket.openSocket(myPort);
		try {
			txu = new Transmitter((String) ObservableFacade.getValue(ORNames.IP_ADDRESS),
					(int) ObservableFacade.getValue(ORNames.FPGA_UDP_PORT));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.currentIteration = 0;
		this.readFileSection = 0;
		this.statusCmd = statusCommand();
	}

	private void selectInputFile() {
		fileNavigator = new FileNavigator();
		this.buildFile = fileNavigator.selectFile();
	}
	
	/**
	 * 
	 * File size must be less than 8.5M and greater than 7.5M
	 * File type must be a binary file
	 * 
	 * @return whether or not selected file is valid
	 */
	private boolean isValidFile() { 
		if(((this.buildFile.getTotalSpace() > (4 * 1000000)) && (this.buildFile.getTotalSpace() > (10 * 1000000))) && (this.buildFile.getAbsolutePath().contains(".bin"))) { 
			return true;
		} else { 
			ObservableFacade.setValue(ORNames.DOWNLOAD_CONSOLE_PRINT_STRING, "INVALID FILE CHOICE");
			return false;
		}
	}
	
	/******************************************
	 * MESSAGE CREATION
	 */
	
	/**
	 * Creates Sector address (to be appended into erase command)
	 * for current iteration.
	 * @param baseSectorAddress
	 * @return hex String with sector address appended
	 */
	private String createSectorAddress(String baseSectorAddress) {
		String hex = Integer.toHexString(this.currentIteration);
		char[] hexChars = hex.toCharArray();
		char[] chars = baseSectorAddress.toCharArray();

		int charsIndex = chars.length - 2;
		for(int index = (hexChars.length - 1); index >= 0; index --) { 
			char c = chars[charsIndex];
			char d = hexChars[index];
			chars[charsIndex] = hexChars[index];
			charsIndex --;
		}
		
		return String.valueOf(chars);
	}
	
	/**
	 * Creates a List of Strings for the load command to be sent in UDP packet
	 * 
	 * @return validLoadCommand
	 */
	private List<String> loadCommand(){ 
	List<String> loadCommand = new ArrayList<>();
		
		for(String headerCommand : (List<String>) ObservableFacade.getValue(ORNames.HEADER_COMMANDS)) { 
			loadCommand.add(headerCommand);
		}
		
		for(Command command : (List<Command>) ObservableFacade.getValue(ORNames.PROGRAM_COMMANDS)) { 
			if(command.getId().equals(this.CMD_ID)) { 
				for(String line : command.getLines()) { 
					
					if(line.equals(ORNames.DOWNLOAD_ADDRESSES)) { 
						for(DownloadAddress downloadAddress : (List<DownloadAddress>) ObservableFacade.getValue(ORNames.DOWNLOAD_ADDRESSES)) { 
							if(downloadAddress.getId().equals(ObservableFacade.getValue(ORNames.SELECTED_FLASH_SLOT))) { 
								loadCommand.add(createSectorAddress(downloadAddress.getDownloadAddress()));
							}
						}
					} else if (line.equals("FILE")) {
						for(String fileSeg : fileSegments()) { 
							loadCommand.add(fileSeg);
						}
					}else { 
						loadCommand.add(line);
					}
					
				}
			}
		}
		
		return loadCommand;
	}
	
	/**
	 * 
	 * 
	 * @return String values of file data to be appended to msg
	 */
	private List<String> fileSegments() { 
		List<String> fileSegs = new ArrayList<>();
		 byte[] allBytes = readSmallBinaryFile(this.buildFile.getAbsolutePath());
		 ByteBuffer bb = ByteBuffer.wrap(allBytes);
		 
		 if((this.readFileSection + BLOCK) <= this.buildFile.length()) {
			
			 for(int index = this.readFileSection; index < (this.readFileSection + BLOCK); index += 4) { 
				 int hexInt = bb.getInt(index);
				 String hex =  String.format("0x%s", BitManipulations.padLeft(hexInt));
				 fileSegs.add(hex);
			 }
			 
		 } else { 

			 for(int index = this.readFileSection; index < this.readFileSection; index += 4) { 
				 int hexInt = bb.getInt(index);
				 String hex =  String.format("0x%s", BitManipulations.padLeft(hexInt));
				 fileSegs.add(hex);
			 }
			 
			 int differenceBytes = ((this.readFileSection + BLOCK) - (this.readFileSection));
			 for(int index = 0; index < differenceBytes; index += 4) { 
				 fileSegs.add(String.format("0x%s", BitManipulations.padLeft(0)));
			 }
			 
		 }
		
		return fileSegs;
	}
	
	  byte[] readSmallBinaryFile(String fileName) {
		    Path path = Paths.get(fileName);
		    try {
				return Files.readAllBytes(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		  }
	
	private List<String> statusCommand(){ 
		List<String> statusCMD = new ArrayList<>();
		for(String headerCommand : (List<String>) ObservableFacade.getValue(ORNames.HEADER_COMMANDS)) { 
			statusCMD.add(headerCommand);
		}
		for(Command command : (List<Command>) ObservableFacade.getValue(ORNames.PROGRAM_COMMANDS)) { 
			if(command.getId().equals(this.CMD_STATUS_ID)) { 
				for(String line : command.getLines()) { 
					statusCMD.add(line);
				}
			}
		}
		
		return statusCMD;
	}
	
	
	/******************************************
	 * NETWORK
	 */
	@Override
	public void networkLoop() {
		
		rx = "";
		txu.sendCustomUDPPacket(loadCommand(), socket);
		while(!isLoadResponseValid()) { 
			listenForResponse(loadCommand());
		}
		
		rx = "";
		
		txu.sendCustomUDPPacket(this.statusCmd, socket);
		while(!isReadResponseValid()) {
			listenForResponse(this.statusCmd);
		}
		
		if(isReadResponseValid()) { 
			this.currentIteration++;
			this.readFileSection += BLOCK;
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
	 * sends the command to execute a region
	 */
	private void sendCmd(List<String> commandsToSend) { 
		isResponse = false;
		txu.sendCustomUDPPacket(commandsToSend, socket);;
	}
	
	
	/*****************************************
	 * CONDITIONS
	 */
	
	private boolean isLoadResponseValid() { 
		return ((isResponse) && (rx.contains("LOAD")));
//		return true;
	}
	
	private boolean isReadResponseValid() { 
		byte[] rxData = rxu.receiveData;
		ByteBuffer bb = ByteBuffer.wrap(rxData);
		return (((BitManipulations.padLeft(bb.getInt(BT_OFFSET)).equals("42540021")) && (BitManipulations.padLeft(bb.getInt(FLAG_OFFSET)).equals("00000000"))) && (isResponse));
//		return true;
	}
	
	/*****************************************
	 * OTHER NETWORK
	 */

	@Override
	public void afterNetworkLoop() {
		socket.closeSocket();
		System.out.println("######  DOWNLOAD TO SELECTED REGION STOPPED: ");
		if (isValidFile()) {
			ObservableFacade.setValue(ORNames.PROGRESS_BAR_STATUS, 100);
			ObservableFacade.setValue(ORNames.DOWNLOAD_CONSOLE_PRINT_STRING,"DOWNLOAD COMPLETE - POLL REGION TO VERIFY SUCCESS");
		}
	}

	@Override
	public boolean networkLoopCondition() {
		return (((boolean) ObservableFacade.getValue(ORNames.RUN_UDP_FPGAPROGRAMMER_NETWORK)) && (isValidFile())) && (calculatePercentCompletion() < 100);
	}
	
	private float calculatePercentCompletion() { 
		float totalFile = this.buildFile.length();
		float rvf = (this.readFileSection) / totalFile;
		float rv = rvf * 100;
		return rv;
	}

	@Override
	public void updateOutputGraphics() {
		float f = calculatePercentCompletion();
		System.out.println(f + "%");
		ObservableFacade.setValue(ORNames.PROGRESS_BAR_STATUS, (int)f);
	}

}
