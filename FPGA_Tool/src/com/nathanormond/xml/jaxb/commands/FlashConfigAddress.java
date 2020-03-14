package com.nathanormond.xml.jaxb.commands;

import javax.xml.bind.annotation.XmlAttribute;

public class FlashConfigAddress {
	
	@XmlAttribute(name="id")
	private String id;
	

	
	@XmlAttribute(name="hex")
	private String flashConfigAddress;
	
	
	public String getId() {
		return id;
	}
	
	
	public String getFlashConfigAddress() {
		return flashConfigAddress;
	}

	
	@Override
	public String toString() {
		return String.format("FLASH CONFIG ADDRESS : %s, VAL : %s", getId(), getFlashConfigAddress());
	}
}
