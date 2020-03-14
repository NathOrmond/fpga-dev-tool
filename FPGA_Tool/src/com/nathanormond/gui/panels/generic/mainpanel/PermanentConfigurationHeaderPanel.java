package com.nathanormond.gui.panels.generic.mainpanel;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.nathanormond.gui.components.textarea.IPArea;
import com.nathanormond.gui.panels.generic.abstractpanels.AbstractPanel;
import com.nathanormond.obsrv.ActionCommands;
import com.nathanormond.obsrv.observable.ORNames;
import com.nathanormond.obsrv.observable.ObservableFacade;

public class PermanentConfigurationHeaderPanel extends AbstractPanel {

	private static final long serialVersionUID = 1L;
	IPArea permanentIPArea;
	JButton permanentIPButton;
	JButton permanentHomeButton;
	
	public PermanentConfigurationHeaderPanel(ActionListener publicListener) {
		super(publicListener);
	}

	/**********************
	 * Initialisation
	 */

	@Override
	public void instantiateComponents() {
		permanentIPArea = new IPArea();
		
		permanentIPArea.setText(ObservableFacade.getValue(ORNames.IP_ADDRESS));
		permanentIPButton = new JButton("SET IP");
		
		permanentHomeButton = new JButton("HOME");
		
		styleComponents();
	}
	
	private void styleComponents(){ 
		Font f = new Font("Arial", Font.BOLD, 10);
		permanentIPButton.setFont(f);
		permanentIPButton.setToolTipText(toolTipTextIPButton());
		
		permanentHomeButton.setFont(f);
		permanentHomeButton.setToolTipText(toolTipTextHomeButton());
	}

	private String toolTipTextHomeButton() {
		return "<html>Returns to home page.</html>";
	}

	private String toolTipTextIPButton() {
		return "<html>Sets IP address for networking</html>";
	}

	@Override
	public void setActionCommands() {
		permanentIPButton.setActionCommand(ActionCommands.SET_IP_ADDR);
		
		permanentHomeButton.setActionCommand(ActionCommands.GUI_HOME);
	}

	@Override
	public void addPublicActionListeners(ActionListener listener) {
		permanentHomeButton.addActionListener(listener);
	}
	
	@Override
	public void addLocalActionListeners() {
		permanentIPButton.addActionListener(this);
	}

	@Override
	public void addComponentsToPanel() {
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		
		addComponentToPanel(permanentIPArea, 0, 0);

		addComponentToPanel(permanentIPButton, 1, 0);
		permanentIPArea.validityCheck();
		
		gbc.anchor = GridBagConstraints.LINE_END;
		
		addComponentToPanel(permanentHomeButton, 2, 0);
	}
	
	/**********************
	 * Methods
	 */
	
	@Override
	public JPanel getPanel() {
		return this;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
			case ActionCommands.SET_IP_ADDR:
				permanentIPArea.validityCheck();
				break;
		}
	}
}
