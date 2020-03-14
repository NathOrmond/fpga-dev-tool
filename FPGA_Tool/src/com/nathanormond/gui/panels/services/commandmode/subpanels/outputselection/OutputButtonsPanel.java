package com.nathanormond.gui.panels.services.commandmode.subpanels.outputselection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Enumeration;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;
import com.nathanormond.obsrv.observable.ORNames;
import com.nathanormond.obsrv.observable.ObservableFacade;

public class OutputButtonsPanel extends AbstractPanel implements ItemListener, Observer {
	
	private static final long serialVersionUID = 1L;
	JRadioButton loopBack, fullPacket;
	ButtonGroup outputSelection;
	private boolean isNetworkInUse;

	public OutputButtonsPanel(ActionListener publicListener) {
		super(publicListener);
		this.isNetworkInUse = ObservableFacade.getValue(ORNames.IS_NETWORK_IN_USE);
		ObservableFacade.addObserver(ORNames.IS_NETWORK_IN_USE, this);
	}
	
	/*************************************************
	 * Instantiation
	 */
	
	@Override
	public void instantiateComponents() {
		loopBack = new JRadioButton("Loop Back");
		fullPacket = new JRadioButton("Full Packet");
		
		styleElements();
		
		outputSelection = new ButtonGroup();
		
		outputSelection.add(loopBack);
		outputSelection.add(fullPacket);
	}
	
	private void styleElements() { 
		ObservableFacade.setValue(ORNames.DISPLAY_FULL_RX_PACKET_CONTENT, true);
		fullPacket.setSelected(true);
	}
	
	@Override
	public void addPublicActionListeners(ActionListener listener) {
		// not used
	}
	
	@Override
	public void setActionCommands() {
		// not used
	}

	@Override
	public void addLocalActionListeners() {
		loopBack.addItemListener(this);
		fullPacket.addItemListener(this);
	}
	
	@Override
	public void addComponentsToPanel() {
		addComponentToPanel(loopBack, 0, 0);
		addComponentToPanel(fullPacket, 1, 0);
	}
	
	/*************************************************
	 * Actions
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// not used as radio button group
	}
	
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(loopBack.isSelected()) { 
			ObservableFacade.setValue(ORNames.DISPLAY_FULL_RX_PACKET_CONTENT, false);
		}else { 
			ObservableFacade.setValue(ORNames.DISPLAY_FULL_RX_PACKET_CONTENT, true);
		}
	}
	
	/*************************************************
	 * Actions
	 */
	
	/**
	 * Outcome: 
	 * 	Greys out radiobutton selection whilst network is running. 
	 * 	Prevents error by changing selection whilst running. 
	 * 
	 * Update called on change of observable variable "isNetworkInUse"
	 */
	@Override
	public void update(Observable obs, Object obj) {
		this.isNetworkInUse = (Boolean) obj;	/**	 Set local instance to new, changed value	**/
		Enumeration<AbstractButton> enumeration = outputSelection.getElements();
		while (enumeration.hasMoreElements()) {
		    enumeration.nextElement().setEnabled(!isNetworkInUse);
		}
	}
	
	
	/*************************************************
	 * GETTERS & SETTERS
	 */

	@Override
	public JPanel getPanel() {
		return this;
	}


}
