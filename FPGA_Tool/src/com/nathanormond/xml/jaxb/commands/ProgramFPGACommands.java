package com.nathanormond.xml.jaxb.commands;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

@XmlAccessorType(XmlAccessType.FIELD)
public class ProgramFPGACommands {
	
	@XmlElementWrapper(name="Header")
	@XmlElement(name="line")
	private List<String> headerLines;
	
	
	@XmlElementWrapper(name="Commands")
	@XmlElement(name="Command")
	private List<Command> commands;
	
	@XmlElementWrapper(name="RunAdresses")
	@XmlElement(name="runBuildStartAddress")
	private List<RunBuildStartAddress> runBuildStartAddresses;
	
	@XmlElementWrapper(name="FlashAddresses")
	@XmlElement(name="flashConfigAddress")
	private List<FlashConfigAddress> flashConfigAddresses;
	
	@XmlElementWrapper(name="SectorAdresses")
	@XmlElement(name="sectorAddress")
	private List<SectorAddress> sectorAddresses;
	
	@XmlElementWrapper(name="DownloadAddresses")
	@XmlElement(name="downloadAddress")
	private List<DownloadAddress> downloadAddresses;
	
	
	public List<String> getHeaderLines() {
		return headerLines;
	}
	
	public List<Command> getCommands() {
		return commands;
	}
	
	public List<RunBuildStartAddress> getRunBuildStartAddresses() {
		return runBuildStartAddresses;
	}
	
	public List<FlashConfigAddress> getFlashConfigAddresses() {
		return flashConfigAddresses;
	}
	
	public List<SectorAddress> getSectorAddresses() {
		return sectorAddresses;
	}

	public List<DownloadAddress> getDownloadAddresses() {
		return downloadAddresses;
	}
	
}
