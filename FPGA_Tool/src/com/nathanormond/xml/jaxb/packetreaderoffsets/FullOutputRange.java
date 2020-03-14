package com.nathanormond.xml.jaxb.packetreaderoffsets;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class FullOutputRange {

	/*******************************
	 * JAXB
	 */
	
	@XmlElement(name="Start")
	private int start;
	
	@XmlElement(name="End")
	private int end;
	
	
	/*************************
	 * getters 
	 */
	public int getStart() {
		return start;
	}
	
	public int getEnd() {
		return end;
	}

}
