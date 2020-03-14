package com.nathanormond.gui.panels.outputterminal.commandmode.fullpacket.infopanels.hotlink;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;
import com.nathanormond.gui.panels.outputterminal.commandmode.fullpacket.infopanels.generic.BitSnapShotPanel;
import com.nathanormond.obsrv.observable.ORNames;
import com.nathanormond.obsrv.observable.ObservableFacade;

public class HotLinkInfoPanel extends AbstractPanel {


	private static final long serialVersionUID = 1L;
	private BitSnapShotPanel bitSnapShot;
	private HotLinkStatusPanel hotLinkPanel;
	
	public HotLinkInfoPanel(ActionListener publicListener) {
		super(publicListener);
	}
	
	/*************************************
	 * INITIALISATION
	 */

	
	@Override
	public void instantiateComponents() {
		this.setPreferredSize(new Dimension(ObservableFacade.getValue(ORNames.PREFERRED_OUTPUT_PANEL_WIDTH), ObservableFacade.getValue(ORNames.PREFERRED_OUTPUT_PANEL_HEIGHT)));
		bitSnapShot = new BitSnapShotPanel(this);
		hotLinkPanel = new HotLinkStatusPanel(this);
	}

	@Override
	public void addComponentsToPanel() {
		addComponentToPanel(bitSnapShot, 0, 0);
		addComponentToPanel(hotLinkPanel, 0, 1);
	}
	
	@Override
	public void addPublicActionListeners(ActionListener listener) {
		// not used
	}

	@Override
	public void addLocalActionListeners() {
		// not used
	}

	@Override
	public void setActionCommands() {
		
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
