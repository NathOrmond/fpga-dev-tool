package com.nathanormond.xml.jaxb.commands;

import java.util.List;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class MCACommands {
	/*******************
	 * JAXB
	 */
	
	@XmlElementWrapper(name="Header")
	@XmlElement(name="line")
	private List<String> headerLines;
	
	@XmlElementWrapper(name="StartCommands")
	@XmlElement(name="Command")
	private List<Command> startCommands;
	
	@XmlElementWrapper(name="StopCommands")
	@XmlElement(name="Command")
	private List<Command> stopCommands;
	
	/*******************
	 * GETTERS & SETTERS 
	 */
	
	public List<String> getHeaderLines() {
		return headerLines;
	}
	
	public List<Command> getStartCommands() {
		return startCommands;
	}
	
	public List<Command> getStopCommands() {
		return stopCommands;
	}

}