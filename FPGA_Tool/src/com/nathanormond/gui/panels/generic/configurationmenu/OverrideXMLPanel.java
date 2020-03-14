package com.nathanormond.gui.panels.generic.configurationmenu;

import java.awt.event.ActionListener;

import javax.xml.bind.JAXBException;

import com.nathanormond.gui.panels.generic.abstractpanels.AbstractFileChoosePanel;
import com.nathanormond.xml.XMLParser;


public class OverrideXMLPanel extends AbstractFileChoosePanel{

	private static final long serialVersionUID = 1L;
	private boolean fileChosen = false;

	public OverrideXMLPanel(ActionListener publicListener) {
		super(publicListener);
		setPlaceHolder("Choose new XML Configuration File");
	}

	@Override
	public void afterFileChosen() {
		fileChosen = true;
		System.out.println("Attempting to change XML File");
		try {
			new XMLParser(getSelectedFilePath());
		} catch (JAXBException e) {
			e.printStackTrace();
			System.out.println("Invalid XMLFile Resetting to Default");
			try {
				new XMLParser();
			} catch (JAXBException e1) {
				e1.printStackTrace();
			}
		}
		System.out.println("XML Set");
	}
	
	public boolean isFileChosen() {
		return fileChosen;
	}

}