package com.nathanormond.xml.jaxb.toolconfig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ToolConfig {
	
	/***************************
	 * JAXB
	 */
	@XmlElement(name = "OutputAreaWidth")
	private int outputAreaWidth;
	
	
	@XmlElement(name = "OutputAreaHeight")
	private int outputAreaHeight;
	
	
	/***************************
	 * GETTERS
	 */
	
	public int getOutputAreaHeight() {
		return outputAreaHeight;
	}
	
	public int getOutputAreaWidth() {
		return outputAreaWidth;
	}
	
}
