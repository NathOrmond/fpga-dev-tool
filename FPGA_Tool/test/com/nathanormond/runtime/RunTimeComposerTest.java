package com.nathanormond.runtime;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.xml.bind.JAXBException;

import org.junit.Before;
import org.junit.Test;

import com.nathanormond.obsrv.observable.ObservableReferences;
import com.nathanormond.xml.XMLParser;


public class RunTimeComposerTest {
	
	private RunTimeComposer runTimeComposer;
	private XMLParser xmlParser;

	@Before
	public void preTest() { 
		new ObservableReferences();
		runTimeComposer = new RunTimeComposer("test");
	}
	
	@Test
	public void testSetDefaultXMLConfig() {
		runTimeComposer.setDefaultXMLConfig();
		try {
			xmlParser = new XMLParser();
			assertTrue(true);
		} catch (JAXBException e) {
			e.printStackTrace();
			fail();
		}
		
	}
	
	

}
