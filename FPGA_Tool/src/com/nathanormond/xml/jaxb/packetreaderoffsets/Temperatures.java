package com.nathanormond.xml.jaxb.packetreaderoffsets;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Temperatures {
	
	
	/**********************
	 * JAXB
	 */
	@XmlElement(name="THER")
	private int ther;
	
	@XmlElement(name="CoreT0")
	private int coreT0;
	
	@XmlElement(name="LTMT1")
	private int ltmt1;
	
	@XmlElement(name="PECT2")
	private int pect2;
	
	@XmlElement(name="TMAX")
	private int tmax;
	
	@XmlElement(name="TMIN")
	private int tmin;

	
	
	/**********************
	 * GETTERS & SETTERS 
	 */
	
	
	public int getTher() {
		return ther;
	}

	public int getCoreT0() {
		return coreT0;
	}

	public int getLtmt1() {
		return ltmt1;
	}

	public int getPect2() {
		return pect2;
	}

	public int getTmax() {
		return tmax;
	}

	public int getTmin() {
		return tmin;
	}
	


}
