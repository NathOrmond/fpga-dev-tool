package com.nathanormond.runtime.runmodes;

import java.util.Observable;

import javax.swing.JPanel;

import com.nathanormond.gui.frames.OutputTerminalFrame;
import com.nathanormond.gui.panels.outputterminal.packetsdropped.OutputPacketsDroppedPanel;
import com.nathanormond.gui.panels.services.tapontest.TapOnTestPanel;
import com.nathanormond.network.tapon.TapOnNetworkMethod;
import com.nathanormond.obsrv.ActionCommands;
import com.nathanormond.obsrv.observable.ORNames;
import com.nathanormond.obsrv.observable.ObservableFacade;
import com.nathanormond.obsrv.observer.VariableObserver;

public class TapOnMode extends AbstractModeComposer {
	
	
	private static TapOnTestPanel tapOnTestPanel;
	private static OutputPacketsDroppedPanel outputTerminalPanel;
	private static TapOnNetworkMethod network;
	private static VariableObserver<Boolean> udpTapOnTestObserver;

	/********************************
	 * Initialisation
	 */

	public TapOnMode() {
		super();
	}

	@Override
	public void instantiateObjects() {
		tapOnTestPanel = new TapOnTestPanel(this);
		network = new TapOnNetworkMethod();
		udpTapOnTestObserver = new VariableObserver<>("Tap On Mode : UDP Tap on Test Observer");
		ObservableFacade.addObserver(ORNames.RUN_UDP_TAPONMODE_NETWORK, udpTapOnTestObserver);
	}
	
	/********************************
	 * Action Switch
	 */

	@Override
	public void actionSwitch(String actionCommand) {
		switch(actionCommand) {
		
			case ActionCommands.START:
				startTapOnSubRoutine();
				break;
		
			case ActionCommands.STOP:
				stopTapOnSubRoutine();
				break;
		}
	}
	
	/********************************
	 * Action Methods
	 */

	private void startTapOnSubRoutine() {
		tapOnTestPanel.startStopPanel.start.startButton.setEnabled(false);
		tapOnTestPanel.startStopPanel.stop.stopButton.setEnabled(true);
		outputTerminalPanel = new OutputPacketsDroppedPanel(this);
		ObservableFacade.setValue(ORNames.RUN_UDP_TAPONMODE_NETWORK, true);
		ObservableFacade.setValue(ORNames.IS_NETWORK_IN_USE, true);
		Thread t1 = new Thread(network);
		t1.start();
		new OutputTerminalFrame(outputTerminalPanel);
	}

	private void stopTapOnSubRoutine() {
		tapOnTestPanel.startStopPanel.start.startButton.setEnabled(true);
		tapOnTestPanel.startStopPanel.stop.stopButton.setEnabled(false);
		ObservableFacade.setValue(ORNames.RUN_UDP_TAPONMODE_NETWORK, false);
		ObservableFacade.setValue(ORNames.IS_NETWORK_IN_USE, false);
	}
	
	/********************************
	 * GETTERS & SETTERS
	 */
	
	public JPanel getTapOnTestPanel() {
		return tapOnTestPanel.getPanel();
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}


}
