package com.nathanormond.xml.jaxb.packetreaderoffsets;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class PacketReaderOffsets {

	/****************************
	 * JAXB
	 */
	
	@XmlElement(name="FullOutputRange")
	private FullOutputRange fullOutputRange;
	
	@XmlElement(name="SignificantOffsets")
	private SignificantOffsets significantOffsets;
	
	@XmlElement(name="Voltages")
	private Voltages voltages;
	
	@XmlElement(name="Temperatures")
	private Temperatures temperatures;

	
	/***************************
	 * GETTERS
	 */

	public FullOutputRange getFullOutputRange() {
		return fullOutputRange;
	}

	public SignificantOffsets getSignificantOffsets() {
		return significantOffsets;
	}

	public Voltages getVoltages() {
		return voltages;
	}

	public Temperatures getTemperatures() {
		return temperatures;
	}
	


}
