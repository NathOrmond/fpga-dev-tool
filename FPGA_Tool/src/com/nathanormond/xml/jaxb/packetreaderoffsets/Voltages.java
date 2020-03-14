package com.nathanormond.xml.jaxb.packetreaderoffsets;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Voltages {

	/***************************
	 * JAXB
	 */
	
	@XmlElement(name = "Input5v")
	private int input5v;

	@XmlElement(name = "Input3v3")
	private int input3v3;

	@XmlElement(name = "Input1v5")
	private int input1v5;

	@XmlElement(name = "Input1v2")
	private int input1v2;

	@XmlElement(name = "Input1v8GTX")
	private int input1v8GTX;

	@XmlElement(name = "Input1v0GTX")
	private int input1v0GTX;

	@XmlElement(name = "Input1v2GTX")
	private int input1v2GTX;

	@XmlElement(name = "Input1_0vCORE")
	private int input1_0vCORE;


	
	/***************************
	 * GETTERS
	 */
	
	public int getInput5v() {
		return input5v;
	}

	public int getInput3v3() {
		return input3v3;
	}

	public int getInput1v5() {
		return input1v5;
	}

	public int getInput1v2() {
		return input1v2;
	}

	public int getInput1v8GTX() {
		return input1v8GTX;
	}

	public int getInput1v0GTX() {
		return input1v0GTX;
	}

	public int getInput1v2GTX() {
		return input1v2GTX;
	}

	public int getInput1_0vCORE() {
		return input1_0vCORE;
	}
	
	
	
	

}
