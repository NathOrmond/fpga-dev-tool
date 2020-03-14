package com.nathanormond.xml.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.nathanormond.xml.jaxb.commands.MCACommands;
import com.nathanormond.xml.jaxb.commands.ProgramFPGACommands;
import com.nathanormond.xml.jaxb.fpgaconfig.FPGAConfig;
import com.nathanormond.xml.jaxb.packetreaderoffsets.PacketReaderOffsets;
import com.nathanormond.xml.jaxb.toolconfig.ToolConfig;

@XmlRootElement(name="FPGATool")
@XmlAccessorType(XmlAccessType.FIELD)
public class FPGAToolXMLConfigJAXB {
	
	/********************
	 * JAXB
	 */
	
	@XmlElement(name="ToolConfig")
	private ToolConfig toolConfig;
	
	@XmlElement(name="FPGAConfig")
	private FPGAConfig fpgaConfig;
	
	@XmlElement(name="PacketReaderOffsets")
	private PacketReaderOffsets packetReaderOffsets;
	
	@XmlElement(name="MCACommands")
	private MCACommands mcaCommands;
	
	@XmlElement(name="ProgramFPGACommands")
	private ProgramFPGACommands programFPGACommands;

	
	/********************
	 * GETTERS & SETTERS
	 */

	public ToolConfig getToolConfig() {
		return toolConfig;
	}

	public FPGAConfig getFpgaConfig() {
		return fpgaConfig;
	}

	public PacketReaderOffsets getPacketReaderOffsets() {
		return packetReaderOffsets;
	}

	public MCACommands getMcaCommands() {
		return mcaCommands;
	}
	
	public ProgramFPGACommands getProgramFPGACommands() {
		return programFPGACommands;
	}
	
}
