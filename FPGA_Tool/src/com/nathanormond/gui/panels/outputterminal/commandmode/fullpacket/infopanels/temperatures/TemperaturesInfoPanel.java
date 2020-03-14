package com.nathanormond.gui.panels.outputterminal.commandmode.fullpacket.infopanels.temperatures;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;
import com.nathanormond.gui.panels.generic.views.temperatures.MultiTempPanel;
import com.nathanormond.gui.panels.generic.views.temperatures.TemperaturePanel;
import com.nathanormond.obsrv.observable.ORNames;
import com.nathanormond.obsrv.observable.ObservableFacade;

public class TemperaturesInfoPanel extends AbstractPanel {

	private static final long serialVersionUID = 1L;
	public MultiTempPanel multiTempPanel;
	public TemperaturePanel mainTempPanel;
	
	public TemperaturesInfoPanel(ActionListener publicListener) {
		super(publicListener);
	}
	
	/*************************************
	 * INITIALISATION
	 */
	
	@Override
	public void instantiateComponents() {
		this.setPreferredSize(new Dimension(ObservableFacade.getValue(ORNames.PREFERRED_OUTPUT_PANEL_WIDTH), ObservableFacade.getValue(ORNames.PREFERRED_OUTPUT_PANEL_HEIGHT)));
		
		multiTempPanel = new MultiTempPanel(this);
		mainTempPanel = new TemperaturePanel(this);
	}

	@Override
	public void addComponentsToPanel() {
		addComponentToPanel(mainTempPanel, 0, 0);
		addComponentToPanel(multiTempPanel, 1, 0);
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
