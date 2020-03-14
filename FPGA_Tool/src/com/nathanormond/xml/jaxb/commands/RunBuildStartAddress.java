package com.nathanormond.xml.jaxb.commands;

import javax.xml.bind.annotation.XmlAttribute;

public class RunBuildStartAddress {
	
	@XmlAttribute(name="id")
	private String id;
	
	@XmlAttribute(name="isGoldenRegion")
	private String isGoldenRegion;
	
	@XmlAttribute(name="hex")
	private String runBuildStartAddress;
	
	
	public String getId() {
		return id;
	}
	
	public String getIsGoldenRegion() {
		return isGoldenRegion;
	}
	
	public String getRunBuildStartAddress() {
		return runBuildStartAddress;
	}
	
	
	@Override
	public String toString() { 
		return String.format("BUILD: %s :: ADDRESS :: %s :: GOLDEN_REGION: %s", getId(), getRunBuildStartAddress(), getIsGoldenRegion());
	}
}
