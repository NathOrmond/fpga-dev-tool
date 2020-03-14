package com.nathanormond.gui.panels.services.buildprogrammer.subpanels;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;

import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;
import com.nathanormond.obsrv.observable.ORNames;
import com.nathanormond.obsrv.observable.ObservableFacade;

public class SelectWorkingRegionPanel extends AbstractPanel {
	
	private static final long serialVersionUID = 1L;
	List<JRadioButton> radioButtons;
	ButtonGroup buttonGroup;
	
	public SelectWorkingRegionPanel(ActionListener publicListener) {
		super(publicListener);
	}

	/***********************************************
	 * Instantiation
	 */
	
	@Override
	public void instantiateComponents() {
		radioButtons = new ArrayList<JRadioButton>();
		buttonGroup = new ButtonGroup();
		
		Dimension d = new Dimension(55,20);
		int maxNum = ObservableFacade.getValue(ORNames.NUM_BUILD_SLOTS);
		for(int count = 0; count < maxNum; count ++) { 
			JRadioButton button = new JRadioButton(String.valueOf(count));
			button.setActionCommand(String.valueOf(count));
			button.addActionListener(this);
			button.setPreferredSize(d);
			radioButtons.add(button);
			buttonGroup.add(button);
		}
	}
	
	@Override
	public void setActionCommands() {
		// not used set in instantiation loop (for speed, no point looping same loop multiple times)
	}

	@Override
	public void addPublicActionListeners(ActionListener listener) {
		for(JRadioButton radioButton : radioButtons) { 
			radioButton.addActionListener(listener);
		}
	}

	@Override
	public void addLocalActionListeners() {
		// not used
	}

	@Override
	public void addComponentsToPanel() {
		int count = 0;
		for(JRadioButton radioButton : radioButtons) { 
			if(count == 0) { 
				radioButton.setSelected(true);
				ObservableFacade.setValue(ORNames.SELECTED_FLASH_SLOT, String.format("region %d", count));
			}
			addComponentToPanel(radioButton, count, 0);
			count ++;
		}
	}
	
	/***********************************************
	 * Action Events
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// not used
	}

	/***********************************************
	 * GETTERS & SETTERS
	 */
	
	@Override
	public JPanel getPanel() {
		this.setBorder(new TitledBorder("SELECT WORKING MEMORY REGION"));
		return this;
	}
}
