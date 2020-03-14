package com.nathanormond.xml.jaxb.commands;

import javax.xml.bind.annotation.XmlAttribute;

public class SectorAddress {
	
	@XmlAttribute(name="id")
	private String id;
	
	
	@XmlAttribute(name="hex")
	private String sectorAddress;
	
	
	public String getId() {
		return id;
	}
	
	public String getSectorAddress() {
		return sectorAddress;
	}
	
	
	@Override
	public String toString() { 
		return String.format("SECTOR ADDRESS: %s : %S" , getId(), getSectorAddress());
	}

}
