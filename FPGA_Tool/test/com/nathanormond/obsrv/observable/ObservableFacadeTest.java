package com.nathanormond.obsrv.observable;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.nathanormond.obsrv.observer.VariableObserver;

public class ObservableFacadeTest {
	
	@Before
	public void preTest() { 
		new ObservableReferences();
	}

	@Test
	public void testAddObserver() {
		assertEquals(ObservableFacade.getObserversCount(), 0);
		VariableObserver<Boolean> isFullPacketContentsDisplayed = new VariableObserver<>(ORNames.DISPLAY_FULL_RX_PACKET_CONTENT);
		ObservableFacade.addObserver(ORNames.DISPLAY_FULL_RX_PACKET_CONTENT, isFullPacketContentsDisplayed);
		assertEquals(ObservableFacade.getObserversCount(), 1);
	}

	@Test
	public void testSetValue() {
		VariableObserver<Boolean> isFullPacketContentsDisplayed = new VariableObserver<>(ORNames.DISPLAY_FULL_RX_PACKET_CONTENT);
		ObservableFacade.addObserver(ORNames.DISPLAY_FULL_RX_PACKET_CONTENT, isFullPacketContentsDisplayed);
		ObservableFacade.setValue(ORNames.DISPLAY_FULL_RX_PACKET_CONTENT, false);
		assertEquals(false, isFullPacketContentsDisplayed.getVariableInstance());
		ObservableFacade.setValue(ORNames.DISPLAY_FULL_RX_PACKET_CONTENT, true);
		assertEquals(true, isFullPacketContentsDisplayed.getVariableInstance());
	}

	@Test
	public void testGetValue() {
		ObservableFacade.setValue(ORNames.DISPLAY_FULL_RX_PACKET_CONTENT, false);
		assertEquals(false, ObservableFacade.getValue(ORNames.DISPLAY_FULL_RX_PACKET_CONTENT));
		ObservableFacade.setValue(ORNames.DISPLAY_FULL_RX_PACKET_CONTENT, true);
		assertEquals(true, ObservableFacade.getValue(ORNames.DISPLAY_FULL_RX_PACKET_CONTENT));
		
	}

	@Test
	public void testGetObserversCount() {
		int observerCount = 5;
		assertEquals(ObservableFacade.getObserversCount(), 0);
		List<VariableObserver<Boolean>> observers = new ArrayList();
		
		for(int i = 0; i < observerCount; i++) { 
			observers.add(i, new VariableObserver<>(String.format("%s : %d",ORNames.DISPLAY_FULL_RX_PACKET_CONTENT, i)));
		}
		
		for(VariableObserver obsVar : observers) { 
			ObservableFacade.addObserver(ORNames.DISPLAY_FULL_RX_PACKET_CONTENT, obsVar);
		}
		
		assertEquals(ObservableFacade.getObserversCount(), observerCount);
	}

}
