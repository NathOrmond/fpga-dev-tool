package com.nathanormond.network.tapon;

import static org.junit.Assert.*;

import javax.xml.bind.JAXBException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.nathanormond.obsrv.observable.ORNames;
import com.nathanormond.obsrv.observable.ObservableFacade;
import com.nathanormond.obsrv.observable.ObservableReferences;
import com.nathanormond.runtime.RunTimeComposer;
import com.nathanormond.xml.XMLParser;


public class TapOnNetworkMethodTest {

	private TapOnNetworkMethod tapOnNetworkMethod;
	private RunTimeComposer runTimeComposer;
	@Before
	public void preTest() { 
		new ObservableReferences();
		ObservableFacade.setValue(ORNames.FPGA_UDP_PORT, 1);
		runTimeComposer = new RunTimeComposer("test");
		runTimeComposer.setDefaultXMLConfig();
		try {
			new XMLParser();
			assertTrue(true);
		} catch (JAXBException e) {
			e.printStackTrace();
			fail();
		}
		tapOnNetworkMethod = new TapOnNetworkMethod();
	}
	
	@After
	public void postTest() { 
		ObservableFacade.setValue(ORNames.RUN_UDP_TAPONMODE_NETWORK, false);
	}
	
	@Test
	public void networkLoopConditionTest() {
		ObservableFacade.setValue(ORNames.RUN_UDP_TAPONMODE_NETWORK, false);
		assertFalse(tapOnNetworkMethod.networkLoopCondition());
		ObservableFacade.setValue(ORNames.RUN_UDP_TAPONMODE_NETWORK, true);
		assertTrue(tapOnNetworkMethod.networkLoopCondition());
	}
	
	@Test
	public void errorCountCheckTest() {
		ObservableFacade.setValue(ORNames.SEQ_COUNT, 2);
		ObservableFacade.setValue(ORNames.PREV_SEQ_COUNT, 1);
		tapOnNetworkMethod.setCount(11);
		tapOnNetworkMethod.errorCountCheck();
		assertEquals(0, tapOnNetworkMethod.getErrorCount());
		
		ObservableFacade.setValue(ORNames.SEQ_COUNT, 5);
		ObservableFacade.setValue(ORNames.PREV_SEQ_COUNT, 1);
		tapOnNetworkMethod.setCount(11);
		tapOnNetworkMethod.errorCountCheck();
		assertEquals((5 - 1), tapOnNetworkMethod.getErrorCount());
	}

}
