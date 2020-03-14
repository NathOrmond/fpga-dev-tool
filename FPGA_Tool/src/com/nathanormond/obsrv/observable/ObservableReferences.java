package com.nathanormond.obsrv.observable;

import java.util.ArrayList;
import java.util.List;

import org.jfree.data.general.DefaultValueDataset;

import com.nathanormond.xml.jaxb.commands.Command;
import com.nathanormond.xml.jaxb.commands.DownloadAddress;
import com.nathanormond.xml.jaxb.commands.FlashConfigAddress;
import com.nathanormond.xml.jaxb.commands.RunBuildStartAddress;
import com.nathanormond.xml.jaxb.commands.SectorAddress;

/**
 * Observable God Class AntiPattern Bean
 * @author User
 *
 */
public class ObservableReferences  {
	
	
	protected static List<ObservableVariable<?>> observables;
	
	/*****************************************************************
	 * HCI CONSTANTS: These constants are set and referenced from within the HCI
	 * (GUI). and define program behaviour and interaction with user.
	 * 
	 * DEFINE THESE WITHIN RUN TIME COMPOSER
	 */
	/******************************
	 * LoopBack test
	 */

	protected static ObservableVariable<Boolean> isSetDelayTime;
	protected static ObservableVariable<Integer> commandDelay;
	protected static ObservableVariable<Boolean> isSetNumCommands;
	protected static ObservableVariable<Integer> commandsToSend;
	protected static ObservableVariable<String> selectedStartCommand;
	protected static ObservableVariable<String> selectedStopCommand;
	

	/******************************
	 * UDP Networking
	 */
	protected static ObservableVariable<Boolean> isNetworkInUse;
	protected static ObservableVariable<Boolean> runUDPLoopBackTest;
	protected static ObservableVariable<Boolean> runUDPTapOnTest;
	protected static ObservableVariable<Boolean> runUDPDownloadTool;
	
	protected static ObservableVariable<Boolean> isFullPacketContentsDisplayed;

	/******************************
	 * BuildProgrammer
	 */

	protected static ObservableVariable<String> selectedBuildSlot;

	/******************************
	 * Log File
	 */
	protected static ObservableVariable<String> logFilePath;
	protected static ObservableVariable<Boolean> isLogFile;
	
	
	/******************************
	 * Internal XML Path
	 */
	public static final String internalXMLRelativePath = "FPGAToolConfig.xml";
	
	/**
	 * GUI Output Observables
	 */
	protected static ObservableVariable<Float> dataSetCoreVoltage, dataSetCoreTemperature, dataSetLTM, dataSetPEC, dataSetMax, dataSetMin;
	
	/*****************************************************************
	 * XML CONSTANTS: INCLUDING DOCUMENT DEFINED MESSAGE DEFINITONS Constants will
	 * vary project to project (verify with ICD before using on new project) FOR
	 * PACKET POSITION DEFINITIONS (SEE DOCUMENTATION) :
	 */

	/******************************
	 * XMLConstants
	 */

	protected static ObservableVariable<String> xmlPath;
	protected static ObservableVariable<String> ipAddr;
	protected static ObservableVariable<Integer> NUM_BUILD_SLOTS;
	protected static ObservableVariable<Integer> myPort;
	protected static ObservableVariable<Integer> fpgaPort;

	/******************************
	 * OutputTerminal
	 */

	protected static ObservableVariable<Integer> PREFERRED_OUTPUT_PANEL_WIDTH;
	protected static ObservableVariable<Integer> PREFERRED_OUTPUT_PANEL_HEIGHT;

	/******************************
	 * XML Configured Transmission Commands for FPGA
	 */

	protected static ObservableVariable<List<String>> headerCommands;
	protected static ObservableVariable<List<Command>> startCommands;
	protected static ObservableVariable<List<Command>> stopCommands;

	/**********************/

	protected static ObservableVariable<Integer> FULL_OUTPUT_OFFSET_START;
	protected static ObservableVariable<Integer> FULL_OUTPUT_OFFSET_END;

	protected static ObservableVariable<Integer> PACKET_HEADER;
	protected static ObservableVariable<Integer> PACKET_SEQUENCE_NUMBER;

	/**********************/

	protected static ObservableVariable<Integer> MEZANINE_2_BITE_FLAGS;

