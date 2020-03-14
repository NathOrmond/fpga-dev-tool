package com.nathanormond.gui.panels.services.commandmode;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;
import com.nathanormond.gui.panels.services.commandmode.subpanels.commandselection.Segment2Panel;
import com.nathanormond.gui.panels.services.commandmode.subpanels.outputselection.OutputSelectionPanel;
import com.nathanormond.gui.panels.services.commandmode.subpanels.transmissiontiming.Segment1Panel;
import com.nathanormond.gui.panels.services.genericudppanels.StartStopPanel;
import com.nathanormond.obsrv.observable.ORNames;
import com.nathanormond.obsrv.observable.ObservableFacade;
import com.nathanormond.xml.jaxb.commands.Command;


public class CommandModePanel extends AbstractPanel {
	
	private static final long serialVersionUID = 1L;
	private Segment1Panel seg1;
	public Segment2Panel seg2;
	private OutputSelectionPanel seg3;
	public StartStopPanel startStopPanel;

	public CommandModePanel(ActionListener publicListener) {
		super(publicListener, true);
	}
	
	/******************
	 * REFRESHMETHODS
	 */
	
	public void refreshPanel(ActionListener publicListener){ 
		removeAll();
		seg2 = new Segment2Panel(publicListener);
		addComponentsToPanel();
	}

	/******************
	 * CREATE PANEL
	 */
	
	@Override
	public void instantiateComponents() {
		
	}

	@Override
	public void addComponentsToPanel() {
		addComponentToPanel(seg1.getPanel(), 0, 0);
		addComponentToPanel(seg2.getPanel(), 0, 1);
		addComponentToPanel(seg3, 0, 2);
		addComponentToPanel(startStopPanel.getPanel(), 0, 3);
	}
	
	@Override
	public void setActionCommands() {
		//no action commands set here
	}
	
	
	/******************
	 * PUBLIC ACTION LISTENER
	 */
	
	@Override
	public void addPublicActionListeners(ActionListener listener) {
		seg1 = new Segment1Panel(listener);
		seg2 = new Segment2Panel(listener);
		seg3 = new OutputSelectionPanel(listener);
		startStopPanel = new StartStopPanel(listener);
	}
	
	/******************
	 * THIS ACTION LISTENER
	 */
	
	@Override
	public void addLocalActionListeners() {
		//no local action listeners
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//no local actions performed
	}
	
	/******************
	 * RETURN PANEL
	 */

	@Override
	public JPanel getPanel() {
		this.setBorder(new TitledBorder("FPGA Loop Back Tests"));
		return this;
	}

}