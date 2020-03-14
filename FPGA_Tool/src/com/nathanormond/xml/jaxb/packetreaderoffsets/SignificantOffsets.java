package com.nathanormond.xml.jaxb.packetreaderoffsets;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class SignificantOffsets {
	
	/*********************************
	 * JAXB
	 */
	
	@XmlElement(name="PacketHeader")
	private int packetHeader;
	
	@XmlElement(name="PacketSequenceNum")
	private int packetSequenceNum;
	
	@XmlElement(name="Mezananine2BiteFlags")
	private int mezanine2BiteFlags;
	
	@XmlElement(name="BiteMode")
	private int biteMode;
	
	@XmlElement(name="ChanABiteRes")
	private int chanABiteRes;
	
	@XmlElement(name="ChanABiteRes")
	private int chanBBiteRes;
	
	@XmlElement(name="ChanCBiteRes")
	private int chanCBiteRes;
	
	@XmlElement(name="ChanDBiteRes")
	private int chanDBiteRes;
	
	@XmlElement(name="Spare1")
	private int spare1;
	
	
	@XmlElement(name="Spare2")
	private int spare2;
	
	@XmlElement(name="Spare3")
	private int spare3;

	
	@XmlElement(name="Spare4")
	private int spare4;
	
	@XmlElement(name="Spare5")
	private int spare5;


	
	
	/*********************************
	 * GETTERS
	 */

	
	public int getPacketHeader() {
		return packetHeader;
	}

	public int getPacketSequenceNum() {
		return packetSequenceNum;
	}

	public int getMezanine2BiteFlags() {
		return mezanine2BiteFlags;
	}
	
	public int getBiteMode() {
		return biteMode;
	}

	public int getChanABiteRes() {
		return chanABiteRes;
	}

	public int getChanBBiteRes() {
		return chanBBiteRes;
	}

	public int getChanCBiteRes() {
		return chanCBiteRes;
	}

	public int getChanDBiteRes() {
		return chanDBiteRes;
	}

	public int getSpare1() {
		return spare1;
	}

	public int getSpare2() {
		return spare2;
	}

	public int getSpare3() {
		return spare3;
	}

	public int getSpare4() {
		return spare4;
	}

	public int getSpare5() {
		return spare5;
	}
	

}