	protected static ObservableVariable<Integer> INPUT_5V;
	protected static ObservableVariable<Integer> INPUT_3V3;

//	protected final int INPUT_2V5; 

	protected static ObservableVariable<Integer> INPUT_1V5;
	protected static ObservableVariable<Integer> INPUT_1V2;
	protected static ObservableVariable<Integer> INPUT_1V8_GTX;
	protected static ObservableVariable<Integer> INPUT_1V0_GTX;
	protected static ObservableVariable<Integer> INPUT_1V2_GTX;
	protected static ObservableVariable<Integer> INPUT_1_0V_CORE;

	/**********************/

	protected static ObservableVariable<Integer> BITE_MODE;
	protected static ObservableVariable<Integer> CHAN_A_BITE_RES;
	protected static ObservableVariable<Integer> CHAN_B_BITE_RES;
	protected static ObservableVariable<Integer> CHAN_C_BITE_RES;
	protected static ObservableVariable<Integer> CHAN_D_BITE_RES;

	protected static ObservableVariable<Integer> SPARE_1;
	protected static ObservableVariable<Integer> SPARE_2;
	protected static ObservableVariable<Integer> SPARE_3;
	protected static ObservableVariable<Integer> SPARE_4;
	protected static ObservableVariable<Integer> SPARE_5;

	protected static ObservableVariable<Integer> THER;

	protected static ObservableVariable<Integer> CORE_TEMP_T0;

	protected static ObservableVariable<Integer> LTM_TEMP;
	protected static ObservableVariable<Integer> PEC_TEMP;

	protected static ObservableVariable<Integer> MAX_TEMP;
	protected static ObservableVariable<Integer> MIN_TEMP;

	protected static ObservableVariable<Integer> PREV_SEQ_COUNT;
	protected static ObservableVariable<Integer> SEQ_COUNT;
	
	
	/******************************
	 * XML Configured FPGA Programmer Vars
	 */
	
