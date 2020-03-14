package com.nathanormond.gui.panels.services.buildprogrammer.subpanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;
import com.nathanormond.obsrv.observable.ORNames;
import com.nathanormond.obsrv.observable.ObservableFacade;

public class ProgressPanel extends AbstractPanel implements Observer {
	
	private static final long serialVersionUID = 1L;
	JProgressBar progressBar;
	
	public ProgressPanel(ActionListener publicListener) {
		super(publicListener);
	}

	/***********************************************
	 * Instantiation
	 */
	
	
	@Override
	public void instantiateComponents() {
		progressBar = new JProgressBar(0);
		progressBar.setMaximum(100);
		progressBar.setMinimum(0);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		progressBar.setFont(new Font("Garamond", Font.BOLD , 11));
		componentStyling();
		ObservableFacade.addObserver(ORNames.PROGRESS_BAR_STATUS, this);
	}
	
	private void componentStyling() { 
		progressBar.setPreferredSize(new Dimension(450,30));
	}
	
	
	@Override
	public void setActionCommands() {
		// not used set in individual panels
	}

	
	
	@Override
	public void addPublicActionListeners(ActionListener listener) {
		// not used as set in individual panels
	}

	@Override
	public void addLocalActionListeners() {
		// not used as set in individual panels
	}

	@Override
	public void addComponentsToPanel() {
		addComponentToPanel(progressBar, 0, 0);
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
		this.setBorder(new TitledBorder("PROGRESS"));
		return this;
	}

	@Override
	public void update(Observable obs, Object obj) {
		int progress = (Integer) obj;
		progressBar.setStringPainted(true);
		progressBar.setValue(progress);
		
		if(progress == 100) {
			progressBar.setForeground(Color.green);
		} else { 
			progressBar.setForeground(Color.BLACK);
		}
	}


}
