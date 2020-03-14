package com.nathanormond.xml;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.nathanormond.obsrv.observable.ORNames;
import com.nathanormond.obsrv.observable.ObservableFacade;
import com.nathanormond.xml.jaxb.FPGAToolXMLConfigJAXB;
import com.nathanormond.xml.jaxb.commands.Command;

public class XMLParser {
	
	private FPGAToolXMLConfigJAXB xmlConfigJAXBObjects;

	/**
	 * Parses XML, sets constants
	 * @throws JAXBException
	 */
	public XMLParser() throws JAXBException { 
		readXML();
		setXMLConsts();
	}
	
	/**
	 * Overloaded XML Parser (Points to new XML file at String:filepath)
	 * @param filePath
	 * @throws JAXBException
	 */
	public XMLParser(String filePath) throws JAXBException {
		readXML(filePath);
		setXMLConsts();
	}
	
	/**
	 * ToDo catch exception
	 * Read Default XML (Contained within this project)
	 * @throws JAXBException
	 */
	private void readXML() throws JAXBException{ 
		JAXBContext jc = JAXBContext.newInstance(FPGAToolXMLConfigJAXB.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
//		String resource = (String) ObservableDAO.getValue(ORNames.xmlPath);
		String resource = ObservableFacade.getValue(ORNames.XML_CONFIG_PATH);
		System.out.println(String.format("RESOURCE LOADING : %s", resource));
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream(resource);
		boolean isNull = (is != null);
		System.out.println(String.format("INPUT STREAM SET : %s", isNull));
		try {
		xmlConfigJAXBObjects = (FPGAToolXMLConfigJAXB) unmarshaller.unmarshal(is);
		Marshaller marshaller = jc.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		System.out.println("### READING XML CONFIG \n=========================================\n");
		marshaller.marshal(xmlConfigJAXBObjects, System.out);
		System.out.println("=========================================\n");
		} catch(JAXBException e) { 
			System.out.println("Check Default XML Path points to XML in this project");
		}
	}
	
	/**
	 * Reads XML file external to project (IE selected through File chooser)
	 * @param filePath
	 * @throws JAXBException
	 */
	private void readXML(String filePath) throws JAXBException{ 	
		JAXBContext jc = JAXBContext.newInstance(FPGAToolXMLConfigJAXB.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		xmlConfigJAXBObjects = (FPGAToolXMLConfigJAXB) unmarshaller.unmarshal(new File(filePath));
		Marshaller marshaller = jc.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		System.out.println("### READING XML CONFIG \n=========================================\n");
		marshaller.marshal(xmlConfigJAXBObjects, System.out);
		System.out.println("=========================================\n");
	}
	
	
	/************************************************
	 * JAXB XML Objects 
	 */
	
	/**
	 * SET XML DEFINED CONSTS IN OBSERVABLE REFERENCES
	 */
	private void setXMLConsts() { 
		setGUIXMLConsts();
		setFPGAConfigXMLConsts();
		setFullPacketOutputRangeXMLConsts();
		setVoltagePacketOffsetsXMLConsts();
		setTemperaturePacketOffsetsXMLConsts();
		setOtherOPacketOffsetsXMLConsts();
		setMCACommandXMLConsts();
		setProgramFPGACommandsXMLConsts();
	}
	
	
	private void setGUIXMLConsts() { 
		/** TOOL GUI CONFIG **/
		ObservableFacade.setValue(ORNames.PREFERRED_OUTPUT_PANEL_WIDTH, xmlConfigJAXBObjects.getToolConfig().getOutputAreaWidth());
		ObservableFacade.setValue(ORNames.PREFERRED_OUTPUT_PANEL_HEIGHT, xmlConfigJAXBObjects.getToolConfig().getOutputAreaHeight());
	}
	
	private void setFPGAConfigXMLConsts() { 
		/** FPGA CONFIG **/
		ObservableFacade.setValue(ORNames.IP_ADDRESS, xmlConfigJAXBObjects.getFpgaConfig().getIpAddress());
		ObservableFacade.setValue(ORNames.FPGA_UDP_PORT, xmlConfigJAXBObjects.getFpgaConfig().getFpgaPort());
		ObservableFacade.setValue(ORNames.MY_UDP_PORT, xmlConfigJAXBObjects.getFpgaConfig().getMyPort());
		ObservableFacade.setValue(ORNames.NUM_BUILD_SLOTS, xmlConfigJAXBObjects.getFpgaConfig().getNumFlashSlots());
	}
	private void setFullPacketOutputRangeXMLConsts() { 
		/** FULL PACKET OUTPUT RANGE **/
		ObservableFacade.setValue(ORNames.FULL_OUTPUT_OFFSET_START, xmlConfigJAXBObjects.getPacketReaderOffsets().getFullOutputRange().getStart());
		ObservableFacade.setValue(ORNames.FULL_OUTPUT_OFFSET_END, xmlConfigJAXBObjects.getPacketReaderOffsets().getFullOutputRange().getEnd());
	}
	
	private void setVoltagePacketOffsetsXMLConsts() { 
		/** VOLTAGE PACKET OFFSETS **/
		ObservableFacade.setValue(ORNames.INPUT_5V, xmlConfigJAXBObjects.getPacketReaderOffsets().getVoltages().getInput5v());
		ObservableFacade.setValue(ORNames.INPUT_3V3, xmlConfigJAXBObjects.getPacketReaderOffsets().getVoltages().getInput3v3());
		ObservableFacade.setValue(ORNames.INPUT_1V5, xmlConfigJAXBObjects.getPacketReaderOffsets().getVoltages().getInput1v5());
		ObservableFacade.setValue(ORNames.INPUT_1V2, xmlConfigJAXBObjects.getPacketReaderOffsets().getVoltages().getInput1v2());
		ObservableFacade.setValue(ORNames.INPUT_1V8_GTX, xmlConfigJAXBObjects.getPacketReaderOffsets().getVoltages().getInput1v8GTX());
		ObservableFacade.setValue(ORNames.INPUT_1V0_GTX, xmlConfigJAXBObjects.getPacketReaderOffsets().getVoltages().getInput1v0GTX());
		ObservableFacade.setValue(ORNames.INPUT_1V2_GTX, xmlConfigJAXBObjects.getPacketReaderOffsets().getVoltages().getInput1v2GTX());
		ObservableFacade.setValue(ORNames.INPUT_1_0V_CORE, xmlConfigJAXBObjects.getPacketReaderOffsets().getVoltages().getInput1_0vCORE());
	}
	private void setTemperaturePacketOffsetsXMLConsts() { 
		/** TEMPERATURE PACKET OFFSETS **/
		ObservableFacade.setValue(ORNames.THER, xmlConfigJAXBObjects.getPacketReaderOffsets().getTemperatures().getTher());
		ObservableFacade.setValue(ORNames.CORE_TEMP_T0, xmlConfigJAXBObjects.getPacketReaderOffsets().getTemperatures().getCoreT0());
		ObservableFacade.setValue(ORNames.LTM_TEMP, xmlConfigJAXBObjects.getPacketReaderOffsets().getTemperatures().getLtmt1());
		ObservableFacade.setValue(ORNames.PEC_TEMP, xmlConfigJAXBObjects.getPacketReaderOffsets().getTemperatures().getPect2());
		ObservableFacade.setValue(ORNames.MAX_TEMP, xmlConfigJAXBObjects.getPacketReaderOffsets().getTemperatures().getTmax());
		ObservableFacade.setValue(ORNames.MIN_TEMP, xmlConfigJAXBObjects.getPacketReaderOffsets().getTemperatures().getTmin());
	}
	
	private void setOtherOPacketOffsetsXMLConsts() { 
		/** OTHER PACKET OFFSETS **/
		ObservableFacade.setValue(ORNames.PACKET_HEADER, xmlConfigJAXBObjects.getPacketReaderOffsets().getSignificantOffsets().getPacketHeader());
		ObservableFacade.setValue(ORNames.PACKET_SEQUENCE_NUMBER, xmlConfigJAXBObjects.getPacketReaderOffsets().getSignificantOffsets().getPacketSequenceNum());
		ObservableFacade.setValue(ORNames.MEZANINE_2_BITE_FLAGS, xmlConfigJAXBObjects.getPacketReaderOffsets().getSignificantOffsets().getMezanine2BiteFlags());
		ObservableFacade.setValue(ORNames.BITE_MODE, xmlConfigJAXBObjects.getPacketReaderOffsets().getSignificantOffsets().getBiteMode());	
		ObservableFacade.setValue(ORNames.CHAN_A_BITE_RES, xmlConfigJAXBObjects.getPacketReaderOffsets().getSignificantOffsets().getChanABiteRes());
		ObservableFacade.setValue(ORNames.CHAN_B_BITE_RES, xmlConfigJAXBObjects.getPacketReaderOffsets().getSignificantOffsets().getChanBBiteRes());
		ObservableFacade.setValue(ORNames.CHAN_C_BITE_RES, xmlConfigJAXBObjects.getPacketReaderOffsets().getSignificantOffsets().getChanCBiteRes());
		ObservableFacade.setValue(ORNames.CHAN_D_BITE_RES, xmlConfigJAXBObjects.getPacketReaderOffsets().getSignificantOffsets().getChanDBiteRes());
		ObservableFacade.setValue(ORNames.SPARE_1, xmlConfigJAXBObjects.getPacketReaderOffsets().getSignificantOffsets().getSpare1());
		ObservableFacade.setValue(ORNames.SPARE_2, xmlConfigJAXBObjects.getPacketReaderOffsets().getSignificantOffsets().getSpare2());
		ObservableFacade.setValue(ORNames.SPARE_3, xmlConfigJAXBObjects.getPacketReaderOffsets().getSignificantOffsets().getSpare3());
		ObservableFacade.setValue(ORNames.SPARE_4, xmlConfigJAXBObjects.getPacketReaderOffsets().getSignificantOffsets().getSpare4());
		ObservableFacade.setValue(ORNames.SPARE_5, xmlConfigJAXBObjects.getPacketReaderOffsets().getSignificantOffsets().getSpare5());
	}
	private void setMCACommandXMLConsts() { 
		/** MCA COMMANDS **/		
		ObservableFacade.setValue(ORNames.HEADER_COMMANDS, xmlConfigJAXBObjects.getMcaCommands().getHeaderLines());
		ObservableFacade.setValue(ORNames.START_CMDS, xmlConfigJAXBObjects.getMcaCommands().getStartCommands());

		List<Command> stopCommands = xmlConfigJAXBObjects.getMcaCommands().getStopCommands(); //debug
		ObservableFacade.setValue(ORNames.STOP_CMDS, stopCommands);
	}
	
	private void setProgramFPGACommandsXMLConsts() { 
		/** PROGRAM FPGA XMDS **/
		ObservableFacade.setValue(ORNames.PROGRAM_COMMANDS, xmlConfigJAXBObjects.getProgramFPGACommands().getCommands());
		ObservableFacade.setValue(ORNames.RUN_BUILD_START_ADDRESSES, xmlConfigJAXBObjects.getProgramFPGACommands().getRunBuildStartAddresses());
		ObservableFacade.setValue(ORNames.FLASH_CONFIG_ADDRESSES, xmlConfigJAXBObjects.getProgramFPGACommands().getFlashConfigAddresses());
		ObservableFacade.setValue(ORNames.SECTOR_ADDRESSES, xmlConfigJAXBObjects.getProgramFPGACommands().getSectorAddresses());
		ObservableFacade.setValue(ORNames.DOWNLOAD_ADDRESSES, xmlConfigJAXBObjects.getProgramFPGACommands().getDownloadAddresses());
	}

}
