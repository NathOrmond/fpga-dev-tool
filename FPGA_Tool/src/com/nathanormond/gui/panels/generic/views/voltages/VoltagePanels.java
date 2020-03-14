package com.nathanormond.gui.panels.generic.views.voltages;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;
import com.nathanormond.obsrv.observable.ORNames;
import com.nathanormond.obsrv.observable.ObservableFacade;

public class VoltagePanels extends AbstractPanel {
	
	private static final long serialVersionUID = 1L;
	public static List<VoltagePanel> voltagePanels;
	private static final int NUM_PANELS = 4;

	public VoltagePanels(ActionListener publicListener) {
		super(publicListener);
	}
	
	/*************************************
	 * INITIALISATION
	 */
	
	@Override
	public void instantiateComponents() {
		
		this.setPreferredSize(new Dimension(ObservableFacade.getValue(ORNames.PREFERRED_OUTPUT_PANEL_WIDTH), ObservableFacade.getValue(ORNames.PREFERRED_OUTPUT_PANEL_HEIGHT)));	
		
		voltagePanels = new ArrayList<VoltagePanel>();
		
		VoltagePanel panel;
		
		for(int index = 0; index < NUM_PANELS; index++) { 
			panel = new VoltagePanel(this);
			voltagePanels.add(panel);
		}
		
	}

	@Override
	public void addComponentsToPanel() {
		int count = 0;
		for(VoltagePanel panel: voltagePanels) { 
			addComponentToPanel(panel, (count % 2), (count/2));
			
			count ++;
		}
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
