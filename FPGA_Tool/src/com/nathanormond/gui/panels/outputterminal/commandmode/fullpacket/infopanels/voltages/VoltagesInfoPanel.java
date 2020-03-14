package com.nathanormond.gui.panels.outputterminal.commandmode.fullpacket.infopanels.voltages;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;
import com.nathanormond.gui.panels.generic.views.voltages.VoltagePanel;
import com.nathanormond.obsrv.observable.ORNames;
import com.nathanormond.obsrv.observable.ObservableFacade;

public class VoltagesInfoPanel extends AbstractPanel  {

	private static final long serialVersionUID = 1L;
	private VoltagePanel voltage;
	
	public VoltagesInfoPanel(ActionListener publicListener) {
		super(publicListener);
	}
	
	/*************************************
	 * INITIALISATION
	 */
	
	@Override
	public void instantiateComponents() {
		this.setPreferredSize(new Dimension(ObservableFacade.getValue(ORNames.PREFERRED_OUTPUT_PANEL_WIDTH), ObservableFacade.getValue(ORNames.PREFERRED_OUTPUT_PANEL_HEIGHT)));
		voltage = new VoltagePanel(this);
	}

	@Override
	public void addComponentsToPanel() {
		addComponentToPanel(voltage, 0, 0);
	}
	
	@Override
	public void addPublicActionListeners(ActionListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addLocalActionListeners() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setActionCommands() {
		// TODO Auto-generated method stub
		
	}
	
	/*************************************
	 * ACTIONS
	 */
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	/*************************************
	 * GETTERS & SETTERS
	 */
	
	@Override
	public JPanel getPanel() {
		return this;
	}

}
