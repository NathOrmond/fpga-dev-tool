package com.nathanormond.gui.panels.services.buildprogrammer.subpanels;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;
import com.nathanormond.obsrv.ActionCommands;

public class InterrogatePanel extends AbstractPanel {
	
	private static final long serialVersionUID = 1L;
	JButton runningBuild, selectedRegion, allRegions;
	
	public InterrogatePanel(ActionListener publicListener) {
		super(publicListener);
	}

	/***********************************************
	 * Instantiation
	 */
	
	@Override
	public void instantiateComponents() {
		runningBuild = new JButton("Running Build");
		selectedRegion = new JButton("Selected Region");
		allRegions = new JButton("All Regions");
		
		styleComponents();
	}
	
	private void styleComponents() {
		
		Font f = new Font("Arial", Font.BOLD, 12);
		Dimension d = new Dimension(150, 30);
		
		runningBuild.setFont(f);				
		runningBuild.setToolTipText(runningBuildToolTip());
		runningBuild.setPreferredSize(d);
		runningBuild.setEnabled(true);
		
		selectedRegion.setFont(f);				
		selectedRegion.setToolTipText(selectedRegionToolTip());
		selectedRegion.setPreferredSize(d);
		selectedRegion.setEnabled(true);
		
		allRegions.setFont(f);				
		allRegions.setToolTipText(allRegionsToolTip());
		allRegions.setPreferredSize(d);
		allRegions.setEnabled(true);
	}

	@Override
	public void setActionCommands() {
		runningBuild.setActionCommand(ActionCommands.INTERROGATE_RUNNING_REGION);
		selectedRegion.setActionCommand(ActionCommands.INTERROGATE_SELECTED_REGION);
		allRegions.setActionCommand(ActionCommands.INTERROGATE_ALL_REGIONS);
	}
	
	@Override
	public void addPublicActionListeners(ActionListener listener) {
		runningBuild.addActionListener(listener);
		selectedRegion.addActionListener(listener);
		allRegions.addActionListener(listener);
	}

	@Override
	public void addLocalActionListeners() {
		// not used
	}

	@Override
	public void addComponentsToPanel() {
			addComponentToPanel(runningBuild, 0, 0);
			addComponentToPanel(selectedRegion, 1, 0);
			addComponentToPanel(allRegions, 2, 0);
	}

	/***********************************************
	 * Action Events
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// not used as no actions	
	}

	
	/***********************************************
	 * GETTERS & SETTERS
	 */
	
	@Override
	public JPanel getPanel() {
		this.setBorder(new TitledBorder("INTERROGATE"));
		return this;
	}
	
	/***********************************************
	 * TOOLTIP DESCRIPTIONS
	 */
	
	private String runningBuildToolTip() { 
		return "<html>Displays which build is currently running on FPGA</html>";
	}
	
	private String selectedRegionToolTip() { 
		return "<html>Polls the selected region informing of its contents</html>";
	}

	
	private String allRegionsToolTip() { 
		return "<html>Interrogates all memory regions informing of their contents</html>";
	}



}
