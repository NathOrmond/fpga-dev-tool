package com.nathanormond.xml.jaxb.commands;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class StopCommands {
	
	@XmlElement(name="StopCommands")
	private List<Command> stopCommands;
	
	
}