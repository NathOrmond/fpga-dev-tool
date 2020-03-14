package com.nathanormond.gui.panels.generic.logoutput;

import java.awt.event.ActionListener;

import com.nathanormond.gui.panels.generic.abstractpanels.AbstractFileChoosePanel;
import com.nathanormond.obsrv.observable.ORNames;
import com.nathanormond.obsrv.observable.ObservableFacade;


public class ChooseLogFilePanel extends AbstractFileChoosePanel{

	private static final long serialVersionUID = 1L;

	public ChooseLogFilePanel(ActionListener publicListener) {
		super(publicListener);
	}

	@Override
	public void afterFileChosen() {
		ObservableFacade.setValue(ORNames.OUTPUT_LOG_PATH, getSelectedFilePath());
	}

}