	protected static ObservableVariable<List<Command>> programCommands;
	protected static ObservableVariable<List<RunBuildStartAddress>> runBuildStartAddresses;
	protected static ObservableVariable<String> downloadConsolePrintString;
	protected static ObservableVariable<List<FlashConfigAddress>> flashConfigAddresses;
	protected static ObservableVariable<Integer> progressBarStatus;
	protected static ObservableVariable<List<SectorAddress>> sectorAddresses;
	protected static ObservableVariable<List<DownloadAddress>> downloadAddresses;
	
	
	/**
	 * Instantiates every variable ever created
	 */
	public ObservableReferences() { 
		observables = new ArrayList<>();
		downloadAddresses = new ObservableVariable<>(ORNames.DOWNLOAD_ADDRESSES);
		observables.add(downloadAddresses);
		sectorAddresses = new ObservableVariable<>(ORNames.SECTOR_ADDRESSES);
		observables.add(sectorAddresses);
		progressBarStatus = new ObservableVariable<>(ORNames.PROGRESS_BAR_STATUS);
		observables.add(progressBarStatus);
		flashConfigAddresses = new ObservableVariable<>(ORNames.FLASH_CONFIG_ADDRESSES);
		observables.add(flashConfigAddresses);
		downloadConsolePrintString = new ObservableVariable<>(ORNames.DOWNLOAD_CONSOLE_PRINT_STRING);
		observables.add(downloadConsolePrintString);
		programCommands = new ObservableVariable<>(ORNames.PROGRAM_COMMANDS);
		observables.add(programCommands);
		runBuildStartAddresses = new ObservableVariable<>(ORNames.RUN_BUILD_START_ADDRESSES);
		observables.add(runBuildStartAddresses);
		dataSetCoreVoltage = new ObservableVariable<>(ORNames.CORE_VOLTAGE_DATASET);
		observables.add(dataSetCoreVoltage);
		dataSetCoreTemperature = new ObservableVariable<>(ORNames.CORE_TEMPERATURE_DATASET);
		observables.add(dataSetCoreTemperature);
		dataSetLTM = new ObservableVariable<>(ORNames.LTM_TEMP_DATASET);
		observables.add(dataSetLTM);
		dataSetMax = new ObservableVariable<>(ORNames.MAX_TEMP_DATASET);
		observables.add(dataSetMax);
		dataSetMin = new ObservableVariable<>(ORNames.MIN_TEMP_DATASET);
		observables.add(dataSetMin);
		dataSetPEC = new ObservableVariable<>(ORNames.PEC_TEMP_DATASET);
		observables.add(dataSetPEC);
		isNetworkInUse = new ObservableVariable<>(ORNames.IS_NETWORK_IN_USE );
		observables.add(isNetworkInUse);
		runUDPDownloadTool = new ObservableVariable<>(ORNames.RUN_UDP_FPGAPROGRAMMER_NETWORK );
		observables.add(runUDPDownloadTool);
		isSetDelayTime = new ObservableVariable<>(ORNames.IS_DELAY_TIME_SET );
		observables.add(isSetDelayTime);
		commandDelay = new ObservableVariable<>(ORNames.COMMAND_DELAY);
		observables.add(commandDelay);
		isSetNumCommands = new ObservableVariable<>(ORNames.IS_NUM_COMMANDS_SET);
		observables.add(isSetNumCommands);
		commandsToSend = new ObservableVariable<>(ORNames.NUM_COMMANDS_TO_SEND);
		observables.add(commandsToSend);
		selectedStartCommand = new ObservableVariable<>(ORNames.SELECTED_START_COMMAND);
		observables.add(selectedStartCommand);
		selectedStopCommand = new ObservableVariable<>(ORNames.SELECTED_STOP_COMMAND);
		observables.add(selectedStopCommand);
		runUDPLoopBackTest = new ObservableVariable<>(ORNames.RUN_UDP_COMMANDMODE_NETWORK);
		observables.add(runUDPLoopBackTest);
		runUDPTapOnTest = new ObservableVariable<>(ORNames.RUN_UDP_TAPONMODE_NETWORK);
		observables.add(runUDPTapOnTest);
		isFullPacketContentsDisplayed = new ObservableVariable<>(ORNames.DISPLAY_FULL_RX_PACKET_CONTENT);
		observables.add(isFullPacketContentsDisplayed);
		selectedBuildSlot = new ObservableVariable<>(ORNames.SELECTED_FLASH_SLOT);
		observables.add(selectedBuildSlot);
		logFilePath = new ObservableVariable<>(ORNames.OUTPUT_LOG_PATH);
		observables.add(logFilePath);
		isSetDelayTime = new ObservableVariable<Boolean>(ORNames.IS_DELAY_TIME_SET);
		observables.add(isSetDelayTime);
		isLogFile = new ObservableVariable<>(ORNames.LOG_TO_OUTPUT);
		observables.add(isLogFile);
		
		//GUI Output Observables
		
		// XML Defined :
		xmlPath = new ObservableVariable<>(ORNames.XML_CONFIG_PATH);
		observables.add(xmlPath);
		ipAddr = new ObservableVariable<>(ORNames.IP_ADDRESS);
		observables.add(ipAddr);
		NUM_BUILD_SLOTS = new ObservableVariable<>(ORNames.NUM_BUILD_SLOTS);
		observables.add(NUM_BUILD_SLOTS);
		myPort = new ObservableVariable<>(ORNames.MY_UDP_PORT);
		observables.add(myPort);
		fpgaPort = new ObservableVariable<>(ORNames.FPGA_UDP_PORT);
		observables.add(fpgaPort);
		PREFERRED_OUTPUT_PANEL_WIDTH = new ObservableVariable<>(ORNames.PREFERRED_OUTPUT_PANEL_WIDTH);
		observables.add(PREFERRED_OUTPUT_PANEL_WIDTH);
		PREFERRED_OUTPUT_PANEL_HEIGHT = new ObservableVariable<>(ORNames.PREFERRED_OUTPUT_PANEL_HEIGHT);
		observables.add(PREFERRED_OUTPUT_PANEL_HEIGHT);
		headerCommands = new ObservableVariable<>(ORNames.HEADER_COMMANDS);
		observables.add(headerCommands);
		startCommands = new ObservableVariable<>(ORNames.START_CMDS);
		observables.add(startCommands);
		stopCommands = new ObservableVariable<>(ORNames.STOP_CMDS);
		observables.add(stopCommands);
		FULL_OUTPUT_OFFSET_START = new ObservableVariable<>(ORNames.FULL_OUTPUT_OFFSET_START);
		observables.add(FULL_OUTPUT_OFFSET_START);
		FULL_OUTPUT_OFFSET_END = new ObservableVariable<>(ORNames.FULL_OUTPUT_OFFSET_END);
		observables.add(FULL_OUTPUT_OFFSET_END);
		PACKET_HEADER = new ObservableVariable<>(ORNames.PACKET_HEADER);
		observables.add(PACKET_HEADER);
		PACKET_SEQUENCE_NUMBER = new ObservableVariable<>(ORNames.PACKET_SEQUENCE_NUMBER);
		observables.add(PACKET_SEQUENCE_NUMBER);
		MEZANINE_2_BITE_FLAGS = new ObservableVariable<>(ORNames.MEZANINE_2_BITE_FLAGS);
		observables.add(MEZANINE_2_BITE_FLAGS);
		INPUT_5V = new ObservableVariable<>(ORNames.INPUT_5V);
		observables.add(INPUT_5V);
		INPUT_3V3 = new ObservableVariable<>(ORNames.INPUT_3V3);
		observables.add(INPUT_3V3);
		INPUT_1V5 = new ObservableVariable<>(ORNames.INPUT_1V5);
		observables.add(INPUT_1V5);
		INPUT_1V2 = new ObservableVariable<>(ORNames.INPUT_1V2);
		observables.add(INPUT_1V2);
		INPUT_1V8_GTX = new ObservableVariable<>(ORNames.INPUT_1V8_GTX);
		observables.add(INPUT_1V8_GTX);
		INPUT_1V0_GTX = new ObservableVariable<>(ORNames.INPUT_1V0_GTX);
		observables.add(INPUT_1V0_GTX);
		INPUT_1V2_GTX = new ObservableVariable<>(ORNames.INPUT_1V2_GTX);
		observables.add(INPUT_1V2_GTX);
		INPUT_1_0V_CORE = new ObservableVariable<>(ORNames.INPUT_1_0V_CORE);
		observables.add(INPUT_1_0V_CORE);
		BITE_MODE = new ObservableVariable<>(ORNames.BITE_MODE);
		observables.add(BITE_MODE);
		CHAN_A_BITE_RES = new ObservableVariable<>(ORNames.CHAN_A_BITE_RES);
		observables.add(CHAN_A_BITE_RES);
		CHAN_B_BITE_RES = new ObservableVariable<>(ORNames.CHAN_B_BITE_RES);
		observables.add(CHAN_B_BITE_RES);
		CHAN_C_BITE_RES = new ObservableVariable<>(ORNames.CHAN_C_BITE_RES);
		observables.add(CHAN_C_BITE_RES);
		CHAN_D_BITE_RES = new ObservableVariable<>(ORNames.CHAN_D_BITE_RES);
		observables.add(CHAN_D_BITE_RES);
		SPARE_1 = new ObservableVariable<>(ORNames.SPARE_1);
		observables.add(SPARE_1);
		SPARE_2 = new ObservableVariable<>(ORNames.SPARE_2);
		observables.add(SPARE_2);
		SPARE_3 = new ObservableVariable<>(ORNames.SPARE_3);
		observables.add(SPARE_3);
		SPARE_4 = new ObservableVariable<>(ORNames.SPARE_4);
		observables.add(SPARE_4);
		SPARE_5 = new ObservableVariable<>(ORNames.SPARE_5);
		observables.add(SPARE_5);
		THER = new ObservableVariable<>(ORNames.THER);
		observables.add(THER);
		CORE_TEMP_T0 = new ObservableVariable<>(ORNames.CORE_TEMP_T0);
		observables.add(CORE_TEMP_T0);
		LTM_TEMP = new ObservableVariable<>(ORNames.LTM_TEMP);
		observables.add(LTM_TEMP);
		PEC_TEMP = new ObservableVariable<>(ORNames.PEC_TEMP);
		observables.add(PEC_TEMP);
		MAX_TEMP = new ObservableVariable<>(ORNames.MAX_TEMP);
		observables.add(MAX_TEMP);
		MIN_TEMP = new ObservableVariable<>(ORNames.MIN_TEMP);
		observables.add(MIN_TEMP);
		PREV_SEQ_COUNT = new ObservableVariable<>(ORNames.PREV_SEQ_COUNT);
		observables.add(PREV_SEQ_COUNT);
		SEQ_COUNT = new ObservableVariable<>(ORNames.SEQ_COUNT);
		observables.add(SEQ_COUNT);
	}

}
