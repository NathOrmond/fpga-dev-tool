package com.nathanormond.xml.jaxb.commands;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Command {

	@XmlElement(name="line")
	private List<String> lines;

	@XmlAttribute(name="id")
	private String id;
	
	
	public List<String> getLines() {
		return lines;
	}
	
	public String getId() {
		return id;
	}
	
	@Override
	public String toString() {
		String rv = String.format("[ID = %s] >>", getId());
		for(String line : getLines()) { 
			rv += String .format("%s ", line);
		}
		return String.format("%s [END]", rv);
	}
}
