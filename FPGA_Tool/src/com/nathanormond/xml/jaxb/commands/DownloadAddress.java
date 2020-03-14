package com.nathanormond.xml.jaxb.commands;

import javax.xml.bind.annotation.XmlAttribute;

public class DownloadAddress {

	@XmlAttribute(name="id")
	private String id;
	
	
	@XmlAttribute(name="hex")
	private String downloadAddress;
	
	
	public String getId() {
		return id;
	}
	
	
	public String getDownloadAddress() {
		return downloadAddress;
	}
	
	
	@Override
	public String toString() { 
		return String.format("BUILD: %s :: ADDRESS :: %s ", getId(), getDownloadAddress());
	}
	
}
