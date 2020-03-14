package com.nathanormond.network.commandmode;

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

public class CommandModeNetworkMethodTest {

	private CommandModeNetworkMethod commandModeNetworkMethod;
	private RunTimeComposer runTimeComposer;
	private XMLParser xmlParser;
	
	@Before
	public void preTest() { 
		new ObservableReferences();
		ObservableFacade.setValue(ORNames.FPGA_UDP_PORT, 1);
		ObservableFacade.setValue(ORNames.IS_DELAY_TIME_SET, false);
		runTimeComposer = new RunTimeComposer("test");
		runTimeComposer.setDefaultXMLConfig();
		try {
			xmlParser = new XMLParser();
			assertTrue(true);
		} catch (JAXBException e) {
			e.printStackTrace();
			fail();
		}
		commandModeNetworkMethod = new CommandModeNetworkMethod();
	}
	
	@After
	public void postTest() { 
		ObservableFacade.setValue(ORNames.RUN_UDP_COMMANDMODE_NETWORK, false);
	}

	@Test
	public void beforeNetworkMethodTest() { 
		commandModeNetworkMethod.beforeNetworkLoop();
		assertTrue((boolean) ObservableFacade.getValue(ORNames.RUN_UDP_COMMANDMODE_NETWORK));
	}
	
	@Test
	public void networkLoopConditionTest() { 
		ObservableFacade.setValue(ORNames.IS_NUM_COMMANDS_SET, false);
		ObservableFacade.setValue(ORNames.RUN_UDP_COMMANDMODE_NETWORK, false);
		ObservableFacade.setValue(ORNames.NUM_COMMANDS_TO_SEND, 10);
		commandModeNetworkMethod.setCount(11);
		assertFalse(commandModeNetworkMethod.networkLoopCondition());
		
		ObservableFacade.setValue(ORNames.IS_NUM_COMMANDS_SET, true);
		ObservableFacade.setValue(ORNames.RUN_UDP_COMMANDMODE_NETWORK, false);
		ObservableFacade.setValue(ORNames.NUM_COMMANDS_TO_SEND, 10);
		commandModeNetworkMethod.setCount(11);
		assertFalse(commandModeNetworkMethod.networkLoopCondition());
		
		ObservableFacade.setValue(ORNames.IS_NUM_COMMANDS_SET, true);
		ObservableFacade.setValue(ORNames.RUN_UDP_COMMANDMODE_NETWORK, true);
		ObservableFacade.setValue(ORNames.NUM_COMMANDS_TO_SEND, 10);
		commandModeNetworkMethod.setCount(11);
		assertFalse(commandModeNetworkMethod.networkLoopCondition());
		
		ObservableFacade.setValue(ORNames.IS_NUM_COMMANDS_SET, true);
		ObservableFacade.setValue(ORNames.RUN_UDP_COMMANDMODE_NETWORK, true);
		ObservableFacade.setValue(ORNames.NUM_COMMANDS_TO_SEND, 10);
		commandModeNetworkMethod.setCount(12);
		assertTrue(commandModeNetworkMethod.networkLoopCondition());
	}

}
