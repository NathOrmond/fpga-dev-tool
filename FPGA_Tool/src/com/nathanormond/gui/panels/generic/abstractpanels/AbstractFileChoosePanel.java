package com.nathanormond.gui.panels.generic.abstractpanels;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import com.nathanormond.fileio.FileNavigator;
import com.nathanormond.gui.components.textarea.ChooseFileArea;
import com.nathanormond.obsrv.ActionCommands;


public abstract class AbstractFileChoosePanel extends AbstractPanel {
	
	private static final long serialVersionUID = 1L;
	private ChooseFileArea chooseFileArea;
	private JButton chooseFileButton;
	public String selectedFilePath;
	private JScrollPane scrollPane;

	public AbstractFileChoosePanel(ActionListener publicListener) {
		super(publicListener);
	}

	/******************
	 * CREATE PANEL
	 */
	@Override
	public void instantiateComponents() {
		chooseFileArea = new ChooseFileArea("choose file");
		chooseFileButton = new JButton("browse");
		
		scrollPane = new JScrollPane(chooseFileArea);
		styleComponents();
	}
	
	private void styleComponents() { 
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(350,50));
	}
	
	@Override
	public void setActionCommands() {
		chooseFileButton.setActionCommand(ActionCommands.CHOOSE_FILE);
	}

	@Override
	public void addComponentsToPanel() {
		addComponentToPanel(scrollPane, 0, 0);
		addComponentToPanel(chooseFileButton, 1, 0);
	}
	
	/******************
	 * PUBLIC ACTION LISTENER
	 */

	@Override
	public void addPublicActionListeners(ActionListener listener) {
		//not used
	}

	/******************
	 * THIS ACTION LISTENER
	 */
	
	@Override
	public void addLocalActionListeners() {
		chooseFileButton.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		FileNavigator navigator;
		switch(e.getActionCommand()){ 
			case ActionCommands.CHOOSE_FILE:
				navigator = new FileNavigator();
				selectedFilePath = navigator.selectPath();
				chooseFileArea.setText(selectedFilePath);
				chooseFileArea.validityCheck();
				afterFileChosen();
		}
	}
	
	public abstract void afterFileChosen();
	
	/******************
	 * RETURN PANEL
	 */

	@Override
	public JPanel getPanel() {
		this.setBorder(new TitledBorder("Choose File"));
		return this;
	}

	public String getSelectedFilePath() {
		return selectedFilePath;
	}
	
	public void setPlaceHolder(String placeholder){ 
		chooseFileArea.setText(placeholder);
	}
}