package com.nathanormond.xml.jaxb.fpgaconfig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class FPGAConfig {
	
	/**********************
	 * JAXB
	 */
	
	@XmlElement(name="IPAddress")
	private String ipAddress; 
	
	
	@XmlElement(name="FPGAPort")
	private int fpgaPort; 
	
	
	@XmlElement(name="MyPort")
	private int myPort; 
	
	
	@XmlElement(name="NumFlashSlots")
	private int numFlashSlots;


	/**********************
	 * GETTERS & SETTERS 
	 */
	
	public String getIpAddress() {
		return ipAddress;
	}


	public int getFpgaPort() {
		return fpgaPort;
	}


	public int getMyPort() {
		return myPort;
	}


	public int getNumFlashSlots() {
		return numFlashSlots;
	}

	
	
}
