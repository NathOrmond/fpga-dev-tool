package com.nathanormond.gui.panels.services.genericudppanels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;


public class StartStopPanel extends AbstractPanel {


	private static final long serialVersionUID = 1L;
	public StartPanel start; 
	public StopPanel stop;

	public StartStopPanel(ActionListener publicListener) {
		super(publicListener, true);
	}

	/******************
	 * CREATE PANEL
	 */
	
	@Override
	public void instantiateComponents() {
		//not used with this super constructor
	}

	@Override
	public void addComponentsToPanel() {
		addComponentToPanel(start.getPanel(), 0, 0);
		addComponentToPanel(stop.getPanel(), 1, 0);
	}
	
	@Override
	public void setActionCommands() {
		//not used as components are panels
	}
	
	
	/******************
	 * PUBLIC ACTION LISTENER
	 */
	
	@Override
	public void addPublicActionListeners(ActionListener listener) {
		start = new StartPanel(listener); 
		stop = new StopPanel(listener);
		
		start.setToolTipText(startDescription());
		stop.setToolTipText(stopDescription());
		
	}
	
	private String startDescription() {
		return "Starts test and sends Start Command"; 
	}
	
	private String stopDescription() {
		return "Stops test and sends Stop Command"; 
	}
	
	/******************
	 * THIS ACTION LISTENER
	 */
	
	@Override
	public void addLocalActionListeners() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/******************
	 * RETURN PANEL
	 */

	@Override
	public JPanel getPanel() {
		return this;
	}
	
}